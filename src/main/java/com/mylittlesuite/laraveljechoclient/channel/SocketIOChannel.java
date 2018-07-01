/*
 * Copyright (c) 2018.
 * Author: Angelo Cassano
 */

package com.mylittlesuite.laraveljechoclient.channel;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.mylittlesuite.laraveljechoclient.models.Options;
import com.mylittlesuite.laraveljechoclient.utils.EventFormatter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Socket io channel.
 */
public class SocketIOChannel extends Channel {

    /**
     * The Socket.
     */
    protected Socket socket;

    /**
     * The Channel name.
     */
    protected String channelName;

    private Options options;

    private Map<String, Emitter.Listener> events;

    /**
     * Instantiates a new Socket io channel.
     *
     * @param socket      the socket
     * @param channelName the channel name
     * @param options     the options
     */
    public SocketIOChannel(Socket socket,
                           String channelName,
                           Options options) {
        this.events = new HashMap<>();

        this.socket = socket;
        this.channelName = channelName;
        this.options = options;

        this.subscribe();
        this.configureReconnector();
    }

    @Override
    public <T> SocketIOChannel listen(String eventName, OnEvent<T> callback) {
        this.on(new EventFormatter(options.namespace).format(eventName), callback);

        return this;
    }

    /**
     * Subscribe socket io channel.
     *
     * @return the socket io channel
     */
    public SocketIOChannel subscribe() {
        this.socket.emit("subscribe", buildPayload(channelName, options.token));

        return this;
    }

    /**
     * On.
     *
     * @param <T>       the type parameter
     * @param eventName the  on(event name
     * @param callback  the callback
     */
    <T> void on(String eventName, final OnEvent<T> callback) {
        Emitter.Listener listener = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (SocketIOChannel.this.channelName.equals(args[0])) {
                    callback.onEvent((T) args[1]);
                }
            }
        };


        this.socket.on(eventName, listener);
        this.bind(eventName, listener);
    }

    /**
     * Unsubscribe.
     */
    public void unsubscribe() {
        this.unbind();

        this.socket.emit("unsubscribe", buildPayload(channelName, options.token));
    }

    private void configureReconnector() {
        Emitter.Listener listener = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                SocketIOChannel.this.subscribe();
            }
        };

        this.socket.on(Socket.EVENT_RECONNECT, listener);
        this.bind(Socket.EVENT_RECONNECT, listener);
    }

    private void bind(String eventName, Emitter.Listener listener) {
        this.events.put(eventName, listener);
    }

    private void unbind() {
        for (String s : this.events.keySet()) {
            this.socket.off(s);
        }

        this.events.clear();
    }

    private static JSONObject buildPayload(String channelName, String token) {
        JSONObject root = null;
        try {
            JSONObject headers = new JSONObject();
            headers.put("Authorization", "Bearer " + token);

            JSONObject auth = new JSONObject();
            auth.put("headers", headers);

            root = new JSONObject();
            root.put("channel", channelName);
            root.put("auth", auth);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root;

    }
}
