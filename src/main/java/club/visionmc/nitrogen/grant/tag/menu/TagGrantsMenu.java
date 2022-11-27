package club.visionmc.nitrogen.grant.tag.menu;

import club.visionmc.nitrogen.grant.rank.Grant;
import club.visionmc.nitrogen.grant.rank.menu.GrantsButton;
import club.visionmc.nitrogen.grant.tag.TagGrant;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.util.ListUtils;
import club.visionmc.xeon.menu.Button;
import club.visionmc.xeon.menu.buttons.BackButton;
import club.visionmc.xeon.menu.pagination.PaginatedMenu;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

/**
 * @author Topu
 * @date 11/27/2022
 */
public class TagGrantsMenu extends PaginatedMenu {

    private final Profile profile;

    public TagGrantsMenu(Profile profile) {
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
        return "Tag Grants";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        int index = 0;
        List<TagGrant> grants = Lists.newArrayList(profile.getTagGrants());
        grants = ListUtils.reverseArrayList(grants);
        for(TagGrant grant : grants){
            buttons.put(index, new TagGrantsButton(grant));
            index++;
        }
        return buttons;
    }
}