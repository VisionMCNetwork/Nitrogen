package club.visionmc.nitrogen.grant.tag;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.database.MongoHandler;
import club.visionmc.nitrogen.event.grant.GrantExpireEvent;
import club.visionmc.nitrogen.event.taggrant.TagGrantExpireEvent;
import club.visionmc.nitrogen.grant.rank.Grant;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.rank.RankHandler;
import club.visionmc.nitrogen.tag.TagHandler;
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
public class TagGrantHandler {

    @Getter
    private List<TagGrant> tagGrants = Lists.newArrayList();

    private final MongoHandler mongoHandler = Nitrogen.getInstance().getMongoHandler();
    private final TagHandler tagHandler = Nitrogen.getInstance().getTagHandler();

    public void init(){
        int index = 0;
        for(Document document : mongoHandler.getTagGrants().find()){
            tagGrants.add(parseTagGrant(document));
            index++;
        }
        Nitrogen.getInstance().log(ChatColor.GREEN + "Loaded a total of " + ChatColor.YELLOW + index + " tag grant" + (index == 1 ? "" : "s") + ChatColor.GREEN + ".");
    }

    public void recalculateTagGrants(){
        List<TagGrant> temp = Lists.newArrayList();
        for(Document document : mongoHandler.getTagGrants().find()){
            temp.add(parseTagGrant(document));
        }
        this.tagGrants = temp;
    }

    public void refreshTagGrants(){
        for(TagGrant tagGrant : tagGrants){
            if(tagGrant.isActive() && tagGrant.getDuration() != Long.MAX_VALUE){
                if(tagGrant.getRemainingTime() <= 0L && (tagGrant.getRemovedReason() == null)){
                    tagGrant.setRemovedBy(Profile.getConsoleUUID());
                    tagGrant.setRemovedAt(System.currentTimeMillis());
                    tagGrant.setRemovedReason("Expired");
                    tagGrant.save();
                    Nitrogen.getInstance().getServer().getPluginManager().callEvent(new TagGrantExpireEvent(tagGrant, Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(tagGrant.getTarget())));
                }
            }
        }
    }

    public TagGrant parseTagGrant(Document document){
        return new TagGrant(UUID.fromString(
                document.getString("uuid")),
                UUID.fromString(document.getString("target")),
                UUID.fromString(document.getString("addedBy")),
                tagHandler.getTag(document.getString("tag")),
                document.getLong("addedAt"),
                document.getString("reason"),
                document.getLong("duration"),
                (document.getString("removedBy") == null ? null : UUID.fromString(document.getString("removedBy"))),
                (document.getLong("removedAt") == null ? 0 : document.getLong("removedAt")),
                (document.getString("removedReason") == null ? null : document.getString("removedReason")));
    }

    public Document parseTagGrantToDocument(TagGrant tagGrant){
        return new Document("uuid", tagGrant.getUuid().toString())
                .append("target", tagGrant.getTarget().toString())
                .append("addedBy", tagGrant.getAddedBy().toString())
                .append("tag", tagGrant.getTag().getId())
                .append("addedAt", tagGrant.getAddedAt())
                .append("reason", tagGrant.getReason())
                .append("duration", tagGrant.getDuration())
                .append("removedBy", (tagGrant.getRemovedBy() == null ? null : tagGrant.getRemovedBy().toString()))
                .append("removedAt", tagGrant.getRemovedAt())
                .append("removedReason", (tagGrant.getRemovedReason() == null ? null : tagGrant.getRemovedReason()));
    }


    public void createTagGrant(TagGrant tagGrant){
        mongoHandler.getGrants().insertOne(parseTagGrantToDocument(tagGrant));
    }

}
