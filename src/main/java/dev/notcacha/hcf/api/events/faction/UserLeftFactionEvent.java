package dev.notcacha.hcf.api.events.faction;

import dev.notcacha.hcf.faction.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UserLeftFactionEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final Faction faction;

    public UserLeftFactionEvent(Player player, Faction faction) {
        this.player = player;
        this.faction = faction;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * @return player from this event
     */

    public Player getPlayer() {
        return player;
    }

    /**
     * @return faction from user has been left
     */

    public Faction getFaction() {
        return faction;
    }
}
