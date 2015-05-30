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

import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 */
public class ForwardedMessages implements Serializable {

    public static final String PROP_OUTPUT_DATA_MODIFIED = "outputDataModified";
    public static final String PROP_INPUT_DATA_MODIFIED = "inputDataModified";
    public static final String PROP_FORWARD = "forward";
    private String outputDataModified = "output";
    private String inputDataModified = "input";
    private boolean forward;
    private PropertyChangeSupport propertySupport;

    public ForwardedMessages() {
        propertySupport = new PropertyChangeSupport(this);
    }

    public String getOutputDataModified() {
        return outputDataModified;
    }

    public void setOutputDataModified(String outputDataModified) {
        String oldValue = this.outputDataModified;
        this.outputDataModified = outputDataModified;
        propertySupport.firePropertyChange(PROP_OUTPUT_DATA_MODIFIED, oldValue, this.outputDataModified);
    }

    public String getInputDataModified() {
        return inputDataModified;
    }

    public void setInputDataModified(String inputDataModified) {
        String oldValue = this.inputDataModified;
        this.inputDataModified = inputDataModified;
        propertySupport.firePropertyChange(PROP_INPUT_DATA_MODIFIED, oldValue, this.inputDataModified);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

    public boolean isForward() {
        return forward;
    }

    public void setForward(boolean forward) {
        boolean oldValue = this.forward;
        this.forward = forward;
        propertySupport.firePropertyChange(PROP_FORWARD, oldValue, forward);
    }
    
    public boolean isForwardEnabled() {
        return !forward;
    }
}
