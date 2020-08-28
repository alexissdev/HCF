package dev.notcacha.hcf.api.events.faction;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionCreateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final String factionName;

    public FactionCreateEvent(Player player, String factionName) {
        this.player = player;
        this.factionName = factionName;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * @return player has been created faction
     */

    public Player getPlayer() {
        return player;
    }

    /**
     * @return name from faction has been created!
     */

    public String getFactionName() {
        return factionName;
    }
}
