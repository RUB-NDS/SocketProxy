/*
 * Copyright 2015 Juraj Somorovsky - juraj.somorovsky@rub.de.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.rub.nds.socketproxy;

import de.rub.nds.socketproxy.controller.AppController;
import de.rub.nds.socketproxy.util.ArrayConverter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 */
public class Proxy implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(Proxy.class);

    private final Config config;

    private volatile boolean proxyRunning;

    private SimpleTransportHandler clientProxy;

    private SimpleTransportHandler proxyServer;

    private volatile ForwardedMessages forwardedMessages;

    public Proxy() {
        AppController c = new AppController();
        this.config = c.getConfig();
        this.forwardedMessages = c.getForwardedMessages();
    }

    public void startProxy() {
        proxyRunning = true;
        try {
            ServerSocket clientServerSocket = new ServerSocket(config.getListeningPort());
            Socket clientProxySocket = clientServerSocket.accept();
            Socket proxyServerSocket = new Socket(config.getForwardAddress(), config.getForwardPort());

            clientProxy = new SimpleTransportHandler();
            clientProxy.initialize(clientProxySocket);

            proxyServer = new SimpleTransportHandler(5);
            proxyServer.initialize(proxyServerSocket);

            LOGGER.info("Successfully started the proxy");

            while (proxyRunning) {
                LOGGER.info("Waiting for the incoming data");
                byte[] clientProxyData = clientProxy.fetchData();
                forwardedMessages.setOutputDataModified(ArrayConverter.bytesToHexString(clientProxyData));
                LOGGER.debug("Client -> Proxy: {}", forwardedMessages.getOutputDataModified());
                forwardSleep();
                clientProxyData = convertStringToByteArray(forwardedMessages.getOutputDataModified());

                proxyServer.sendData(clientProxyData);
                byte[] proxyServerData = proxyServer.fetchData();
                forwardedMessages.setInputDataModified(ArrayConverter.bytesToHexString(proxyServerData));
                LOGGER.debug("Server -> Proxy: {}", forwardedMessages.getInputDataModified());
                forwardSleep();
                proxyServerData = convertStringToByteArray(forwardedMessages.getInputDataModified());

                clientProxy.sendData(proxyServerData);
            }
            clientProxy.closeConnection();
            proxyServer.closeConnection();
            clientServerSocket.close();
            LOGGER.info("Successfully stopped the proxy");

        } catch (IOException ioe) {
            LOGGER.error(ioe);
        }
    }

    public void stopProxy() {
        proxyRunning = false;
    }

    @Override
    public void run() {
        startProxy();
    }

    private void forwardSleep() {
        forwardedMessages.setForward(false);
        while (!forwardedMessages.isForward()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {

            }
        }
    }
    
    private byte[] convertStringToByteArray(String input) {
        String message = input.replaceAll("\\s", "");
        if(message == null || message.length() == 0) {
            return new byte[0];
        }
        BigInteger bi = new BigInteger(message, 16);
        return ArrayConverter.bigIntegerToByteArray(bi);
    }
    
}
