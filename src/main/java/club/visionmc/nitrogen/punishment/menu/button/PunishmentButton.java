package club.visionmc.nitrogen.punishment.menu.button;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.punishment.Punishment;
import club.visionmc.nitrogen.punishment.menu.ProofMenu;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.nitrogen.util.TimeUtil;
import club.visionmc.xeon.menu.Button;
import club.visionmc.xeon.util.TimeUtils;
import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.Date;
import java.util.List;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class PunishmentButton extends Button {

    private final Profile profile;
    private final Punishment punishment;

    public PunishmentButton(Profile profile, Punishment punishment){
        this.profile = profile;
        this.punishment = punishment;
        punishment.save();
    }

    @Override
    public String getName(Player player) {
        return Chat.ORANGE + TimeUtils.formatIntoCalendarString(new Date(punishment.getAddedAt()));
    }

    @Override
    public List<String> getDescription(Player player) {
        List<String> description = Lists.newArrayList();
        description.add(Chat.LIGHT_GRAY.toString() + Chat.STRIKETHROUGH + Chat.LINE);
        String addedBy;
        if(punishment.getAddedBy().toString().equalsIgnoreCase(Profile.getConsoleUUID().toString())){
            addedBy = Chat.DARK_RED.toString() + Chat.BOLD + "Console";
        }else{
            addedBy = Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(punishment.getAddedBy()).getHighestRank().getColor() + Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(punishment.getAddedBy()).getUsername();
        }
        description.add(Chat.format("&eAdded By&7: &r" + addedBy));
        description.add(Chat.format("&eAdded Reason&7: &c") + punishment.getReason());
        description.add(Chat.format("&eAdded Server&7: &c") + punishment.getAddedServer().getName());
        if(punishment.isActive()){
            if(punishment.getDuration() != Long.MAX_VALUE){
                description.add(Chat.LIGHT_GRAY.toString() + Chat.STRIKETHROUGH + Chat.LINE);
                description.add(Chat.YELLOW + "Punishment expires in " + Chat.LIGHT_RED.toString() + punishment.getRemainingText() + Chat.RESET.toString() + Chat.YELLOW + ".");
                //TimeUtils.formatLongIntoDetailedString((this.time + this.duration - System.currentTimeMillis()) / 1000)
            }
        }else{
            String removedBy;
            if(punishment.getRemovedBy() == null && punishment.getDuration() != Long.MAX_VALUE){
                removedBy = Chat.DARK_RED.toString() + Chat.BOLD + "Console";
            }else{
                removedBy = (punishment.getRemovedBy().toString().equalsIgnoreCase(Profile.getConsoleUUID().toString()) || punishment.getRemovedBy() == null ? Chat.DARK_RED.toString() + Chat.BOLD + "Console" : Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(punishment.getRemovedBy()).getHighestRank().getColor() + Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(punishment.getRemovedBy()).getUsername());
            }
            description.add(Chat.LIGHT_GRAY.toString() + Chat.STRIKETHROUGH + Chat.LINE);
            description.add(Chat.LIGHT_RED.toString() + Chat.BOLD + "Removed by " + Chat.RESET.toString() + Chat.format(removedBy) + Chat.RESET.toString() + Chat.LIGHT_GRAY + ":");
            description.add(Chat.LIGHT_RED + "The punishment was removed for" + Chat.LIGHT_GRAY + ": " + Chat.RESET.toString() + punishment.getRemovedReason());
            description.add(Chat.LIGHT_RED + "at " + Chat.ORANGE + TimeUtils.formatIntoCalendarString(new Date(punishment.getRemovedAt())) + Chat.LIGHT_RED + ".");
            if(punishment.getDuration() != Long.MAX_VALUE){
                description.add(" ");
                description.add(Chat.LIGHT_RED + "Initial Duration" + Chat.LIGHT_GRAY + ": " + Chat.ORANGE + TimeUtil.formatDuration(punishment.getDuration()));
            }
        }
        description.add(Chat.LIGHT_GRAY.toString() + Chat.STRIKETHROUGH + Chat.LINE);
        return description;
    }

    @Override
    public Material getMaterial(Player player) {
        return Material.WOOL;
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType) {
        new ProofMenu(punishment).openMenu(player);
    }

    @Override
    public byte getDamageValue(Player player) {
        return (byte) (punishment.isActive() ? 5 : 14);
    }
}
