package club.visionmc.nitrogen.event.grant;

import club.visionmc.nitrogen.grant.rank.Grant;
import club.visionmc.nitrogen.profile.Profile;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class GrantExpireEvent extends Event {

    @Getter private final Grant grant;
    @Getter private final Profile profile;

    public GrantExpireEvent(Grant grant, Profile profile){
        this.grant = grant;
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