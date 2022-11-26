package club.visionmc.nitrogen;

import club.visionmc.nitrogen.profile.ProfileHandler;
import club.visionmc.nitrogen.rank.RankHandler;
import club.visionmc.nitrogen.tag.TagHandler;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

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

    @Override
    public void onEnable() {
        instance = this;

        profileHandler = new ProfileHandler();
        rankHandler = new RankHandler();
        tagHandler = new TagHandler();
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
