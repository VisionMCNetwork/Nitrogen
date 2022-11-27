package club.visionmc.nitrogen.commands;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.commands.menu.grant.GrantMenu;
import club.visionmc.nitrogen.commands.menu.taggrant.TagGrantMenu;
import club.visionmc.nitrogen.grant.rank.Grant;
import club.visionmc.nitrogen.grant.tag.TagGrant;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.rank.Rank;
import club.visionmc.nitrogen.tag.Tag;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.nitrogen.util.TimeUtil;
import club.visionmc.xeon.command.Command;
import club.visionmc.xeon.command.param.Param;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Topu
 * @date 11/27/2022
 */
public class GrantCommands {

    @Command(names = {"grant"}, permission = "nitrogen.grant.admin")
    public static void grant(Player player, @Param(name = "target") Profile target){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            new GrantMenu(target).openMenu(player);
        }, 1L);
    }

    @Command(names = {"ogrant"}, permission = "console")
    public static void ogrant(CommandSender sender, @Param(name = "target") Profile target, @Param(name = "rank") Rank rank, @Param(name = "length") String length, @Param(name = "reason", wildcard = true) String reason){
        Nitrogen.getInstance().getGrantHandler().createGrant(new Grant(UUID.randomUUID(), target.getUuid(), (sender instanceof Player ? ((Player) sender).getUniqueId() : Profile.getConsoleUUID()), rank, System.currentTimeMillis(), reason, TimeUtil.parseTime(length), null, 0L, null));
        sender.sendMessage(Chat.LIGHT_GREEN + "Successfully granted " + Chat.RESET.toString() + Chat.format(rank.getColor() + target.getUsername()) + Chat.RESET.toString() + Chat.LIGHT_GREEN + " the "
                + Chat.RESET.toString() + rank.getCoolName() + Chat.RESET.toString() + Chat.LIGHT_GREEN + " rank "
                + (TimeUtil.parseTime(length) == Long.MAX_VALUE ? "indefinitely" : "for a period of " + Chat.YELLOW + TimeUtil.formatDuration(TimeUtil.parseTime(length)) + Chat.LIGHT_GREEN + " due to " + Chat.YELLOW + reason + Chat.LIGHT_GREEN + "."));
    }

    @Command(names = {"taggrant"}, permission = "nitrogen.grant.admin")
    public static void taggrant(Player player, @Param(name = "target") Profile target){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            new TagGrantMenu(target).openMenu(player);
        }, 1L);
    }

    @Command(names = {"otaggrant"}, permission = "console")
    public static void otaggrant(CommandSender sender, @Param(name = "target") Profile target, @Param(name = "tag") Tag tag, @Param(name = "length") String length, @Param(name = "reason", wildcard = true) String reason){
        Nitrogen.getInstance().getTagGrantHandler().createTagGrant(new TagGrant(UUID.randomUUID(), target.getUuid(), (sender instanceof Player ? ((Player) sender).getUniqueId() : Profile.getConsoleUUID()), tag, System.currentTimeMillis(), reason, TimeUtil.parseTime(length), null, 0L, null));
        sender.sendMessage(Chat.LIGHT_GREEN + "Successfully granted " + Chat.RESET.toString() + Chat.format(tag.getColor() + target.getUsername()) + Chat.RESET.toString() + Chat.LIGHT_GREEN + " the "
                + Chat.RESET.toString() + tag.getDisplayName() + Chat.RESET.toString() + Chat.LIGHT_GREEN + " tag "
                + (TimeUtil.parseTime(length) == Long.MAX_VALUE ? "indefinitely" : "for a period of " + Chat.YELLOW + TimeUtil.formatDuration(TimeUtil.parseTime(length)) + Chat.LIGHT_GREEN + " due to " + Chat.YELLOW + reason + Chat.LIGHT_GREEN + "."));
    }

}