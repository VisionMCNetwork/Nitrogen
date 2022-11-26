package club.visionmc.nitrogen.server.heartbeat;

import club.visionmc.nitrogen.Nitrogen;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class HeartbeatTask extends BukkitRunnable {

    @Override
    public void run() {
        Nitrogen.getInstance().getRankHandler().recalculateRanks();
        Nitrogen.getInstance().getGrantHandler().recalculateGrants();
        Nitrogen.getInstance().getTagGrantHandler().recalculateTagGrants();
        Nitrogen.getInstance().getProfileHandler().recalculateProfiles();
        Nitrogen.getInstance().getPunishmentHandler().recalculatePunishments();
        Nitrogen.getInstance().getServerHandler().recalculateServers();
        Nitrogen.getInstance().getTagHandler().recalculateTags();

        Nitrogen.getInstance().getServerHandler().refreshServerData();

        Nitrogen.getInstance().getGrantHandler().refreshGrants();
        Nitrogen.getInstance().getTagGrantHandler().refreshTagGrants();
        Nitrogen.getInstance().getPunishmentHandler().refreshPunishments();

    }


}