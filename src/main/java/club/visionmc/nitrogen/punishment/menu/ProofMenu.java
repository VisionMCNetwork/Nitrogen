package club.visionmc.nitrogen.punishment.menu;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.punishment.Punishment;
import club.visionmc.nitrogen.punishment.menu.button.ProofAddButton;
import club.visionmc.nitrogen.punishment.menu.button.ProofButton;
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
public class ProofMenu extends PaginatedMenu {

    private final Punishment punishment;

    public ProofMenu(Punishment punishment){
        this.punishment = punishment;
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(3, new BackButton(new PunishmentListMenu(Nitrogen.getInstance().getProfileHandler().getProfile(punishment.getTarget()), punishment.getPunishmentType())));
        buttons.put(5, new ProofAddButton(punishment));
        return buttons;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Proof Editor";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        int index = 0;
        List<String> proof = (punishment.getProof() == null ? Lists.newArrayList() : punishment.getProof());
        for(String entry : proof){
            buttons.put(index, new ProofButton(punishment, entry));
            index++;
        }
        return buttons;
    }
}
