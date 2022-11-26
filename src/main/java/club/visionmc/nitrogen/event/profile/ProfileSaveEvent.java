package club.visionmc.nitrogen.event.profile;

import club.visionmc.nitrogen.profile.Profile;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class ProfileSaveEvent extends Event {

    @Getter private final Profile profile;

    public ProfileSaveEvent(Profile profile){
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

