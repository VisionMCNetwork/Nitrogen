package club.visionmc.nitrogen.commands.paramaters;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.tag.Tag;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.xeon.command.param.ParameterType;
import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

/**
 * @author Topu
 * @date 11/27/2022
 */
public class TagParameterType implements ParameterType {

    @Override
    public Object transform(CommandSender commandSender, String s) {
        for(Tag tag : Nitrogen.getInstance().getTagHandler().getTagsInOrder()){
            if(tag.getId().equalsIgnoreCase(s)){
                return tag;
            }
        }
        commandSender.sendMessage(Chat.format("&cThe tag with name \"" + s + "\" was not found."));
        return null;
    }

    @Override
    public List<String> tabComplete(Player player, Set set, String s) {
        List<String> tabCompletions = Lists.newArrayList();
        for(Tag tag : Nitrogen.getInstance().getTagHandler().getTagsInOrder()){
            tabCompletions.add(tag.getId());
        }
        return tabCompletions;
    }
}