package club.visionmc.nitrogen.commands.menu.tag;

import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.tag.Tag;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.xeon.menu.Button;
import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;

/**
 * @author Topu
 * @date 11/27/2022
 */
public class TagButton extends Button {

    private final Profile profile;
    private final Tag tag;

    public TagButton(Profile profile, Tag tag){
        this.profile = profile;
        this.tag = tag;
    }

    @Override
    public String getName(Player player) {
        return Chat.format(tag.getColor()) + tag.getDisplayName();
    }

    @Override
    public List<String> getDescription(Player player) {
        List<String> description = Lists.newArrayList();
        description.add(Chat.format("&7&m") + Chat.LINE);
        description.add(Chat.format("&eDisplay&7: &r" + tag.getPrefix() + "&r" + profile.getHighestRank().getPrefix()) + player.getName() + Chat.LIGHT_GRAY + ": " + Chat.RESET.toString() + "Example");
        description.add(" ");
        if(!player.hasPermission("nitrogen.tags." + tag.getId())) {
            description.add(Chat.format("&cYou don't have permission to use this tag."));
        }else {
            description.add((profile.getActiveTag() == null || !profile.getActiveTag().getId().toLowerCase().equals(tag.getId().toLowerCase()) ? Chat.LIGHT_GREEN + "Click to activate this prefix." : Chat.LIGHT_RED + "Click to deactivate this prefix."));
        }
        description.add(Chat.format("&7&m") + Chat.LINE);
        return description;
    }

    @Override
    public Material getMaterial(Player player) {
        return Material.WOOL;
    }

    @Override
    public byte getDamageValue(Player player) {
        return (byte) (profile.getActiveTag() == null || !profile.getActiveTag().getId().toLowerCase().equals(tag.getId().toLowerCase()) ? 14 : 5);
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType) {
        if(!player.hasPermission("exolon.prefixes." + tag.getId())){
            player.sendMessage(Chat.format("&cYou do not have permission to use this tag."));
            return;
        }
        if(profile.getActiveTag() == null || !profile.getActiveTag().getId().toLowerCase().equals(tag.getId().toLowerCase())){
            profile.setActiveTag(tag);
            profile.save();
            player.sendMessage(Chat.format("&aSuccessfully equipped the &r" + tag.getColor() + tag.getDisplayName() + "&r &atag."));
        }else{
            profile.setActiveTag(null);
            profile.save();
            player.sendMessage(Chat.format("&cYou have unequipped the &r" + tag.getColor() + tag.getDisplayName() + "&r &ctag."));
        }
    }
}