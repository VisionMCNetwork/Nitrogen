package club.visionmc.nitrogen.listener;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.ProfileHandler;
import club.visionmc.nitrogen.rank.Rank;
import club.visionmc.nitrogen.tag.Tag;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.nitrogen.util.PermissionUtils;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class GeneralListener implements Listener {

    private final ProfileHandler profileHandler = Nitrogen.getInstance().getProfileHandler();

    @EventHandler
    public void joinServer(PlayerJoinEvent e){
        e.setJoinMessage(null);
        if(!profileHandler.profileExists(e.getPlayer().getUniqueId())) {
            Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(e.getPlayer().getUniqueId()).save();
        }
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent e){
        Rank rank = profileHandler.getProfileOrCreate(e.getPlayer().getUniqueId()).getHighestRank();
        Tag tag = profileHandler.getProfileOrCreate(e.getPlayer().getUniqueId()).getActiveTag();
        e.setFormat(Chat.format(rank.getPrefix()) + "%1$s" + (tag == null ? "" : " " + ChatColor.RESET + Chat.format(tag.getPrefix())) + Chat.LIGHT_GRAY + ": " + Chat.RESET.toString() + "%2$s");
    }

    @EventHandler
    public void quit(PlayerQuitEvent e){
        e.setQuitMessage(null);
        e.getPlayer().removeAttachment(PermissionUtils.getAttachment(e.getPlayer()));
    }

}