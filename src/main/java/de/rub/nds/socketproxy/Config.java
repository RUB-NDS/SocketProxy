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

import java.io.Serializable;

/**
 *
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 */
public class Config implements Serializable {
    
    private int listeningPort = 8443;

    private int forwardPort = 4433;

    private String forwardAddress = "localhost";

    public int getListeningPort() {
        return listeningPort;
    }

    public void setListeningPort(int listeningPort) {
        System.out.println("listen " + listeningPort);
        this.listeningPort = listeningPort;
    }

    public int getForwardPort() {
        return forwardPort;
    }

    public void setForwardPort(int forwardPort) {
        System.out.println("forward " + forwardPort);
        this.forwardPort = forwardPort;
    }

    public String getForwardAddress() {
        return forwardAddress;
    }

    public void setForwardAddress(String forwardAddress) {
        System.out.println("forward " + forwardAddress);
        this.forwardAddress = forwardAddress;
    }
}
