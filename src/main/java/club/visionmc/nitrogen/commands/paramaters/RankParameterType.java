package club.visionmc.nitrogen.commands.paramaters;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.rank.Rank;
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
public class RankParameterType implements ParameterType {

    @Override
    public Object transform(CommandSender commandSender, String s) {
        for(Rank rank : Nitrogen.getInstance().getRankHandler().getRanks()){
            if(rank.getId().equalsIgnoreCase(s)){
                return rank;
            }
        }
        commandSender.sendMessage(Chat.LIGHT_RED + "No rank with name \"" + s + "\" was found.");
        return null;
    }

    @Override
    public List<String> tabComplete(Player player, Set set, String s) {
        List<String> tabCompletion = Lists.newArrayList();
        for(Rank rank : Nitrogen.getInstance().getRankHandler().getRanksInOrder()){
            tabCompletion.add(rank.getId());
        }
        return tabCompletion;
    }
}