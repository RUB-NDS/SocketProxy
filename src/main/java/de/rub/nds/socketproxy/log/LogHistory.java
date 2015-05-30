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
package de.rub.nds.socketproxy.log;

import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 */
public class LogHistory implements Serializable {

    public static final String PROP_LOG = "log";
    private String log = "";
    private final PropertyChangeSupport propertySupport;

    public LogHistory() {
        propertySupport = new PropertyChangeSupport(this);
    }

    public String getLog() {
        return log;
    }

    public void setLog(String value) {
        String oldValue = log;
        log = value;
        propertySupport.firePropertyChange(PROP_LOG, oldValue, log);
    }

    public void appendLog(String value) {
        String oldValue = log;
        log = log + value;
        propertySupport.firePropertyChange(PROP_LOG, oldValue, log);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
}
