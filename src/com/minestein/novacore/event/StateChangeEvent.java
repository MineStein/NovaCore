package com.minestein.novacore.event;

import com.minestein.novacore.Core;
import com.minestein.novacore.util.general.State;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class StateChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private State from;
    private State to;

    public StateChangeEvent() {
        from = Core.getState();

        switch (from) {
            case LOBBY:
                to = State.PREGAME;
                break;
            case PREGAME:
                to = State.WARMUP;
                break;
            case WARMUP:
                to = State.INGAME;
                break;
            case INGAME:
                to = State.RESTARTING;
                break;
        }
    }

    public State getFrom() {
        return from;
    }

    public State getTo() {
        return to;
    }

    public State getState() {
        return Core.getState();
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
