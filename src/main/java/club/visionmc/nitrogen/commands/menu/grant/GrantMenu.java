package club.visionmc.nitrogen.commands.menu.grant;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.rank.Rank;
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
public class GrantMenu extends PaginatedMenu {

    private final Profile target;

    public GrantMenu(Profile target){
        this.target = target;
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(4, new BackButton(null));
        return buttons;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Choose a Rank";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        int index = 0;
        for (Rank rank : Nitrogen.getInstance().getRankHandler().getRanksInOrder()) {
            buttons.put(index, new RankButton(target, rank));
            index++;
        }
        return buttons;
    }


}