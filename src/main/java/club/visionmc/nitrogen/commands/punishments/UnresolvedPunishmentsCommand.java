package club.visionmc.nitrogen.commands.punishments;

import club.visionmc.nitrogen.commands.menu.punishment.UnresolvedPunishmentsMenu;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.xeon.command.Command;
import club.visionmc.xeon.command.param.Param;
import org.bukkit.entity.Player;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class UnresolvedPunishmentsCommand {

    @Command(names = {"unresolvedpunishments"}, permission = "nitrogen.staff")
    public static void unresolvedpunishments(Player player, @Param(name = "target", defaultValue = "self") Profile target){
        new UnresolvedPunishmentsMenu(target).openMenu(player);
    }

}