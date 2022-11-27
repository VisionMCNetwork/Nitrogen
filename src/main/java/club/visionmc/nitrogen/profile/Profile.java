package club.visionmc.nitrogen.profile;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.event.profile.ProfileSaveEvent;
import club.visionmc.nitrogen.grant.rank.Grant;
import club.visionmc.nitrogen.grant.tag.TagGrant;
import club.visionmc.nitrogen.punishment.Punishment;
import club.visionmc.nitrogen.punishment.PunishmentType;
import club.visionmc.nitrogen.rank.Rank;
import club.visionmc.nitrogen.tag.Tag;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author Topu
 * @date 11/26/2022
 */
@Getter @Setter @Data
@AllArgsConstructor
public class Profile {

    @Getter public static UUID consoleUUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private UUID uuid;
    private Tag activeTag;
    private String username;

    public boolean isConsole(){
        return uuid.toString().equalsIgnoreCase(consoleUUID.toString());
    }

    public Rank getHighestRank(){
        Rank highest = Nitrogen.getInstance().getRankHandler().getRank("default");
        for(Grant grant : getGrants()){
            if(grant.getRank().getPriority() > highest.getPriority() && grant.getRemovedAt() <= 0){
                highest = grant.getRank();
            }
        }
        return highest;
    }

    public Tag getHighestTag(){
        Tag highest = Nitrogen.getInstance().getTagHandler().getTag("default");
        for(TagGrant grant : getTagGrants()){
            if(grant.getTag().getPriority() > highest.getPriority() && grant.getRemovedAt() <= 0){
                highest = grant.getTag();
            }
        }
        return highest;
    }

    public boolean isOnline(){
        return (Nitrogen.getInstance().getServer().getPlayer(uuid) != null);
    }

    public boolean hasPermission(String permission){
        List<String> permissions = Lists.newArrayList();
        for(Grant grant : getGrants()){
            if(grant.isActive()){
                if (!permissions.contains(permission)) {
                    permissions.add(permission);
                }
            }
        }
        return permissions.contains(permission);
    }

    public List<String> getPermissions(){
        List<String> perms = Lists.newArrayList();
        for(Grant grant : getGrants()){
            if(grant.isActive()) {
                for (String perm : grant.getRank().getPermissions()) {
                    if (!perms.contains(perm)) {
                        perms.add(perm);
                    }
                }
            }
        }
        return perms;
    }

    public List<Grant> getGrants(){
        List<Grant> grants = Lists.newArrayList();
        for(Grant grant : Nitrogen.getInstance().getGrantHandler().getGrants()){
            if(grant.getTarget().toString().equalsIgnoreCase(uuid.toString())){
                grants.add(grant);
            }
        }
        return grants;
    }

    public List<TagGrant> getTagGrants(){
        List<TagGrant> tagGrants = Lists.newArrayList();
        for(TagGrant tagGrant : Nitrogen.getInstance().getTagGrantHandler().getTagGrants()){
            if(tagGrant.getTarget().toString().equalsIgnoreCase(uuid.toString())){
                tagGrants.add(tagGrant);
            }
        }
        return tagGrants;
    }

    public boolean hasActivePunishment(PunishmentType punishmentType){
        for(Punishment punishment : getActivePunishments()){
            if(punishment.getPunishmentType() == punishmentType){
                return true;
            }
        }
        return false;
    }

    public List<Punishment> getPunishments(){
        List<Punishment> punishments = Lists.newArrayList();
        for(Punishment punishment : Nitrogen.getInstance().getPunishmentHandler().getPunishments()){
            if(punishment.getTarget().toString().equalsIgnoreCase(uuid.toString())){
                punishments.add(punishment);
            }
        }
        return punishments;
    }

    public List<Punishment> getPunishments(PunishmentType punishmentType){
        List<Punishment> punishments = Lists.newArrayList();
        for(Punishment punishment : Nitrogen.getInstance().getPunishmentHandler().getPunishments()){
            if(punishment.getTarget().toString().equalsIgnoreCase(uuid.toString()) && punishment.getPunishmentType() == punishmentType){
                punishments.add(punishment);
            }
        }
        return punishments;
    }

    public List<Punishment> getActivePunishments(){
        List<Punishment> punishments = Lists.newArrayList();
        for(Punishment punishment : Nitrogen.getInstance().getPunishmentHandler().getPunishments()){
            if(punishment.getTarget().toString().equalsIgnoreCase(uuid.toString()) && punishment.getRemovedAt() <= 0){
                punishments.add(punishment);
            }
        }
        return punishments;
    }

    public boolean hasActiveTag(){
        return activeTag != null;
    }

    public List<Punishment> getActivePunishments(PunishmentType punishmentType){
        List<Punishment> punishments = Lists.newArrayList();
        for(Punishment punishment : Nitrogen.getInstance().getPunishmentHandler().getPunishments()){
            if(punishment.getTarget().toString().equalsIgnoreCase(uuid.toString()) && punishment.getRemovedAt() <= 0 && punishment.getPunishmentType() == punishmentType){
                punishments.add(punishment);
            }
        }
        return punishments;
    }

    public void save(){
        Document filter = new Document("uuid", this.uuid.toString());
        Document old = Nitrogen.getInstance().getMongoHandler().getProfiles().find(filter).first();
        if(old == null) return;
        Document replace = new Document("uuid", this.uuid.toString())
                .append("activeTag", (this.activeTag == null ? null : this.activeTag.getId()))
                .append("username", (this.username == null || this.username.equals("") ? Nitrogen.getInstance().getServer().getOfflinePlayer(uuid).getName() : username));
        Nitrogen.getInstance().getMongoHandler().getProfiles().replaceOne(old, replace);
        Nitrogen.getInstance().getServer().getPluginManager().callEvent(new ProfileSaveEvent(this));
    }

}