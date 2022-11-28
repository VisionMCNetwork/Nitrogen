package club.visionmc.nitrogen.punishment.menu;

import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.punishment.Punishment;
import club.visionmc.nitrogen.punishment.PunishmentType;
import club.visionmc.nitrogen.punishment.menu.button.PunishmentButton;
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
 * @date 11/28/2022
 */
public class PunishmentListMenu extends PaginatedMenu {

    private final Profile profile;
    private final PunishmentType punishmentType;

    public PunishmentListMenu(Profile profile, PunishmentType punishmentType){
        this.profile = profile;
        this.punishmentType = punishmentType;
    }

    @Override
    public boolean isAutoUpdate() {
        return true;
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(4, new BackButton(new MainPunishmentMenu(profile)));
        return buttons;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return punishmentType.getMenuDisplay();
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        int index = 0;
        Map<Integer, Button> buttons = Maps.newHashMap();
        List<Punishment> punishments = Lists.newArrayList(profile.getPunishments());
        punishments = ListUtils.reverseArrayList(punishments);
        for(Punishment punishment : punishments){
            if(punishment.getPunishmentType() == punishmentType){
                buttons.put(index, new PunishmentButton(profile, punishment));
                index++;
            }
        }
        return buttons;
    }
}
