package club.visionmc.nitrogen.commands.paramaters;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.Profile;
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
public class ProfileParameterType implements ParameterType {

    @Override
    public Object transform(CommandSender commandSender, String s) {
        if (s.equals("self")) {
            return Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(((Player) commandSender).getUniqueId());
        } else {
            Profile profile = Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(Nitrogen.getInstance().getServer().getOfflinePlayer(s).getUniqueId());
            if (profile != null) {
                return profile;
            }
            commandSender.sendMessage(Chat.format("&cNo player with name \"" + s + "\" found."));
            return null;
        }
    }

    @Override
    public List<String> tabComplete(Player player, Set set, String s) {
        List<String> tabCompletion = Lists.newArrayList();
        for(Profile profile : Nitrogen.getInstance().getProfileHandler().getProfiles()){
            if(Nitrogen.getInstance().getServer().getPlayer(profile.getUuid()) != null){
                tabCompletion.add(profile.getUsername());
            }
        }
        return tabCompletion;
    }
}