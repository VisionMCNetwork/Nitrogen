package club.visionmc.nitrogen.commands.punishments.remove;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.punishment.Punishment;
import club.visionmc.nitrogen.punishment.PunishmentType;
import club.visionmc.nitrogen.staff.packets.StaffRemovePunishmentPacket;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.xeon.command.Command;
import club.visionmc.xeon.command.flag.Flag;
import club.visionmc.xeon.command.param.Param;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class UnBanCommand {

    @Command(names = {"unban"}, permission = "nitrogen.punishments.unban")
    public static void unban(CommandSender sender, @Param(name = "target") Profile target, @Flag(value = {"a", "announce"}) boolean announce, @Param(name = "reason", wildcard = true) String reason){
        if(!(target.hasActivePunishment(PunishmentType.BAN))){
            sender.sendMessage(Chat.format(target.getHighestRank().getColor() + target.getUsername() + "&r &cdoes not have an active ban."));
            return;
        }
        Punishment punishment = target.getActivePunishments(PunishmentType.BAN).get(0);
        if(punishment == null){
            sender.sendMessage(Chat.format("&cThere was an error whilst attempting to process this command..."));
            return;
        }
        punishment.setRemovedAt(System.currentTimeMillis());
        punishment.setRemovedBy((sender instanceof Player ? ((Player) sender).getUniqueId() : Profile.getConsoleUUID()));
        punishment.setRemovedReason(reason);
        punishment.save();
        sender.sendMessage(ChatColor.GREEN + "You've unbanned " + target.getHighestRank().getColor() + target.getUsername() + ChatColor.GREEN + " for " + ChatColor.YELLOW + reason + ChatColor.GREEN + ".");
        Nitrogen.getInstance().getRedisHandler().sendPacket(new StaffRemovePunishmentPacket(punishment, announce, Nitrogen.getInstance().getServerHandler().getServer(Nitrogen.getInstance().getServer().getServerName())));
    }
}