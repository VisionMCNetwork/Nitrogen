package club.visionmc.nitrogen.listener.punishment;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.punishment.Punishment;
import club.visionmc.nitrogen.punishment.PunishmentType;
import club.visionmc.nitrogen.util.Chat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class BanListener implements Listener {

    @EventHandler
    public void preLogin(AsyncPlayerPreLoginEvent e){
        Profile profile = Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(e.getUniqueId());
        if(profile == null) {
            return;
        }
        if(profile.hasActivePunishment(PunishmentType.BAN)){
            Punishment activePunishment = profile.getActivePunishments(PunishmentType.BAN).get(0);
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
            if(activePunishment.getDuration() == Long.MAX_VALUE) {
                e.setKickMessage(Chat.format("&cYou are permanently banned from &4VisionMC&c." + "\n&cYou were banned for: &7 " + activePunishment.getReason() + ". \n&7If you feel this is unjustified create a ticket at visionmc.club/support"));
            }else{
                e.setKickMessage(Chat.format("&cYou are banned from &4VisionMC&c for" + activePunishment.getRemainingText() + "." + "\n&cYou were banned for: &7" + activePunishment.getReason() + ". \n&7If you feel this is unjustified create a ticket at visionmc.club/support"));
            }
        }
    }

    @EventHandler
    public void preLoginBlacklist(AsyncPlayerPreLoginEvent e){
        Profile profile = Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(e.getUniqueId());
        if(profile == null) {
            return;
        }
        if(profile.hasActivePunishment(PunishmentType.BLACKLIST)){
            Punishment activePunishment = profile.getActivePunishments(PunishmentType.BLACKLIST).get(0);
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
            if(activePunishment.getDuration() == Long.MAX_VALUE) {
                e.setKickMessage(Chat.format("&cYou are permanently blacklisted from &4VisionMC&c." + "\n&cYou were banned for: &7 " + activePunishment.getReason() + ". \n&7If you feel this is unjustified create a ticket at visionmc.club/support"));
            }else{
                e.setKickMessage(Chat.format("&cYou are blacklisted from &4VisionMC&c for" + activePunishment.getRemainingText() + "." + "\n&cYou were blacklisted for: &7" + activePunishment.getReason() + ". \n&7If you feel this is unjustified create a ticket at visionmc.club/support"));
            }
        }
    }

}
