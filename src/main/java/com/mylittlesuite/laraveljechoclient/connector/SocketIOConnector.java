/*
 * Copyright (c) 2018.
 * Author: Angelo Cassano
 */

package com.mylittlesuite.laraveljechoclient.connector;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.mylittlesuite.laraveljechoclient.channel.Channel;
import com.mylittlesuite.laraveljechoclient.channel.SocketIOChannel;
import com.mylittlesuite.laraveljechoclient.channel.SocketIOPresenceChannel;
import com.mylittlesuite.laraveljechoclient.channel.SocketIOPrivateChannel;
import com.mylittlesuite.laraveljechoclient.models.Options;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Socket io connector.
 */
public class SocketIOConnector extends Connector {

    private Options options;
    private Socket socket;

    private Map<String, SocketIOChannel> channels;

    /**
     * Instantiates a new Socket io connector.
     *
     * @param options the options
     * @throws URISyntaxException the uri syntax exception
     */
    public SocketIOConnector(Options options) throws URISyntaxException {
        this.channels = new HashMap<>();

        this.options = options;
        this.socket = IO.socket(options.url);

        this.connect();
    }

    @Override
    public void connect() {
        socket.connect();
    }

    public <T> SocketIOChannel listen(String channelName, String eventName, Channel.OnEvent<T> callback) {
        return this.channel(channelName).listen(eventName, callback);
    }

    @Override
    public SocketIOChannel channel(String channelName) {
        if (!this.channels.containsKey("private-" + channelName)) {
            this.channels.put(channelName,
                    new SocketIOChannel(socket, channelName, options)
            );
        }

        return this.channels.get(channelName);
    }

    @Override
    public SocketIOPrivateChannel privateChannel(String channelName) {
        if (!this.channels.containsKey("private-" + channelName)) {
            this.channels.put("private-" + channelName,
                    new SocketIOPrivateChannel(socket, "private-" + channelName, options)
            );
        }

        return (SocketIOPrivateChannel) this.channels.get("private-" + channelName);
    }

    @Override
    public SocketIOPresenceChannel presenceChannel(String channelName) {
        if (!this.channels.containsKey("presence-" + channelName)) {
            this.channels.put("presence-" + channelName,
                    new SocketIOPresenceChannel(socket, "presence-" + channelName, options)
            );
        }

        return (SocketIOPresenceChannel) this.channels.get("presence-" + channelName);
    }

    @Override
    public void leave(String channelName) {
        String[] channelNames = {channelName, "private-" + channelName, "presence-" + channelName};

        for (String name : channelNames) {
            if (this.channels.containsKey(name)) {
                this.channels.get(name).unsubscribe();
                this.channels.remove(name);
            }
        }
    }

    @Override
    public void disconnect() {
        this.socket.disconnect();
    }
}
