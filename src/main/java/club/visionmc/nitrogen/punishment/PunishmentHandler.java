package club.visionmc.nitrogen.punishment;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.database.MongoHandler;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.profile.ProfileHandler;
import club.visionmc.nitrogen.util.Chat;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.bson.Document;

import java.util.List;
import java.util.UUID;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class PunishmentHandler {

    @Getter
    private List<Punishment> punishments = Lists.newArrayList();

    private final MongoHandler mongoHandler = Nitrogen.getInstance().getMongoHandler();
    private final ProfileHandler profileHandler = Nitrogen.getInstance().getProfileHandler();

    public void init(){
        int index = 0;
        for(Document document : mongoHandler.getPunishments().find()){
            punishments.add(parsePunishment(document));
            index++;
        }
        Nitrogen.getInstance().log(Chat.LIGHT_GREEN + "Loaded a total of " + Chat.YELLOW + index + " punishment" + (index == 1 ? "" : "s") + Chat.LIGHT_GREEN + ".");
    }

    public void refreshPunishments(){
        for(Punishment punishment : punishments){
            if(punishment.isActive() && punishment.getDuration() != Long.MAX_VALUE){
                if(punishment.getRemainingTime() <= 0L && (punishment.getRemovedReason() == null)){
                    punishment.setRemovedBy(Profile.getConsoleUUID());
                    punishment.setRemovedAt(System.currentTimeMillis());
                    punishment.setRemovedReason("Expired");
                    punishment.save();
                }
            }
        }
    }



    public Punishment createPunishment(Punishment punishment){
        mongoHandler.getPunishments().insertOne(parsePunishmentToDocument(punishment));
        return punishment;
    }

    public void recalculatePunishments(){
        List<Punishment> temp = Lists.newArrayList();
        for(Document document : mongoHandler.getPunishments().find()){
            temp.add(parsePunishment(document));
        }
        this.punishments = temp;
    }

    public Punishment parsePunishment(Document document){
        return new Punishment(UUID.fromString(
                document.getString("uuid")),
                UUID.fromString(document.getString("target")),
                PunishmentType.valueOf(document.getString("punishmentType")),
                Nitrogen.getInstance().getServerHandler().getServer(document.getString("addedServer")),
                UUID.fromString(document.getString("addedBy")),
                document.getLong("addedAt"),
                document.getString("reason"),
                document.getLong("duration"),
                (List<String>) document.get("proof"),
                (document.getString("removedBy") == null ? null : UUID.fromString(document.getString("removedBy"))),
                (document.getLong("removedAt") == null ? 0 : document.getLong("removedAt")),
                (document.getString("removedReason") == null ? null : document.getString("removedReason")));
    }

    public Document parsePunishmentToDocument(Punishment punishment){
        return new Document("uuid", punishment.getUuid().toString())
                .append("target", punishment.getTarget().toString())
                .append("addedServer", punishment.getAddedServer().getName())
                .append("addedBy", punishment.getAddedBy().toString())
                .append("punishmentType", punishment.getPunishmentType().name())
                .append("addedAt", punishment.getAddedAt())
                .append("reason", punishment.getReason())
                .append("duration", punishment.getDuration())
                .append("proof", punishment.getProof())
                .append("removedBy", (punishment.getRemovedBy() == null ? null : punishment.getRemovedBy().toString()))
                .append("removedAt", punishment.getRemovedAt())
                .append("removedReason", (punishment.getRemovedReason() == null ? null : punishment.getRemovedReason()));
    }

}