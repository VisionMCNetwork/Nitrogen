package club.visionmc.nitrogen.commands;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.grant.rank.Grant;
import club.visionmc.nitrogen.grant.rank.menu.GrantsMenu;
import club.visionmc.nitrogen.grant.tag.menu.TagGrantsMenu;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.rank.Rank;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.nitrogen.util.TimeUtil;
import club.visionmc.xeon.command.Command;
import club.visionmc.xeon.command.param.Param;
import club.visionmc.xeon.util.TimeUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Topu
 * @date 11/27/2022
 */
public class GrantsCommands {

    @Command(names = {"grants"}, permission = "nitrogen.grant.admin")
    public static void grants(Player player, @Param(name = "target")Profile target){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            new GrantsMenu(target).openMenu(player);
        }, 1L);
    }

    @Command(names = {"taggrants"}, permission = "nitrogen.grant.admin")
    public static void taggrants(Player player, @Param(name = "target")Profile target){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            new TagGrantsMenu(target).openMenu(player);
        }, 1L);
    }

}
