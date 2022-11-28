package club.visionmc.nitrogen.commands.punishments;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.punishment.menu.MainPunishmentMenu;
import club.visionmc.xeon.command.Command;
import club.visionmc.xeon.command.param.Param;
import org.bukkit.entity.Player;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class CheckPunishmentsCommand {

    @Command(names = {"checkpunishments", "cp", "hist", "history", "c"}, permission = "nitrogen.punishments.history")
    public static void checkPunishments(Player player, @Param(name = "target") Profile target){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            new MainPunishmentMenu(target).openMenu(player);
        }, 1L);
    }

}
