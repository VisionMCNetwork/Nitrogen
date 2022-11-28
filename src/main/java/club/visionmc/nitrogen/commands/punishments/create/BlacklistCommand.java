package club.visionmc.nitrogen.commands.punishments.create;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.punishment.Punishment;
import club.visionmc.nitrogen.punishment.PunishmentType;
import club.visionmc.nitrogen.staff.packets.StaffPunishmentPacket;
import club.visionmc.nitrogen.util.Chat;
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
public class BlacklistCommand {

    @Command(names = {"blacklist"}, permission = "nitrogen.punishments.blacklist")
    public static void blacklist(CommandSender sender, @Param(name = "target") Profile profile, @Flag(value = {"a", "announce"}) boolean announce, @Param(name = "reason", wildcard = true) String reason) {
        if(profile.hasActivePunishment(PunishmentType.BAN)){
            sender.sendMessage(Chat.format(Chat.format(profile.getHighestRank().getColor() + profile.getUsername()) + "&r &calready has an active blacklist."));
            return;
        }

        sender.sendMessage(ChatColor.GREEN + "You've blacklisted " + Chat.format(profile.getHighestRank().getColor() + profile.getUsername()) + ChatColor.GREEN + " for " + ChatColor.YELLOW + reason + ChatColor.GREEN + ".");
        Punishment punishment = Nitrogen.getInstance().getPunishmentHandler().createPunishment(new Punishment(UUID.randomUUID(), profile.getUuid(), PunishmentType.BLACKLIST, Nitrogen.getInstance().getServerHandler().getServer(Nitrogen.getInstance().getServer().getServerName()), (sender instanceof Player ? ((Player) sender).getUniqueId() : Profile.getConsoleUUID()), System.currentTimeMillis(), reason, Long.MAX_VALUE, null, null, 0L, null));
        Nitrogen.getInstance().getRedisHandler().sendPacket(new StaffPunishmentPacket(punishment, announce));
    }

}