package club.visionmc.nitrogen.event.rank;

import club.visionmc.nitrogen.rank.Rank;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class RankSaveEvent extends Event {

    @Getter
    private final Rank rank;

    public RankSaveEvent(Rank rank){
        this.rank = rank;
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
