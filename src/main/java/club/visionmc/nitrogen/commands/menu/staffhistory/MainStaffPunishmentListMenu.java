package club.visionmc.nitrogen.commands.menu.staffhistory;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.punishment.PunishmentType;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.xeon.menu.Button;
import club.visionmc.xeon.menu.Menu;
import com.google.common.collect.Maps;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;
import java.util.Map;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class MainStaffPunishmentListMenu extends Menu {

    private final Profile profile;

    public MainStaffPunishmentListMenu(Profile profile){
        this.profile = profile;
    }


    @Override
    public String getTitle(Player player) {
        return "Staff Punishments";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        if(!player.hasPermission("nitrogen.punishments.blacklist") || !player.hasPermission("nitrogen.punishments.tempblacklist")) {
            buttons.put(2, button(PunishmentType.WARN));
            buttons.put(4, button(PunishmentType.MUTE));
            buttons.put(6, button(PunishmentType.BAN));
        }else{
            buttons.put(1, button(PunishmentType.WARN));
            buttons.put(3, button(PunishmentType.MUTE));
            buttons.put(5, button(PunishmentType.BAN));
            buttons.put(7, button(PunishmentType.BLACKLIST));
        }
        return buttons;
    }

    private Button button(PunishmentType punishmentType){
        switch(punishmentType){
            case WARN: {
                return new Button() {
                    @Override
                    public String getName(Player player) {
                        return Chat.format("&eWarnings");
                    }

                    @Override
                    public List<String> getDescription(Player player) {
                        return null;
                    }

                    @Override
                    public Material getMaterial(Player player) {
                        return Material.WOOL;
                    }

                    @Override
                    public byte getDamageValue(Player player) {
                        return (byte) 4;
                    }

                    @Override
                    public void clicked(Player player, int slot, ClickType clickType) {
                        Nitrogen.getInstance().getServer().getConsoleSender().sendMessage(Chat.format("&a") + player);
                        new StaffPunishmentsListMenu(MainStaffPunishmentListMenu.this.profile, PunishmentType.WARN).openMenu(player);
                    }
                };
            }case MUTE: {
                return new Button() {
                    @Override
                    public String getName(Player player) {
                        return Chat.format("&6Mutes");
                    }

                    @Override
                    public List<String> getDescription(Player player) {
                        return null;
                    }

                    @Override
                    public Material getMaterial(Player player) {
                        return Material.WOOL;
                    }

                    @Override
                    public byte getDamageValue(Player player) {
                        return (byte) 1;
                    }

                    @Override
                    public void clicked(Player player, int slot, ClickType clickType) {
                        new StaffPunishmentsListMenu(MainStaffPunishmentListMenu.this.profile, PunishmentType.MUTE).openMenu(player);
                    }
                };
            }case BAN: {
                return new Button() {
                    @Override
                    public String getName(Player player) {
                        return Chat.format("&cBans");
                    }

                    @Override
                    public List<String> getDescription(Player player) {
                        return null;
                    }

                    @Override
                    public Material getMaterial(Player player) {
                        return Material.WOOL;
                    }

                    @Override
                    public byte getDamageValue(Player player) {
                        return (byte) 14;
                    }

                    @Override
                    public void clicked(Player player, int slot, ClickType clickType) {
                        new StaffPunishmentsListMenu(MainStaffPunishmentListMenu.this.profile, PunishmentType.BAN).openMenu(player);
                    }
                };
            }case BLACKLIST: {
                return new Button() {
                    @Override
                    public String getName(Player player) {
                        return Chat.format("&4Blacklists");
                    }

                    @Override
                    public List<String> getDescription(Player player) {
                        return null;
                    }

                    @Override
                    public Material getMaterial(Player player) {
                        return Material.WOOL;
                    }

                    @Override
                    public byte getDamageValue(Player player) {
                        return (byte) 15;
                    }

                    @Override
                    public void clicked(Player player, int slot, ClickType clickType) {
                        new StaffPunishmentsListMenu(MainStaffPunishmentListMenu.this.profile, PunishmentType.BLACKLIST).openMenu(player);
                    }
                };
            } default: {
                return null;
            }
        }
    }

}