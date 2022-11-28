package club.visionmc.nitrogen.commands.staff;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.staff.packets.AdminChatPacket;
import club.visionmc.nitrogen.staff.packets.StaffChatPacket;
import club.visionmc.xeon.command.Command;
import club.visionmc.xeon.command.param.Param;
import org.bukkit.entity.Player;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class StaffCommands {

    @Command(names = {"staffchat", "sc"}, permission = "nitrogen.staff")
    public static void staffchat(Player player, @Param(name = "message", wildcard = true) String message){
        Nitrogen.getInstance().getRedisHandler().sendPacket(new StaffChatPacket(player.getName(), Nitrogen.getInstance().getServer().getServerName(), message));
    }

    @Command(names = {"adminchat", "ac"}, permission = "nitrogen.admin")
    public static void adminchat(Player player, @Param(name = "message", wildcard = true) String message){
        Nitrogen.getInstance().getRedisHandler().sendPacket(new AdminChatPacket(player.getName(), Nitrogen.getInstance().getServer().getServerName(), message));
    }

}