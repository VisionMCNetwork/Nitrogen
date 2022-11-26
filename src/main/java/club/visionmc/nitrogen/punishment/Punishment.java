package club.visionmc.nitrogen.punishment;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.server.NitrogenServer;
import club.visionmc.nitrogen.util.TimeUtils;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.List;
import java.util.UUID;

/**
 * @author Topu
 * @date 11/26/2022
 */
@Getter @Setter
@Data
@AllArgsConstructor
public class Punishment {

    private final UUID uuid;
    private final UUID target;
    private final PunishmentType punishmentType;
    private final NitrogenServer addedServer;

    private final UUID addedBy;
    private final Long addedAt;
    private String reason;
    private Long duration;
    private List<String> proof;

    private UUID removedBy;
    private Long removedAt;
    private String removedReason;

    public long getActiveUntil(){
        return (this.duration == Long.MAX_VALUE ? Long.MAX_VALUE : (this.addedAt + this.duration));
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
        Document old = Nitrogen.getInstance().getMongoHandler().getPunishments().find(filter).first();
        if(old == null) return;
        Document replace = new Document("uuid", this.getUuid().toString())
                .append("target", this.getTarget().toString())
                .append("addedServer", this.addedServer.getName())
                .append("addedBy", this.getAddedBy().toString())
                .append("punishmentType", this.getPunishmentType().name())
                .append("addedAt", this.getAddedAt())
                .append("reason", this.getReason())
                .append("duration", this.getDuration())
                .append("proof", (this.getProof() == null ? Lists.newArrayList() : this.getProof()))
                .append("removedBy", (this.getRemovedBy() == null ? null : this.getRemovedBy().toString()))
                .append("removedAt", this.getRemovedAt())
                .append("removedReason", (this.getRemovedReason() == null ? null : this.getRemovedReason()));
        Nitrogen.getInstance().getMongoHandler().getPunishments().replaceOne(old, replace);
    }

}