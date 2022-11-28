package club.visionmc.nitrogen.staff;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.punishment.PunishmentType;
import club.visionmc.nitrogen.redis.PacketListener;
import club.visionmc.nitrogen.redis.RedisListener;
import club.visionmc.nitrogen.server.NitrogenServer;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.xeon.util.UUIDUtils;
import com.google.gson.JsonObject;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class StaffPacketListener extends RedisListener {

    @PacketListener(identifier = "staff-chat")
    public void onSendStaffMessage(JsonObject object) {
        String sender = object.get("sender").getAsString();
        String server = object.get("server").getAsString();
        String message = object.get("message").getAsString();
        Profile profile = Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(UUIDUtils.uuid(sender));
        Nitrogen.getInstance().getServer().getOnlinePlayers().stream().filter(target -> target.hasPermission("Nitrogen.staff")).forEach(target -> target.sendMessage(Chat.format("&9[Staff] &7(" + server + ") &r" + Chat.format(profile.getHighestRank().getColor() + profile.getUsername()) + "&7: &b") + message));
        Nitrogen.getInstance().getServer().getConsoleSender().sendMessage(Chat.format("&9[Staff] &7(" + server + ") &r" + Chat.format(profile.getHighestRank().getColor() + profile.getUsername()) + "&7: &b") + message);
    }

    @PacketListener(identifier = "admin-chat")
    public void onSendAdminMessage(JsonObject object) {
        String sender = object.get("sender").getAsString();
        String server = object.get("server").getAsString();
        String message = object.get("message").getAsString();
        Profile profile = Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(UUIDUtils.uuid(sender));
        Nitrogen.getInstance().getServer().getOnlinePlayers().stream().filter(target -> target.hasPermission("Nitrogen.admin")).forEach(target -> target.sendMessage(Chat.format("&c[Admin] &7(" + server + ") &r" + Chat.format(profile.getHighestRank().getColor() + profile.getUsername()) + "&7: &b") + message));
        Nitrogen.getInstance().getServer().getConsoleSender().sendMessage(Chat.format("&c[Admin] &7(" + server + ") &r" + Chat.format(profile.getHighestRank().getColor() + profile.getUsername()) + "&7: &b") + message);
    }


    @PacketListener(identifier = "request")
    public void onStaffRequest(JsonObject object) {
        String sender = object.get("player").getAsString();
        String server = object.get("server").getAsString();
        String message = object.get("message").getAsString();
        Profile profile = Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(UUIDUtils.uuid(sender));
        Nitrogen.getInstance().getServer().getOnlinePlayers().stream().filter(target -> target.hasPermission("Nitrogen.staff")).forEach(target -> {
            target.sendMessage(Chat.format("&9[Request] &7(" + server + ")&r " + Chat.format(profile.getHighestRank().getColor() + profile.getUsername()) + " &bhas requested assistance&7: &r") + message);
        });
        Nitrogen.getInstance().getServer().getConsoleSender().sendMessage(Chat.format("&9[Request] &7(" + server + ")&r " + Chat.format(profile.getHighestRank().getColor() + profile.getUsername()) + " &bhas requested assistance&7: &r") + message);
    }

    @PacketListener(identifier = "report")
    public void onStaffReport(JsonObject object) {
        String sender = object.get("player").getAsString();
        String reported = object.get("target").getAsString();
        String server = object.get("server").getAsString();
        String message = object.get("message").getAsString();
        Profile profile = Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(UUIDUtils.uuid(sender));
        Profile reportedProfile = Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(UUIDUtils.uuid(reported));
        Nitrogen.getInstance().getServer().getOnlinePlayers().stream().filter(target -> target.hasPermission("Nitrogen.staff")).forEach(target -> {
            target.sendMessage(Chat.format("&9[Request] &7(" + server + ")&r " + Chat.format(profile.getHighestRank().getColor() + profile.getUsername()) + " &bhas reported " + reportedProfile.getHighestRank().getColor() + reportedProfile.getUsername() + "&7: &r") + message);
        });
        Nitrogen.getInstance().getServer().getConsoleSender().sendMessage(Chat.format("&9[Request] &7(" + server + ")&r " + Chat.format(profile.getHighestRank().getColor() + profile.getUsername()) + " &bhas reported " + reportedProfile.getHighestRank().getColor() + reportedProfile.getUsername() + "&7: &r") + message);
    }

    @PacketListener(identifier = "punishment-packet")
    public void onPunishment(JsonObject object){
        UUID sender = UUID.fromString(object.get("addedBy").getAsString());
        UUID target = UUID.fromString(object.get("target").getAsString());
        PunishmentType punishmentType = PunishmentType.valueOf(object.get("punishmentType").getAsString());
        NitrogenServer server = Nitrogen.getInstance().getServerHandler().getServer(object.get("server").getAsString());
        String reason = object.get("reason").getAsString();
        long duration = object.get("duration").getAsLong();
        String remainingText = object.get("remainingText").getAsString();
        boolean announce = object.get("announce").getAsBoolean();

        Profile targetProfile = Nitrogen.getInstance().getProfileHandler().getProfile(target);
        Profile senderProfile = Nitrogen.getInstance().getProfileHandler().getProfile(sender);

        String targetDisplay = (targetProfile == null ? Chat.format("&4&lConsole") : targetProfile.getHighestRank().getColor() + targetProfile.getUsername());
        String senderDisplay = (senderProfile == null ? Chat.format("&4&lConsole") : senderProfile.getHighestRank().getColor() + senderProfile.getUsername());

        if(targetProfile.getHighestRank().getPriority() > senderProfile.getHighestRank().getPriority()){
            Player staff = Bukkit.getServer().getPlayer(targetProfile.getUuid());
            if(staff != null){
                staff.sendMessage(Chat.format("&cYou do not have permission to issue a punishment on this player."));
                Bukkit.getServer().getScheduler().runTaskLater(Nitrogen.getInstance(), () -> {
                    Nitrogen.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "tempban " + staff.getName() + " 30d Potentially compromised account.");
                }, 10L);
            }
            return;
        }

        Nitrogen.getInstance().getServer().getOnlinePlayers().stream().filter(target2 -> target2.hasPermission("nitrogen.staff")).forEach(target2 -> {
            FancyMessage fancyMessage = new FancyMessage();
            fancyMessage.tooltip(Chat.format("&6&m-----------------------------------"));
            if(duration == Long.MAX_VALUE) {
                fancyMessage.text(Chat.format((!announce ? "&7[Silent] &r" : "") + senderDisplay + "&r &ahas permanently " + punishmentType.getMessageDisplay() + " &r" + targetDisplay + "&r&a."));
                fancyMessage.tooltip(Chat.format("&6&m-----------------------------------"), Chat.format("&eReason&7: &f") + reason + ChatColor.GRAY + " (" + server.getName() + ")", Chat.format("&6&m-----------------------------------"));
            }else{
                fancyMessage.text(Chat.format((!announce ? "&7[Silent] &r" : "") + senderDisplay + "&r &ahas temporarily " + punishmentType.getMessageDisplay() + " &r" + targetDisplay + "&r&a for " + remainingText + "."));
                fancyMessage.tooltip(Chat.format("&6&m-----------------------------------"), Chat.format("&eReason&7: &f") + reason + ChatColor.GRAY + " (" + server.getName() + ")", Chat.format("&eDuration&7: &f") + remainingText, Chat.format("&6&m-----------------------------------"));
            }
            if(duration == Long.MAX_VALUE) {
                fancyMessage.tooltip(Chat.format("&6&m-----------------------------------"), Chat.format("&eReason&7: &f") + reason + ChatColor.GRAY + " (" + server.getName() + ")", Chat.format("&6&m-----------------------------------"));
            } else{
                fancyMessage.tooltip(Chat.format("&6&m-----------------------------------"), Chat.format("&eReason&7: &f") + reason + ChatColor.GRAY + " (" + server.getName() + ")", Chat.format("&eDuration&7: &f") + remainingText, Chat.format("&6&m-----------------------------------"));
            }
            fancyMessage.command("/c " + targetProfile.getUsername());
            fancyMessage.send(target2);
        });
        if(announce){
            Nitrogen.getInstance().getServer().getOnlinePlayers().stream().filter(target2 -> !target2.hasPermission("Nitrogen.staff")).forEach(target2 -> {
                target2.sendMessage(Chat.format(senderDisplay + "&r &ahas " + (duration != Long.MAX_VALUE ? "temporarily " : "") + punishmentType.getMessageDisplay() + " &r" + targetDisplay + "&r&a."));
            });
        }
        Player toSend = Nitrogen.getInstance().getServer().getPlayer(target);
        if(toSend != null){
            if(punishmentType == PunishmentType.BAN || punishmentType == PunishmentType.BLACKLIST) {
                Nitrogen.getInstance().getServer().getScheduler().runTask(Nitrogen.getInstance(), () -> {
                    toSend.kickPlayer(Chat.format("&cYour account has been " + (duration != Long.MAX_VALUE ? "temporarily " : "") + punishmentType.getKickMessageDisplay() + " from the VisionMC Network.\n\n" + (duration != Long.MAX_VALUE ? "Expires in " + remainingText + "." : "&cAppeal at https://visionmc.club/support.")));
                });
            }
        }
    }

    @PacketListener(identifier = "punishment-remove-packet")
    public void onPunishmentRemove(JsonObject object){
        UUID sender = UUID.fromString(object.get("removedBy").getAsString());
        UUID target = UUID.fromString(object.get("target").getAsString());
        PunishmentType punishmentType = PunishmentType.valueOf(object.get("punishmentType").getAsString());
        NitrogenServer server = Nitrogen.getInstance().getServerHandler().getServer(object.get("removedServer").getAsString());
        String reason = object.get("reason").getAsString();
        long duration = object.get("duration").getAsLong();
        String remainingText = object.get("remainingText").getAsString();
        boolean announce = object.get("announce").getAsBoolean();

        Profile targetProfile = Nitrogen.getInstance().getProfileHandler().getProfile(target);
        Profile senderProfile = Nitrogen.getInstance().getProfileHandler().getProfile(sender);

        String targetDisplay = targetProfile.getHighestRank().getColor() + targetProfile.getUsername();
        String senderDisplay = senderProfile.getHighestRank().getColor() + senderProfile.getUsername();

        Nitrogen.getInstance().getServer().getOnlinePlayers().stream().filter(target2 -> target2.hasPermission("nitrogen.staff")).forEach(target2 -> {
            FancyMessage fancyMessage = new FancyMessage();
            fancyMessage.text(Chat.format((!announce ? "&7[Silent] &r" : "") + targetDisplay + "&r &awas " + punishmentType.getMessageDisplayRemoved() + " by &r" + senderDisplay + "&r&a."));
            fancyMessage.tooltip(Chat.format("&eReason&7: &c") + reason, Chat.format("&eServer&7: &c") + server.getName());
            fancyMessage.send(target2);
        });
        if(announce){
            Nitrogen.getInstance().getServer().getOnlinePlayers().stream().filter(target2 -> !target2.hasPermission("nitrogen.staff")).forEach(target2 -> {
                target2.sendMessage(Chat.format(targetDisplay + "&r &awas " + punishmentType.getMessageDisplayRemoved() + " by &r" + senderDisplay + "&r&a."));
                if(punishmentType == PunishmentType.MUTE){
                    Player toSend = Nitrogen.getInstance().getServer().getPlayer(target);
                    if(toSend != null){
                        toSend.sendMessage(Chat.LIGHT_GREEN + "You are no longer silenced.");
                    }
                }
            });
        }
    }

}