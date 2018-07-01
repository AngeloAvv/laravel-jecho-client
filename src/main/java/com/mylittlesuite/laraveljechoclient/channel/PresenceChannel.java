/*
 * Copyright (c) 2018.
 * Author: Angelo Cassano
 */

package com.mylittlesuite.laraveljechoclient.channel;

/**
 * The interface Presence channel.
 */
public interface PresenceChannel {

    /**
     * Joining presence channel.
     *
     * @param listener the listener
     * @return the presence channel
     */
    PresenceChannel joining(final OnUserJoiningListener listener);

    /**
     * Leaving presence channel.
     *
     * @param listener the listener
     * @return the presence channel
     */
    PresenceChannel leaving(final OnUserLeavingListener listener);

    /**
     * Here presence channel.
     *
     * @param listener the listener
     * @return the presence channel
     */
    PresenceChannel here(final OnUsersHereListener listener);

    /**
     * The interface On user joining listener.
     */
    interface OnUserJoiningListener {
        /**
         * On user joining.
         *
         * @param id the id
         */
        void onUserJoining(int id);
    }

    /**
     * The interface On user leaving listener.
     */
    interface OnUserLeavingListener {
        /**
         * On user leaving.
         *
         * @param id the id
         */
        void onUserLeaving(int id);
    }

    /**
     * The interface On users here listener.
     */
    interface OnUsersHereListener {
        /**
         * On users here.
         *
         * @param ids the ids
         */
        void onUsersHere(int... ids);
    }
}
