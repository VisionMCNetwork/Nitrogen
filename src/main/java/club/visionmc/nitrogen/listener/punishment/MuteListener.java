package club.visionmc.nitrogen.listener.punishment;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.punishment.Punishment;
import club.visionmc.nitrogen.punishment.PunishmentType;
import club.visionmc.nitrogen.util.Chat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class MuteListener implements Listener {

    @EventHandler
    public void chat(AsyncPlayerChatEvent e){
        Profile profile = Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(e.getPlayer().getUniqueId());
        if(profile == null){
            e.getPlayer().kickPlayer(Chat.format("&cYour profile has failed to load. Please contact an administrator."));
            return;
        }
        if(profile.hasActivePunishment(PunishmentType.MUTE)){
            Punishment activePunishment = profile.getActivePunishments(PunishmentType.MUTE).get(0);
            if(activePunishment == null){
                Nitrogen.getInstance().getServer().getConsoleSender().sendMessage(Chat.format("&cUnable to complete punishment request for " + profile.getUuid() + "; the chat event will resume."));
                return;
            }
            e.setCancelled(true);
            e.getPlayer().sendMessage(Chat.format("&cYou are currently muted due to &l" + activePunishment.getReason() + "&r&c." + (activePunishment.getDuration() == Long.MAX_VALUE ? "" : " Your mute will expire within &l" + activePunishment.getRemainingText() + "&r&c.")));
        }
    }

}