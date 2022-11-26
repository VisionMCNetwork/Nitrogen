package club.visionmc.nitrogen.grant.rank;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.database.MongoHandler;
import club.visionmc.nitrogen.event.grant.GrantExpireEvent;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.rank.RankHandler;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.UUID;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class GrantHandler {

    @Getter private List<Grant> grants = Lists.newArrayList();

    private final MongoHandler mongoHandler = Nitrogen.getInstance().getMongoHandler();
    private final RankHandler rankHandler = Nitrogen.getInstance().getRankHandler();

    public void init(){
        int index = 0;
        for(Document document : mongoHandler.getGrants().find()){
            grants.add(parseGrant(document));
            index++;
        }
        Nitrogen.getInstance().log(ChatColor.GREEN + "Loaded a total of " + ChatColor.YELLOW + index + " grant" + (index == 1 ? "" : "s") + ChatColor.GREEN + ".");
    }

    public void recalculateGrants(){
        List<Grant> temp = Lists.newArrayList();
        for(Document document : mongoHandler.getGrants().find()){
            temp.add(parseGrant(document));
        }
        this.grants = temp;
    }

    public void refreshGrants(){
        for(Grant grant : grants){
            if(grant.isActive() && grant.getDuration() != Long.MAX_VALUE){
                if(grant.getRemainingTime() <= 0L && (grant.getRemovedReason() == null)){
                    grant.setRemovedBy(Profile.getConsoleUUID());
                    grant.setRemovedAt(System.currentTimeMillis());
                    grant.setRemovedReason("Expired");
                    grant.save();
                    Nitrogen.getInstance().getServer().getPluginManager().callEvent(new GrantExpireEvent(grant, Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(grant.getTarget())));
                }
            }
        }
    }

    public Grant parseGrant(Document document){
        return new Grant(UUID.fromString(
                document.getString("uuid")),
                UUID.fromString(document.getString("target")),
                UUID.fromString(document.getString("addedBy")),
                rankHandler.getRank(document.getString("rank")),
                document.getLong("addedAt"),
                document.getString("reason"),
                document.getLong("duration"),
                (document.getString("removedBy") == null ? null : UUID.fromString(document.getString("removedBy"))),
                (document.getLong("removedAt") == null ? 0 : document.getLong("removedAt")),
                (document.getString("removedReason") == null ? null : document.getString("removedReason")));
    }

    public Document parseGrantToDocument(Grant grant){
        return new Document("uuid", grant.getUuid().toString())
                .append("target", grant.getTarget().toString())
                .append("addedBy", grant.getAddedBy().toString())
                .append("rank", grant.getRank().getId())
                .append("addedAt", grant.getAddedAt())
                .append("reason", grant.getReason())
                .append("duration", grant.getDuration())
                .append("removedBy", (grant.getRemovedBy() == null ? null : grant.getRemovedBy().toString()))
                .append("removedAt", grant.getRemovedAt())
                .append("removedReason", (grant.getRemovedReason() == null ? null : grant.getRemovedReason()));
    }


    public void createGrant(Grant grant){
        mongoHandler.getGrants().insertOne(parseGrantToDocument(grant));
    }

}