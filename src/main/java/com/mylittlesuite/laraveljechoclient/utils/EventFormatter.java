/*
 * Copyright (c) 2018.
 * Author: Angelo Cassano
 */

package com.mylittlesuite.laraveljechoclient.utils;

/**
 * The type Event formatter.
 */
public class EventFormatter {

    private String namespace;

    /**
     * Instantiates a new Event formatter.
     *
     * @param namespace the namespace
     */
    public EventFormatter(String namespace) {
        this.namespace = namespace;
    }

    /**
     * Format string.
     *
     * @param event the event
     * @return the string
     */
    public String format(String event) {
        if (event.charAt(0) == '.' || event.charAt(0) == '\\') {
            return event.substring(1);
        } else if (!this.namespace.isEmpty()) {
            event = this.namespace + '.' + event;
        }

        return event.replace("/\\./g", "\\");
    }

}
