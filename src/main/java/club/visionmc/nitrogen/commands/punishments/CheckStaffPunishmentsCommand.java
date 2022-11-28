package club.visionmc.nitrogen.commands.punishments;

import club.visionmc.nitrogen.commands.menu.staffhistory.MainStaffPunishmentListMenu;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.xeon.command.Command;
import club.visionmc.xeon.command.param.Param;
import org.bukkit.entity.Player;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class CheckStaffPunishmentsCommand {

    @Command(names = {"staffpunishments", "checkstaffpunishments", "staffhistory", "staffhist"}, permission = "nitrogen.punishments.staffpunishments")
    public static void staffPunishments(Player player, @Param(name = "target") Profile profile){
        new MainStaffPunishmentListMenu(profile).openMenu(player);
    }
}