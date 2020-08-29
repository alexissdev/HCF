package dev.notcacha.hcf.api.events.user;

import dev.notcacha.hcf.user.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UserLeftEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final User user;

    public UserLeftEvent(User user) {
        this.user = user;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * @return {@link User} has been un-registry
     */

    public User getUser() {
        return user;
    }
}
