package club.visionmc.nitrogen.event.grant;

import club.visionmc.nitrogen.grant.rank.Grant;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class GrantSaveEvent extends Event {

    @Getter private final Grant grant;

    public GrantSaveEvent(Grant grant){
        this.grant = grant;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
