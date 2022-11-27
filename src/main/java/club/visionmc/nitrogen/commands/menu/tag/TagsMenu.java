package club.visionmc.nitrogen.commands.menu.tag;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.tag.Tag;
import club.visionmc.xeon.menu.Button;
import club.visionmc.xeon.menu.buttons.BackButton;
import club.visionmc.xeon.menu.pagination.PaginatedMenu;
import com.google.common.collect.Maps;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * @author Topu
 * @date 11/27/2022
 */
public class TagsMenu extends PaginatedMenu {

    private final Profile profile;

    public TagsMenu(Profile profile){
        this.profile = profile;
    }

    @Override
    public boolean isAutoUpdate() {
        return true;
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(4, new BackButton(null));
        return buttons;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Choose a Prefix";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        int index = 0;
        for(Tag tag : Nitrogen.getInstance().getTagHandler().getTagsInOrder()){
            buttons.put(index, new TagButton(profile, tag));
            index++;
        }
        return buttons;
    }
}