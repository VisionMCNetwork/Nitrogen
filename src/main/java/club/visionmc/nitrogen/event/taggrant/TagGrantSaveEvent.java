package club.visionmc.nitrogen.event.taggrant;

import club.visionmc.nitrogen.grant.rank.Grant;
import club.visionmc.nitrogen.grant.tag.TagGrant;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class TagGrantSaveEvent extends Event {

    @Getter
    private final TagGrant tagGrant;

    public TagGrantSaveEvent(TagGrant tagGrant){
        this.tagGrant = tagGrant;
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
