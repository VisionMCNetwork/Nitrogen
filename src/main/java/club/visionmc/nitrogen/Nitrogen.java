package club.visionmc.nitrogen;

import club.visionmc.nitrogen.commands.paramaters.ProfileParameterType;
import club.visionmc.nitrogen.commands.paramaters.PunishmentTypeParameterType;
import club.visionmc.nitrogen.commands.paramaters.RankParameterType;
import club.visionmc.nitrogen.commands.paramaters.TagParameterType;
import club.visionmc.nitrogen.database.MongoHandler;
import club.visionmc.nitrogen.grant.rank.GrantHandler;
import club.visionmc.nitrogen.grant.tag.TagGrantHandler;
import club.visionmc.nitrogen.listener.GeneralListener;
import club.visionmc.nitrogen.listener.grant.GrantListener;
import club.visionmc.nitrogen.listener.grant.TagGrantListener;
import club.visionmc.nitrogen.listener.punishment.BanListener;
import club.visionmc.nitrogen.listener.punishment.MuteListener;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.profile.ProfileHandler;
import club.visionmc.nitrogen.punishment.PunishmentHandler;
import club.visionmc.nitrogen.punishment.PunishmentType;
import club.visionmc.nitrogen.rank.Rank;
import club.visionmc.nitrogen.rank.RankHandler;
import club.visionmc.nitrogen.redis.RedisHandler;
import club.visionmc.nitrogen.server.ServerHandler;
import club.visionmc.nitrogen.server.ServerStatus;
import club.visionmc.nitrogen.server.heartbeat.HeartbeatTask;
import club.visionmc.nitrogen.staff.StaffPacketListener;
import club.visionmc.nitrogen.staff.packets.*;
import club.visionmc.nitrogen.tag.Tag;
import club.visionmc.nitrogen.tag.TagHandler;
import club.visionmc.nitrogen.util.Cooldowns;
import club.visionmc.xeon.Xeon;
import club.visionmc.xeon.command.param.defaults.*;
import lombok.Getter;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

/**
 * @author Topu
 * @date 11/26/2022
 */
@Getter
public class Nitrogen extends JavaPlugin {

    @Getter private static Nitrogen instance;

    @Getter private MongoHandler mongoHandler;
    @Getter private RankHandler rankHandler;
    @Getter private ServerHandler serverHandler;
    @Getter private GrantHandler grantHandler;
    @Getter private TagGrantHandler tagGrantHandler;
    @Getter private ProfileHandler profileHandler;
    @Getter private PunishmentHandler punishmentHandler;
    @Getter private RedisHandler redisHandler;
    @Getter private TagHandler tagHandler;

    @Getter private BukkitTask heartbeatTask;

    public void onEnable(){
        instance = this;

        this.mongoHandler = new MongoHandler();
        this.rankHandler = new RankHandler();
        this.serverHandler = new ServerHandler();
        this.grantHandler = new GrantHandler();
        this.tagGrantHandler = new TagGrantHandler();
        this.profileHandler = new ProfileHandler();
        this.punishmentHandler = new PunishmentHandler();
        this.tagHandler = new TagHandler();
        this.redisHandler = new RedisHandler("nitrogen-packet");
        initializeHandlers();
        setupListeners();

        this.heartbeatTask = new HeartbeatTask().runTaskTimerAsynchronously(this, 0,20L);

        Xeon.getInstance().getCommandHandler().registerAll(this);
        Xeon.getInstance().getCommandHandler().registerParameterType(Integer.class, new IntegerParameterType());
        Xeon.getInstance().getCommandHandler().registerParameterType(Float.class, new FloatParameterType());
        Xeon.getInstance().getCommandHandler().registerParameterType(Boolean.class, new BooleanParameterType());
        Xeon.getInstance().getCommandHandler().registerParameterType(UUID.class, new UUIDParameterType());
        Xeon.getInstance().getCommandHandler().registerParameterType(World.class, new WorldParameterType());
        Xeon.getInstance().getCommandHandler().registerParameterType(Rank.class, new RankParameterType());
        Xeon.getInstance().getCommandHandler().registerParameterType(Profile.class, new ProfileParameterType());
        Xeon.getInstance().getCommandHandler().registerParameterType(Tag.class, new TagParameterType());
        Xeon.getInstance().getCommandHandler().registerParameterType(PunishmentType.class, new PunishmentTypeParameterType());

        redisHandler.registerListener(new StaffPacketListener());
        redisHandler.registerPacket(new StaffChatPacket());
        redisHandler.registerPacket(new StaffRequestPacket());
        redisHandler.registerPacket(new StaffReportPacket());
        redisHandler.registerPacket(new StaffPunishmentPacket());
        redisHandler.registerPacket(new StaffRemovePunishmentPacket());
        redisHandler.registerPacket(new AdminChatPacket());


        Cooldowns.createCooldown("report");
        Cooldowns.createCooldown("request");

    }

    @Override
    public void onDisable() {
        serverHandler.getServer(getServer().getServerName()).setServerStatus(ServerStatus.OFFLINE);
        serverHandler.getServer(getServer().getServerName()).setLastPlayerCount(0);
        serverHandler.getServer(getServer().getServerName()).setLastTps(0);
        serverHandler.getServer(getServer().getServerName()).save();
    }

    private void initializeHandlers() {
        mongoHandler.connect();
        rankHandler.init();
        serverHandler.init();
        grantHandler.init();
        profileHandler.init();
        punishmentHandler.init();
        tagHandler.init();
    }

    private void setupListeners(){
        getServer().getPluginManager().registerEvents(new GeneralListener(), this);
        getServer().getPluginManager().registerEvents(new GrantListener(), this);
        getServer().getPluginManager().registerEvents(new MuteListener(), this);
        getServer().getPluginManager().registerEvents(new BanListener(), this);
    }

    public void log(String message){
        getServer().getConsoleSender().sendMessage(message);
    }



}