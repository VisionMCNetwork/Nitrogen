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
public class WarnCommand {

    @Command(names = {"warn"}, permission = "nitrogen.punishments.warn")
    public static void warn(CommandSender sender, @Param(name = "target") Profile profile, @Flag(value = {"a", "announce"}) boolean announce, @Param(name = "reason", wildcard = true) String reason){
        Punishment punishment = Nitrogen.getInstance().getPunishmentHandler().createPunishment(new Punishment(UUID.randomUUID(), profile.getUuid(), PunishmentType.WARN, Nitrogen.getInstance().getServerHandler().getServer(Nitrogen.getInstance().getServer().getServerName()), (sender instanceof Player ? ((Player) sender).getUniqueId() : Profile.getConsoleUUID()), System.currentTimeMillis(), reason, Long.MAX_VALUE, null, null, 0L, null));

        sender.sendMessage(ChatColor.GREEN + "You've warned " + Chat.format(profile.getHighestRank().getColor() + profile.getUsername()) + ChatColor.GREEN + " for " + ChatColor.YELLOW + reason + ChatColor.GREEN + ".");
        Nitrogen.getInstance().getRedisHandler().sendPacket(new StaffPunishmentPacket(punishment, announce));
        Player toSend = Nitrogen.getInstance().getServer().getPlayer(profile.getUuid());
        if(toSend != null){
            toSend.sendMessage(" ");
            toSend.sendMessage(" ");
            toSend.sendMessage(Chat.format("&c&lYou have been warned: " + Chat.RESET.toString() + Chat.YELLOW + reason));
            toSend.sendMessage(" ");
            toSend.sendMessage(" ");
        }
    }
}