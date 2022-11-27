package club.visionmc.nitrogen.commands;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.rank.Rank;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.xeon.command.Command;
import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.List;

/**
 * @author Topu
 * @date 11/27/2022
 */
public class ListCommand {

    @Command(names = {"list", "who", "players", "online"}, permission = "")
    public static void list(CommandSender sender){
        List<Profile> profiles = Lists.newArrayList();
        for(Player player : Nitrogen.getInstance().getServer().getOnlinePlayers()){
            if(player.hasMetadata("invisible")) {
                if(sender.hasPermission("basic.staff")) {
                    profiles.add(Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(player.getUniqueId()));
                }
            }else{
                profiles.add(Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(player.getUniqueId()));
            }
        }
        List<String> names = Lists.newArrayList();
        profiles.sort((o1, o2) -> o2.getHighestRank().getPriority() - o1.getHighestRank().getPriority());
        for(Profile profile : profiles){
            String name = Chat.format(Nitrogen.getInstance().getServer().getPlayer(profile.getUuid()).hasMetadata("invisible") ? "&7*&r" : "") + profile.getHighestRank().getColor() + profile.getUsername() + Chat.RESET;
            names.add(name);
        }
        String merged = "";
        for(String str : names){
            merged = merged + Chat.format(", " + str + "&r");
        }
        sender.sendMessage(getListHeader());
        sender.sendMessage(Chat.format("(" + profiles.size() + "/" + Nitrogen.getInstance().getServer().getMaxPlayers() + ") &r" + names));
    }

    private static String getListHeader() {
        StringBuilder builder = new StringBuilder();
        List<Rank> ranksList = Nitrogen.getInstance().getRankHandler().getRanks();
        ranksList.sort((o1, o2) -> o2.getPriority() - o1.getPriority());
        Iterator<Rank> ranks = ranksList.iterator();
        while (ranks.hasNext()) {
            Rank rank = ranks.next();
            boolean displayed = (rank.getPriority() > 0);
            if (displayed)
                builder.append(rank.getCoolName()).append(Chat.RESET).append(", ");
        }
        if (builder.length() > 2)
            builder.setLength(builder.length() - 2);
        return builder.toString();
    }

}