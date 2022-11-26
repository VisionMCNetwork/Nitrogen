package club.visionmc.nitrogen.grant.rank;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.event.grant.GrantSaveEvent;
import club.visionmc.nitrogen.rank.Rank;
import club.visionmc.nitrogen.util.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

/**
 * @author Topu
 * @date 11/26/2022
 */
@Getter
@Setter
@Data
@AllArgsConstructor
public class Grant {

    private final UUID uuid;
    private final UUID target;

    private final UUID addedBy;
    private final Rank rank;
    private final Long addedAt;
    private String reason;
    private Long duration;

    private UUID removedBy;
    private Long removedAt;
    private String removedReason;

    public long getActiveUntil(){
        return (this.duration == Long.MAX_VALUE) ? Long.MAX_VALUE : (this.addedAt + this.duration);
    }

    public boolean isActive(){
        return removedAt <= 0;
    }

    public String getRemainingText(){
        return TimeUtils.formatIntoDetailedString((int)((getActiveUntil() - System.currentTimeMillis()) / 1000L));
    }

    public long getRemainingTime(){
        return this.addedAt + this.duration - System.currentTimeMillis();
    }

    public void save(){
        Document filter = new Document("uuid", this.uuid.toString());
        Document old = Nitrogen.getInstance().getMongoHandler().getGrants().find(filter).first();
        if(old == null) return;
        Document replace = new Document("uuid", this.uuid.toString())
                .append("target", this.target.toString())
                .append("addedBy", this.addedBy.toString())
                .append("rank", this.rank.getId())
                .append("addedAt", this.addedAt)
                .append("reason", this.reason)
                .append("duration", this.duration)
                .append("removedBy", this.removedBy.toString())
                .append("removedAt", this.removedAt)
                .append("removedReason", this.removedReason);
        Nitrogen.getInstance().getMongoHandler().getGrants().replaceOne(old, replace);
        Nitrogen.getInstance().getServer().getPluginManager().callEvent(new GrantSaveEvent(this));
    }

}
