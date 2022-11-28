package club.visionmc.nitrogen.commands.menu.staffhistory;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.punishment.Punishment;
import club.visionmc.nitrogen.punishment.PunishmentType;
import club.visionmc.xeon.menu.Button;
import club.visionmc.xeon.menu.buttons.BackButton;
import club.visionmc.xeon.menu.pagination.PaginatedMenu;
import com.google.common.collect.Maps;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class StaffPunishmentsListMenu extends PaginatedMenu {

    private final Profile profile;
    private final PunishmentType punishmentType;

    @Override
    public boolean isAutoUpdate() {
        return true;
    }

    public StaffPunishmentsListMenu(Profile profile, PunishmentType punishmentType){
        this.profile = profile;
        this.punishmentType = punishmentType;
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(4, new BackButton(new MainStaffPunishmentListMenu(profile)));
        return buttons;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return punishmentType.getMenuDisplay();
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        int index = 0;
        for(Punishment punishment : Nitrogen.getInstance().getPunishmentHandler().getPunishments()){
            if(punishment.getAddedBy().toString().equalsIgnoreCase(profile.getUuid().toString()) && Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(punishment.getTarget()) != null){
                if(punishment.getPunishmentType() == punishmentType) {
                    buttons.put(index, new StaffPunishmentButton(profile, punishment));
                    index++;
                }
            }
        }
        return buttons;
    }
}