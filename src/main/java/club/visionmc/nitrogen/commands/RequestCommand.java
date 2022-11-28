package club.visionmc.nitrogen.commands;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.staff.packets.StaffReportPacket;
import club.visionmc.nitrogen.staff.packets.StaffRequestPacket;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.nitrogen.util.Cooldowns;
import club.visionmc.xeon.command.Command;
import club.visionmc.xeon.command.param.Param;
import org.bukkit.entity.Player;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class RequestCommand {

    @Command(names = {"request", "helpop"}, permission = "")
    public static void request(Player player, @Param(name = "message", wildcard = true) String message){
        if(!Cooldowns.isOnCooldown("request", player)) {
            Nitrogen.getInstance().getRedisHandler().sendPacket(new StaffRequestPacket(player.getName(), Nitrogen.getInstance().getServer().getServerName(), message));
            Cooldowns.addCooldown("request", player, 45);
        }else{
            player.sendMessage(Chat.format("&cYou must wait before creating another request."));
        }
    }

}