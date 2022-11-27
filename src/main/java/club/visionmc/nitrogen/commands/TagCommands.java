package club.visionmc.nitrogen.commands;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.commands.menu.tag.TagsMenu;
import club.visionmc.nitrogen.tag.Tag;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.xeon.command.Command;
import club.visionmc.xeon.command.param.Param;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Topu
 * @date 11/27/2022
 */
public class TagCommands {

    @Command(names = {"tag", "chattags", "tags", "prefix"}, permission = "")
    public static void tags(Player player){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            new TagsMenu(Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(player.getUniqueId())).openMenu(player);
        }, 1L);
    }

    @Command(names = {"prefix create", "chattags create", "tags create", "tag create"}, permission = "nitrogen.tag.admin")
    public static void tagCreate(CommandSender sender, @Param(name = "name") String name){
        if(Nitrogen.getInstance().getTagHandler().tagExists(name)){
            Tag tag = Nitrogen.getInstance().getTagHandler().getTag(name);
            sender.sendMessage(Chat.format("&cThe tag &r" + tag.getColor() + tag.getDisplayName() + "&r &calready exists."));
        }else{
            Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
                Nitrogen.getInstance().getTagHandler().createTag(name);
                sender.sendMessage(Chat.format("&aYou have created the &r" + name + "&r &atag."));
            }, 1L);
        }
    }

    @Command(names = {"prefix delete", "chattags delete", "tags delete", "tag delete"}, permission = "nitrogen.tag.admin")
    public static void prefixDelete(CommandSender sender, @Param(name = "tag") Tag tag){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            Nitrogen.getInstance().getTagHandler().deleteTag(tag);
            sender.sendMessage(Chat.format("&aYou have deleted the &r" + tag.getColor() + tag.getDisplayName() + "&r &atag."));
        }, 1L);
    }

    @Command(names = {"prefix setcolor", "chattags setcolor", "tags setcolor", "tag setcolor"}, permission = "nitrogen.tag.admin")
    public static void prefixSetColor(CommandSender sender, @Param(name = "tag") Tag tag, @Param(name = "color") String color){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            String previousDisplay = tag.getColor() + tag.getDisplayName();
            tag.setColor(color);
            tag.save();
            sender.sendMessage(Chat.format("&aYou have set the color of &r" + previousDisplay + "&r &atag to: &r" + tag.getColor()) + tag.getColor());
        }, 1L);
    }

    @Command(names = {"prefix setprefix", "chattags setprefix", "tags setprefix", "tag setprefix"}, permission = "nitrogen.tag.admin")
    public static void prefixSetPrefix(CommandSender sender, @Param(name = "tag") Tag tag, @Param(name = "tag", wildcard = true) String prefix1){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            tag.setPrefix(prefix1);
            tag.save();
            sender.sendMessage(Chat.format("&aYou have set the prefix of &r" + tag.getColor() + tag.getDisplayName() + "&r &atag to: &r" + tag.getPrefix()));
        }, 1L);
    }

    @Command(names = {"prefix setpriority", "chattags setpriority", "tags setpriority", "tag setpriority"}, permission = "nitrogen.tag.admin")
    public static void prefixSetPrefix(CommandSender sender, @Param(name = "tag") Tag tag, @Param(name = "priority") Integer priority){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            tag.setPriority(priority);
            tag.save();
            sender.sendMessage(Chat.format("&aYou have set the priority of &r" + tag.getColor() + tag.getDisplayName() + "&r &atag to: &r" + tag.getPriority()));
        }, 1L);
    }

    @Command(names = {"prefix setdisplayname", "chattags setdisplayname", "tags setdisplayname", "tag setdisplayname"}, permission = "nitrogen.tag.admin")
    public static void prefixSetDisplayName(CommandSender sender, @Param(name = "tag") Tag tag, @Param(name = "display name", wildcard = true) String displayName){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            String previousDisplay = tag.getColor() + tag.getDisplayName();
            tag.setDisplayName(displayName);
            tag.save();
            sender.sendMessage(Chat.format("&aYou have set the display name of &r" + previousDisplay + "&r &atag to: &r" + tag.getColor()) + tag.getDisplayName());
        }, 1L);
    }

}
