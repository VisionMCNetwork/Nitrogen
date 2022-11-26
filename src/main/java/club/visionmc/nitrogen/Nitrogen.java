package club.visionmc.nitrogen;

import club.visionmc.nitrogen.database.MongoHandler;
import club.visionmc.nitrogen.grant.rank.GrantHandler;
import club.visionmc.nitrogen.grant.tag.TagGrantHandler;
import club.visionmc.nitrogen.profile.ProfileHandler;
import club.visionmc.nitrogen.punishment.PunishmentHandler;
import club.visionmc.nitrogen.rank.RankHandler;
import club.visionmc.nitrogen.server.ServerHandler;
import club.visionmc.nitrogen.server.heartbeat.HeartbeatTask;
import club.visionmc.nitrogen.tag.TagHandler;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author Topu
 * @date 11/26/2022
 */
@Getter
public class Nitrogen extends JavaPlugin {

    @Getter private static Nitrogen instance;

    private ProfileHandler profileHandler;
    private RankHandler rankHandler;
    private TagHandler tagHandler;
    private MongoHandler mongoHandler;
    private GrantHandler grantHandler;
    private TagGrantHandler tagGrantHandler;
    private PunishmentHandler punishmentHandler;
    private ServerHandler serverHandler;

    @Getter private BukkitTask heartbeatTask;

    @Override
    public void onEnable() {
        instance = this;

        profileHandler = new ProfileHandler();
        rankHandler = new RankHandler();
        tagHandler = new TagHandler();
        mongoHandler = new MongoHandler();
        grantHandler = new GrantHandler();
        tagGrantHandler = new TagGrantHandler();
        punishmentHandler = new PunishmentHandler();
        serverHandler = new ServerHandler();

        this.heartbeatTask = new HeartbeatTask().runTaskTimerAsynchronously(this, 0,20L);

    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public void log(String message){
        getServer().getConsoleSender().sendMessage(message);
    }

}
