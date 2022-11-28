package club.visionmc.nitrogen.commands.paramaters;

import club.visionmc.nitrogen.punishment.PunishmentType;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.xeon.command.param.ParameterType;
import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class PunishmentTypeParameterType implements ParameterType {

    @Override
    public Object transform(CommandSender commandSender, String s) {
        for(PunishmentType punishmentType : PunishmentType.values()){
            if(s.equalsIgnoreCase(punishmentType.name())){
                return punishmentType;
            }
        }
        commandSender.sendMessage(Chat.format("&cPunishment type with name \"" + s + "\" not found."));
        return null;
    }

    @Override
    public List<String> tabComplete(Player player, Set set, String s) {
        List<String> tabCompletions = Lists.newArrayList();
        for(PunishmentType punishmentType : PunishmentType.values()){
            tabCompletions.add(punishmentType.name().toUpperCase());
        }
        return tabCompletions;
    }
}