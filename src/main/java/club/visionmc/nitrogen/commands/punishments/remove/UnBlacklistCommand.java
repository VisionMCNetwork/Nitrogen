package club.visionmc.nitrogen.commands.punishments.remove;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.punishment.Punishment;
import club.visionmc.nitrogen.punishment.PunishmentType;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.xeon.command.Command;
import club.visionmc.xeon.command.flag.Flag;
import club.visionmc.xeon.command.param.Param;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class UnBlacklistCommand {

    @Command(names = {"unblacklist"}, permission = "nitrogen.punishments.unblacklist")
    public static void unban(CommandSender sender, @Param(name = "target") Profile target, @Flag(value = {"a", "announce"}) boolean announce, @Param(name = "reason", wildcard = true) String reason){
        if(!(target.hasActivePunishment(PunishmentType.BLACKLIST))){
            sender.sendMessage(Chat.format(target.getHighestRank().getColor() + target.getUsername() + "&r &cdoes not have an active blacklist."));
            return;
        }
        Punishment punishment = target.getActivePunishments(PunishmentType.BLACKLIST).get(0);
        if(punishment == null){
            sender.sendMessage(Chat.format("&cThere was an error whilst attempting to process this command..."));
            return;
        }
        punishment.setRemovedAt(System.currentTimeMillis());
        punishment.setRemovedBy((sender instanceof Player ? ((Player) sender).getUniqueId() : Profile.getConsoleUUID()));
        punishment.setRemovedReason(reason);
        punishment.save();
        sender.sendMessage(ChatColor.GREEN + "You've unblacklisted " + target.getHighestRank().getColor() + target.getUsername() + ChatColor.GREEN + " for " + ChatColor.YELLOW + reason + ChatColor.GREEN + ".");
        FancyMessage fancyMessage = new FancyMessage();
        String targetDisplay = target.getHighestRank().getColor() + target.getUsername();
        String by = (sender instanceof Player ? Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(((Player) sender).getUniqueId()).getHighestRank().getColor() + Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(((Player) sender).getUniqueId()).getUsername() : "&4&lConsole");
        fancyMessage.text(Chat.format((announce ? "" : "&7[Silent] &r") + targetDisplay + "&r &awas unblacklisted by " + by + "&r&a.")).tooltip(Chat.format("&eReason: &c") + reason);
        Bukkit.getServer().getOnlinePlayers().stream().filter(target1 -> target1.hasPermission("nitrogen.punishments.unblacklist")).forEach(fancyMessage::send);
        Bukkit.getServer().getOnlinePlayers().stream().filter(target1 -> !target1.hasPermission("nitrogen.punishments.unblacklist") && announce).forEach(target1 -> {
            target1.sendMessage(Chat.format(target + "&r &awas unblacklisted by &r" + by + "&r&a."));
        });
    }

}