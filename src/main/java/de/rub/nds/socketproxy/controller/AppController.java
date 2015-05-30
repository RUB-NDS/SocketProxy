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
package de.rub.nds.socketproxy.controller;

import de.rub.nds.socketproxy.Config;
import de.rub.nds.socketproxy.ForwardedMessages;
import de.rub.nds.socketproxy.Proxy;
import de.rub.nds.socketproxy.log.LogHistory;

/**
 *
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 * @version 0.1
 */
public class AppController {
    
    private static LogHistory logHistory;
    
    private static Config config;
    
    private static Proxy proxy;
    
    private static ForwardedMessages forwardedMessages;
    
    public LogHistory getLogHistory() {
        if(logHistory == null) {
            logHistory = new LogHistory();
        }
        return logHistory;
    }
    
    public Config getConfig() {
        if(config == null) {
            config = new Config();
        }
        return config;
    }
    
    public Proxy getProxy() {
        if(proxy == null) {
            proxy = new Proxy();
        }
        return proxy;
    }
    
    public ForwardedMessages getForwardedMessages() {
        if(forwardedMessages == null) {
            forwardedMessages = new ForwardedMessages();
        }
        return forwardedMessages;
    }
}
