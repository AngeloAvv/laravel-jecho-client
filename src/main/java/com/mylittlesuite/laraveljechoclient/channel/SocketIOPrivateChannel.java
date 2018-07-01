/*
 * Copyright (c) 2018.
 * Author: Angelo Cassano
 */

package com.mylittlesuite.laraveljechoclient.channel;

import com.github.nkzawa.socketio.client.Socket;
import com.mylittlesuite.laraveljechoclient.models.Options;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The type Socket io private channel.
 */
public class SocketIOPrivateChannel extends SocketIOChannel {

    /**
     * Instantiates a new Socket io private channel.
     *
     * @param socket      the socket
     * @param channelName the channel name
     * @param options     the options
     */
    public SocketIOPrivateChannel(Socket socket, String channelName, Options options) {
        super(socket, channelName, options);
    }

    /**
     * Whisper socket io private channel.
     *
     * @param <T>       the type parameter
     * @param eventName the event name
     * @param data      the data
     * @return the socket io private channel
     */
    public <T> SocketIOPrivateChannel whisper(String eventName, T data) {
        this.socket.emit("client event", buildPayload(channelName, eventName, data));

        return this;
    }

    private static <T> JSONObject buildPayload(String channelName, String eventName, T data) {
        JSONObject root = new JSONObject();
        try {
            root.put("channel", channelName);
            root.put("event", "client-" + eventName);
            root.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root;
    }

}
