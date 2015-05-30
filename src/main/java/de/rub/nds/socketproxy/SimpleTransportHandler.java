/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS.
 *
 * Copyright (C) 2015 Juraj Somorovsky
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.rub.nds.socketproxy;

import de.rub.nds.socketproxy.util.ArrayConverter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Juraj Somorovsky <juraj.somorovsky@rub.de>
 */
public class SimpleTransportHandler {

    private static final Logger LOGGER = LogManager.getLogger(SimpleTransportHandler.class);

    private Socket socket;

    private BufferedOutputStream bos;

    private BufferedInputStream bis;

    private boolean proxyRunning;
    
    private final int maxResponseWaitCycles;

    public SimpleTransportHandler() {
        proxyRunning = true;
        maxResponseWaitCycles = -1;
    }
    
    public SimpleTransportHandler(int maxResponseWaitCycles) {
        proxyRunning = true;
        this.maxResponseWaitCycles = maxResponseWaitCycles;
    }

    public void initialize(String address, int port) throws IOException {
        socket = new Socket(address, port);
        OutputStream os = socket.getOutputStream();
        bos = new BufferedOutputStream(os);

        InputStream is = socket.getInputStream();
        bis = new BufferedInputStream(is);
    }

    public void initialize(Socket socket) throws IOException {
        OutputStream os = socket.getOutputStream();
        bos = new BufferedOutputStream(os);

        InputStream is = socket.getInputStream();
        bis = new BufferedInputStream(is);
    }

    public void sendData(byte[] data) throws IOException {
        bos.write(data);
        try {
            bos.flush();
        } catch (SocketException ex) {
            // While connecting to a Java server, a "Connection reset" failure
            // was received.Connection reset means that a TCP packet with the
            // RST bit was received. The most common cause of this is an attempt
            // to send to a partner that has closed its socket
            LOGGER.debug("Connection reset was received by flushing the data. It is very probable that the peer"
                    + " closed the socket after the last data was written. Thus, simply ignore this failure");
        }
    }

    public byte[] fetchData() throws IOException {
        byte[] response = new byte[0];
        int cycles = 0;

        while (bis.available() == 0 && cycles != maxResponseWaitCycles) {
            try {
                Thread.sleep(200);
                cycles++;
            } catch (InterruptedException ex) {
                System.out.println("am back");
            }
        }

        while (bis.available() != 0 && proxyRunning) {
            // TODO: It is never correct to use the return value of this
            // method to allocate a buffer intended to hold all data in this
            // stream.
            // http://docs.oracle.com/javase/7/docs/api/java/io/InputStream.html#available%28%29
            byte[] current = new byte[bis.available()];
            int readResult = bis.read(current);
            if (readResult != -1) {
                response = ArrayConverter.concatenate(response, current);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {

                }
            }
        }
        // LOGGER.debug("Accepted new bytes from server: {}",
        // ArrayConverter.bytesToHexString(response));
        LOGGER.debug("Accepted {} new bytes", response.length);
        return response;
    }

    public void closeConnection() {
        try {
            if (bos != null) {
                bos.close();
            }
        } catch (IOException e) {
            LOGGER.debug(e);
        }
        try {
            if (bis != null) {
                bis.close();
            }
        } catch (IOException e) {
            LOGGER.debug(e);
        }
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            LOGGER.debug(e);
        }
    }

    public boolean isProxyRunning() {
        return proxyRunning;
    }

    public void setProxyRunning(boolean proxyRunning) {
        this.proxyRunning = proxyRunning;
    }
}
