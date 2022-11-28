package club.visionmc.nitrogen.commands.punishments.create;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.punishment.Punishment;
import club.visionmc.nitrogen.punishment.PunishmentType;
import club.visionmc.nitrogen.staff.packets.StaffPunishmentPacket;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.nitrogen.util.TimeUtil;
import club.visionmc.xeon.command.Command;
import club.visionmc.xeon.command.flag.Flag;
import club.visionmc.xeon.command.param.Param;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class TempBlacklistCommand {

    @Command(names = {"tempblacklist", "tblacklist"}, permission = "nitrogen.punishments.tempblacklist")
    public static void tempblacklist(CommandSender sender, @Param(name = "target") Profile profile, @Param(name = "duration") String duration, @Flag(value = {"a", "announce"}) boolean announce, @Param(name = "reason", wildcard = true) String reason) {
        long lengthParse = TimeUtil.parseTime(duration);
        if(lengthParse <= 0L){
            sender.sendMessage(Chat.format("&cInvalid duration.."));
            return;
        }
        if(profile.hasActivePunishment(PunishmentType.BLACKLIST)){
            sender.sendMessage(Chat.format(Chat.format(profile.getHighestRank().getColor() + profile.getUsername()) + "&r &calready has an active blacklist."));
            return;
        }

        sender.sendMessage(ChatColor.GREEN + "You've blacklisted " + Chat.format(profile.getHighestRank().getColor() + profile.getUsername()) + ChatColor.GREEN + " for " + ChatColor.YELLOW + reason + ChatColor.GREEN + ".");
        Punishment punishment = Nitrogen.getInstance().getPunishmentHandler().createPunishment(new Punishment(UUID.randomUUID(), profile.getUuid(), PunishmentType.BLACKLIST, Nitrogen.getInstance().getServerHandler().getServer(Nitrogen.getInstance().getServer().getServerName()), (sender instanceof Player ? ((Player) sender).getUniqueId() : Profile.getConsoleUUID()), System.currentTimeMillis(), reason, TimeUtil.parseTime(duration), null, null, 0L, null));
        Nitrogen.getInstance().getRedisHandler().sendPacket(new StaffPunishmentPacket(punishment, announce));
    }

}