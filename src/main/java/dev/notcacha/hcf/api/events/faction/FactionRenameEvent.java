package dev.notcacha.hcf.api.events.faction;

import dev.notcacha.hcf.faction.Faction;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionRenameEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Faction faction;
    private final String author;

    public FactionRenameEvent(Faction faction, String author) {
        this.faction = faction;
        this.author = author;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * @return faction has been change name
     */

    public Faction getFaction() {
        return faction;
    }

    /**
     * @return author from name
     */

    public String getAuthor() {
        return author;
    }
}
