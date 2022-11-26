package club.visionmc.nitrogen.profile;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.database.MongoHandler;
import club.visionmc.nitrogen.grant.rank.Grant;
import club.visionmc.nitrogen.grant.rank.GrantHandler;
import club.visionmc.nitrogen.grant.tag.TagGrantHandler;
import club.visionmc.nitrogen.rank.RankHandler;
import club.visionmc.nitrogen.util.ColorUtils;
import club.visionmc.nitrogen.util.PermissionUtils;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class ProfileHandler {

    @Getter private List<Profile> profiles = Lists.newArrayList();

    private final MongoHandler mongoHandler = Nitrogen.getInstance().getMongoHandler();
    private final RankHandler rankHandler = Nitrogen.getInstance().getRankHandler();
    private final GrantHandler grantHandler = Nitrogen.getInstance().getGrantHandler();
    private final TagGrantHandler tagGrantHandler = Nitrogen.getInstance().getTagGrantHandler();

    public void init(){
        int index = 0;
        for(Document document : mongoHandler.getProfiles().find()) {
            Profile profile = parseProfile(document);
            profiles.add(profile);
            profile.save();
            index++;
        }
        Nitrogen.getInstance().log(ChatColor.GREEN + "Loaded a total of " + ChatColor.YELLOW + index + " profile" + (index == 1 ? "" : "s") + ChatColor.GREEN + ".");
    }

    public void recalculateProfiles(){
        List<Profile> temp = Lists.newArrayList();
        for(Document document : mongoHandler.getProfiles().find()){
            temp.add(parseProfile(document));
        }
        this.profiles = temp;
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            PermissionUtils.updatePermissions(player);
            player.setDisplayName(ColorUtils.convertToChatColor(Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(player.getUniqueId()).getHighestRank().getColor()) + player.getName());
        }
    }

    public boolean profileExists(UUID uuid){
        for(Profile profile : profiles){
            if(profile.getUuid().toString().equalsIgnoreCase(uuid.toString())){
                return true;
            }
        }
        return false;
    }

    public void createProfile(UUID uuid){
        if(profileExists(uuid)) return;
        Profile profile = new Profile(uuid, null, Nitrogen.getInstance().getServer().getOfflinePlayer(uuid).getName());
        Document document = new Document("uuid", profile.getUuid().toString())
                .append("activeTag", null)
                .append("username", profile.getUsername());
        mongoHandler.getProfiles().insertOne(document);
        grantHandler.createGrant(new Grant(UUID.randomUUID(), uuid, Profile.getConsoleUUID(), rankHandler.getRank("default"), System.currentTimeMillis(), "Default Rank", Long.MAX_VALUE, null, null, null));
    }

    public void deleteProfile(Profile profile){
        if(!profileExists(profile.getUuid()) || profile.isConsole()) return;
        Document filter = new Document("uuid", profile.getUuid().toString());
        Document document = mongoHandler.getProfiles().find(filter).first();
        if(document == null) return;
        mongoHandler.getProfiles().deleteOne(document);
    }

    public Profile getProfile(UUID uuid){
        for(Profile profile : profiles){
            if(profile.getUuid().toString().equalsIgnoreCase(uuid.toString())){
                return profile;
            }
        }
        return null;
    }

    public Profile getProfileOrCreate(UUID uuid){
        if(!profileExists(uuid)){
            createProfile(uuid);
        }
        for(Profile profile : profiles){
            if(profile.getUuid().toString().equalsIgnoreCase(uuid.toString())){
                return profile;
            }
        }
        return null;
    }

    public Profile parseProfile(Document document){
        return new Profile(UUID.fromString(document.getString("uuid")), (document.getString("activeTag") == null ? null : Nitrogen.getInstance().getTagHandler().getTag(document.getString("activeTag"))), Nitrogen.getInstance().getServer().getOfflinePlayer(UUID.fromString(document.getString("uuid"))).getName());
    }

    public Document parseProfileToDocument(Profile profile){
        return new Document("uuid", profile.getUuid().toString())
                .append("activeTag", (profile.getActiveTag() == null ? null : profile.getActiveTag().getId()))
                .append("username", (profile.getUsername() == null ? "" : profile.getUsername()));
    }

}