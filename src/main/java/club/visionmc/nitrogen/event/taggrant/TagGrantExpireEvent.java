package club.visionmc.nitrogen.event.taggrant;

import club.visionmc.nitrogen.grant.rank.Grant;
import club.visionmc.nitrogen.grant.tag.TagGrant;
import club.visionmc.nitrogen.profile.Profile;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class TagGrantExpireEvent extends Event {

    @Getter
    private final TagGrant tagGrant;
    @Getter private final Profile profile;

    public TagGrantExpireEvent(TagGrant tagGrant, Profile profile){
        this.tagGrant = tagGrant;
        this.profile = profile;
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
