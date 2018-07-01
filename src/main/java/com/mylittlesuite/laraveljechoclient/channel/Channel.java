/*
 * Copyright (c) 2018.
 * Author: Angelo Cassano
 */

package com.mylittlesuite.laraveljechoclient.channel;

/**
 * The type Channel.
 */
public abstract class Channel {

    /**
     * Listen channel.
     *
     * @param <T>       the type parameter
     * @param eventName the event name
     * @param callback  the callback
     * @return the channel
     */
    public abstract <T> Channel listen(String eventName, OnEvent<T> callback);

    /**
     * Listen for whisper channel.
     *
     * @param <T>       the type parameter
     * @param eventName the event name
     * @param callback  the callback
     * @return the channel
     */
    public <T> Channel listenForWhisper(String eventName, OnEvent<T> callback) {
        return listen(".client-" + eventName, callback);
    }

    /**
     * The interface On event.
     *
     * @param <T> the type parameter
     */
    public interface OnEvent<T> {
        /**
         * On event.
         *
         * @param arg the arg
         */
        void onEvent(T arg);
    }
}
