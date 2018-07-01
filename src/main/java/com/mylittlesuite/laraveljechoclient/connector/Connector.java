/*
 * Copyright (c) 2018.
 * Author: Angelo Cassano
 */

package com.mylittlesuite.laraveljechoclient.connector;


import com.mylittlesuite.laraveljechoclient.channel.Channel;
import com.mylittlesuite.laraveljechoclient.channel.PresenceChannel;

/**
 * The type Connector.
 */
public abstract class Connector {

    /**
     * Instantiates a new Connector.
     */
    Connector() {
    }


    /**
     * Connect.
     */
    public abstract void connect();

    /**
     * Channel channel.
     *
     * @param channelName the channel name
     * @return the channel
     */
    public abstract Channel channel(String channelName);

    /**
     * Private channel channel.
     *
     * @param channelName the channel name
     * @return the channel
     */
    public abstract Channel privateChannel(String channelName);

    /**
     * Presence channel presence channel.
     *
     * @param channelName the channel name
     * @return the presence channel
     */
    public abstract PresenceChannel presenceChannel(String channelName);

    /**
     * Leave.
     *
     * @param channelName the channel name
     */
    public abstract void leave(String channelName);

    /**
     * Disconnect.
     */
    public abstract void disconnect();

    /**
     * Listen channel.
     *
     * @param <T>         the type parameter
     * @param channelName the channel name
     * @param eventName   the event name
     * @param listener    the listener
     * @return the channel
     */
    public abstract <T> Channel listen(String channelName, String eventName, Channel.OnEvent<T> listener);

}
