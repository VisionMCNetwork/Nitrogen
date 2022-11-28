package club.visionmc.nitrogen.commands;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.staff.packets.StaffReportPacket;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.nitrogen.util.Cooldowns;
import club.visionmc.xeon.command.Command;
import club.visionmc.xeon.command.param.Param;
import org.bukkit.entity.Player;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class ReportCommand {

    @Command(names = {"report"}, permission = "")
    public static void report(Player player, @Param(name = "target") Profile target, @Param(name = "reason") String reason){
        if(!Cooldowns.isOnCooldown("report", player)){
            Nitrogen.getInstance().getRedisHandler().sendPacket(new StaffReportPacket(player.getName(), target.getUsername(), Nitrogen.getInstance().getServer().getServerName(), reason));
            Cooldowns.addCooldown("report", player, 45);
        }else{
            player.sendMessage(Chat.format("&cYou must wait before creating another report."));
        }
    }

}