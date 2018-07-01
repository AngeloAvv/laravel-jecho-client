/*
 * Copyright (c) 2018.
 * Author: Angelo Cassano
 */

package com.mylittlesuite.laraveljechoclient.channel;

import com.github.nkzawa.socketio.client.Socket;
import com.mylittlesuite.laraveljechoclient.models.Options;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The type Socket io presence channel.
 */
public class SocketIOPresenceChannel extends SocketIOPrivateChannel implements PresenceChannel {

    /**
     * Instantiates a new Socket io presence channel.
     *
     * @param socket      the socket
     * @param channelName the channel name
     * @param options     the options
     */
    public SocketIOPresenceChannel(Socket socket, String channelName, Options options) {
        super(socket, channelName, options);
    }

    public SocketIOPresenceChannel joining(final OnUserJoiningListener listener) {
        this.on("presence:joining", new Channel.OnEvent<JSONObject>() {

            @Override
            public void onEvent(JSONObject object) {
                listener.onUserJoining(object.optInt("user_id"));
            }
        });

        return this;
    }

    public SocketIOPresenceChannel leaving(final OnUserLeavingListener listener) {
        this.on("presence:leaving", new Channel.OnEvent<JSONObject>() {

            @Override
            public void onEvent(JSONObject object) {
                listener.onUserLeaving(object.optInt("user_id"));
            }
        });

        return this;
    }

    public SocketIOPresenceChannel here(final OnUsersHereListener listener) {
        this.on("presence:subscribed", new OnEvent<JSONArray>() {

            @Override
            public void onEvent(JSONArray objects) {
                int[] ids = new int[objects.length()];

                for (int i = 0; i < objects.length(); i++) {
                    ids[i] = objects.optJSONObject(i).optInt("user_id");
                }

                listener.onUsersHere(ids);
            }
        });

        return this;
    }
}
