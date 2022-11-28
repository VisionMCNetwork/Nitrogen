package club.visionmc.nitrogen.commands.menu.punishment;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.commands.menu.staffhistory.StaffPunishmentButton;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.punishment.Punishment;
import club.visionmc.nitrogen.util.Chat;
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
public class UnresolvedPunishmentsMenu extends PaginatedMenu {

    private final Profile profile;

    public UnresolvedPunishmentsMenu(Profile profile){
        this.profile = profile;
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(4, new BackButton(null));
        return buttons;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Unresolved Punishments";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        int index = 0;
        for(Punishment punishment : Nitrogen.getInstance().getPunishmentHandler().getPunishments()){
            if(punishment.getAddedBy().toString().equalsIgnoreCase(profile.getUuid().toString()) && Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(punishment.getTarget()) != null){
                if(punishment.getProof() != null && punishment.getProof().size() != 0) {
                    buttons.put(index, new StaffPunishmentButton(profile, punishment));
                    index++;
                }
            }
        }
        return buttons;
    }

    @Override
    public void onOpen(Player player) {
        if(!profile.getUuid().toString().equalsIgnoreCase(player.getUniqueId().toString()) && !player.hasPermission("op")){
            player.closeInventory();
            player.sendMessage(Chat.LIGHT_RED + "You do not have permission to view unresolved punishment history of other staff members.");
        }
    }
}
