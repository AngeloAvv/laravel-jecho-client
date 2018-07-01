/*
 * Copyright (c) 2018.
 * Author: Angelo Cassano
 */

package com.mylittlesuite.laraveljechoclient;

import com.mylittlesuite.laraveljechoclient.channel.Channel;
import com.mylittlesuite.laraveljechoclient.channel.PresenceChannel;
import com.mylittlesuite.laraveljechoclient.connector.Connector;
import com.mylittlesuite.laraveljechoclient.connector.SocketIOConnector;
import com.mylittlesuite.laraveljechoclient.models.Options;

import java.net.URISyntaxException;

/**
 * The type Echo.
 */
public class Echo {

    private Connector connector;

    private Echo() {

    }

    /**
     * Instantiates a new Echo.
     *
     * @param options the options
     */
    public Echo(Options options) {
        try {
            this.connector = new SocketIOConnector(options);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Listen channel.
     *
     * @param <T>         the type parameter
     * @param channelName the channel name
     * @param eventName   the event name
     * @param listener    the listener
     * @return the channel
     */
    public <T> Channel listen(String channelName, String eventName, Channel.OnEvent<T> listener) {
        return connector.listen(channelName, eventName, listener);
    }

    /**
     * Channel channel.
     *
     * @param channelName the channel name
     * @return the channel
     */
    public Channel channel(String channelName) {
        return connector.channel(channelName);
    }

    /**
     * Private channel channel.
     *
     * @param channelName the channel name
     * @return the channel
     */
    public Channel privateChannel(String channelName) {
        return connector.privateChannel(channelName);
    }

    /**
     * Join presence channel.
     *
     * @param channelName the channel name
     * @return the presence channel
     */
    public PresenceChannel join(String channelName) {
        return connector.presenceChannel(channelName);
    }

    /**
     * Leave.
     *
     * @param channelName the channel name
     */
    public void leave(String channelName) {
        connector.leave(channelName);
    }

    /**
     * Disconnect.
     */
    public void disconnect() {
        connector.disconnect();
    }
}
