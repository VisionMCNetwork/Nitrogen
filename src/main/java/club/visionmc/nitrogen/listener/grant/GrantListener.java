package club.visionmc.nitrogen.listener.grant;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.event.grant.GrantExpireEvent;
import club.visionmc.nitrogen.util.Chat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class GrantListener implements Listener {

    @EventHandler
    public void grantExpiration(GrantExpireEvent e) {
        Player target = Nitrogen.getInstance().getServer().getPlayer(e.getProfile().getUuid());
        if (target != null) {
            target.sendMessage(ChatColor.RED + "Your " + Chat.RESET.toString() + e.getGrant().getRank().getCoolName() + Chat.RESET.toString() + Chat.LIGHT_RED + " grant has expired.");
        }
    }
}
