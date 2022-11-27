package club.visionmc.nitrogen.commands;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.rank.Rank;
import club.visionmc.nitrogen.rank.RankHandler;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.xeon.command.Command;
import club.visionmc.xeon.command.param.Param;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Topu
 * @date 11/27/2022
 */
public class RankCommands {

    private static final RankHandler rankHandler = Nitrogen.getInstance().getRankHandler();

    @Command(names = {"rank create"}, permission = "Nitrogen.rank.admin", description = "Create a rank.")
    public static void rankCreate(CommandSender sender, @Param(name = "rank") String rank){
        if(rankHandler.rankExists(rank)){
            sender.sendMessage(Chat.LIGHT_RED + "The rank named " + Chat.RESET.toString() + Chat.format(rankHandler.getRank(rank).getCoolName()) + Chat.RESET.toString() + Chat.LIGHT_RED + " already exists.");
            return;
        }
        rankHandler.createRank(rank);
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            sender.sendMessage(Chat.LIGHT_GREEN + "You have created the " + Chat.RESET.toString() + rank + Chat.RESET.toString() + Chat.LIGHT_GREEN + " rank.");
        }, 2L);
    }

    @Command(names = {"rank delete"}, permission = "Nitrogen.rank.admin", description = "Delete a rank.")
    public static void rankDelete(CommandSender sender, @Param(name = "rank") Rank rank){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            if(rank.getId().equalsIgnoreCase("default")){
                sender.sendMessage(Chat.LIGHT_RED + "You cannot delete the default rank.");
                return;
            }
            rankHandler.deleteRank(rank.getId());
            sender.sendMessage(Chat.LIGHT_GREEN + "You have deleted the " + Chat.RESET.toString() + rank.getCoolName() + Chat.RESET.toString() + Chat.LIGHT_GREEN + " rank.");
        }, 1L);
    }

    @Command(names = {"rank setdisplayname"}, permission = "Nitrogen.rank.admin", description = "Set the display name of a rank.")
    public static void rankSetDisplayName(CommandSender sender, @Param(name = "rank") Rank rank, @Param(name = "color", wildcard = true) String displayName){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            String priorDisplay = rank.getCoolName();
            rank.setDisplayName(displayName);
            rank.save();
            sender.sendMessage(Chat.LIGHT_GREEN + "Set the display name of rank " + Chat.RESET.toString() + priorDisplay + Chat.RESET.toString() + Chat.LIGHT_GREEN + " to: " + Chat.RESET.toString() + rank.getCoolName());
        }, 1L);
    }

    @Command(names = {"rank setcolor"}, permission = "Nitrogen.rank.admin", description = "Set the display color of a rank.")
    public static void rankSetColor(CommandSender sender, @Param(name = "rank") Rank rank, @Param(name = "color") String color){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            String priorDisplay = rank.getCoolName();
            rank.setColor(color);
            rank.save();
            sender.sendMessage(Chat.LIGHT_GREEN + "Set the color of rank " + Chat.RESET.toString() + priorDisplay + Chat.RESET.toString() + Chat.LIGHT_GREEN + " to: " + Chat.RESET.toString() + Chat.format(color) + color);
        }, 1L);
    }

    @Command(names = {"rank setprefix"}, permission = "Nitrogen.rank.admin", description = "Set the display prefix of a rank.")
    public static void rankSetPrefix(CommandSender sender, @Param(name = "rank") Rank rank, @Param(name = "prefix", wildcard = true) String prefix){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            rank.setPrefix(prefix);
            rank.save();
            sender.sendMessage(Chat.LIGHT_GREEN + "Set the prefix of rank " + Chat.RESET.toString() + rank.getCoolName() + Chat.RESET.toString() + Chat.LIGHT_GREEN + " to: " + Chat.RESET.toString() + Chat.format(rank.getPrefix()));
        }, 1L);
    }

    @Command(names = {"rank setpriority"}, permission = "Nitrogen.rank.admin", description = "Set the general and display priority of a rank.")
    public static void rankSetPrefix(CommandSender sender, @Param(name = "rank") Rank rank, @Param(name = "priority") Integer priority){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            rank.setPriority(priority);
            rank.save();
            sender.sendMessage(Chat.LIGHT_GREEN + "Set the priority of rank " + Chat.RESET.toString() + rank.getCoolName() + Chat.RESET.toString() + Chat.LIGHT_GREEN + " to: " + Chat.RESET.toString() + rank.getPriority());
        }, 1L);
    }

    @Command(names = {"rank setstaff"}, permission = "Nitrogen.rank.admin", description = "Set the staff status of a rank.")
    public static void rankSetStaff(CommandSender sender, @Param(name = "rank") Rank rank, @Param(name = "staff") Boolean staff){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            rank.setStaff(staff);
            rank.save();
            sender.sendMessage(Chat.LIGHT_GREEN + "Set the staff status of rank " + Chat.RESET.toString() + rank.getCoolName() + Chat.RESET.toString() + Chat.LIGHT_GREEN + " to: " + Chat.RESET.toString() + rank.isStaff());
        }, 1L);
    }

    @Command(names = {"rank sethidden"}, permission = "Nitrogen.rank.admin", description = "Set the visibility status of a rank.")
    public static void rankSetHidden(CommandSender sender, @Param(name = "rank") Rank rank, @Param(name = "hidden") Boolean hidden){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            rank.setHidden(hidden);
            rank.save();
            sender.sendMessage(Chat.LIGHT_GREEN + "Set the staff status of rank " + Chat.RESET.toString() + rank.getCoolName() + Chat.RESET.toString() + Chat.LIGHT_GREEN + " to: " + Chat.RESET.toString() + rank.isHidden());
        }, 1L);
    }

    @Command(names = {"rank setgrantable"}, permission = "Nitrogen.rank.admin", description = "Set the grant status of a rank.")
    public static void rankSetgrantable(CommandSender sender, @Param(name = "rank") Rank rank, @Param(name = "grantable") Boolean grantable){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            rank.setGrantable(grantable);
            rank.save();
            sender.sendMessage(Chat.LIGHT_GREEN + "Set the staff status of rank " + Chat.RESET.toString() + rank.getCoolName() + Chat.RESET.toString() + Chat.LIGHT_GREEN + " to: " + Chat.RESET.toString() + rank.isHidden());
        }, 1L);
    }

    @Command(names = {"rank setdefault"}, permission = "Nitrogen.rank.admin", description = "Set the default status of a rank.")
    public static void rankSetdefault(CommandSender sender, @Param(name = "rank") Rank rank, @Param(name = "default") Boolean defaultRank){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            rank.setDefaultRank(defaultRank);
            rank.save();
            sender.sendMessage(Chat.LIGHT_GREEN + "Set the staff status of rank " + Chat.RESET.toString() + rank.getCoolName() + Chat.RESET.toString() + Chat.LIGHT_GREEN + " to: " + Chat.RESET.toString() + rank.isDefaultRank());
        }, 1L);
    }

    @Command(names = {"rank addpermission"}, permission = "Nitrogen.rank.admin", description = "Add a permission to a rank.")
    public static void rankAddPermission(CommandSender sender, @Param(name = "rank") Rank rank, @Param(name = "permission") String permission){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            if(rank.getPermissions().contains(permission.toLowerCase())){
                sender.sendMessage(Chat.LIGHT_RED + "The rank " + rank.getCoolName() + Chat.RESET.toString() + Chat.LIGHT_RED + " already has access to that permission.");
                return;
            }
            rank.getPermissions().add(permission.toLowerCase());
            rank.save();
            sender.sendMessage(Chat.LIGHT_GREEN + "Added permission " + Chat.RESET.toString() + permission.toLowerCase() + Chat.LIGHT_GREEN.toString() + " to permissions of rank " + Chat.RESET.toString() + rank.getCoolName() + Chat.RESET.toString() + Chat.LIGHT_GREEN + ".");
        }, 1L);
    }

    @Command(names = {"rank removepermission"}, permission = "Nitrogen.rank.admin", description = "Remove a permission to a rank.")
    public static void rankRemovePermission(CommandSender sender, @Param(name = "rank") Rank rank, @Param(name = "permission") String permission){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            if(!rank.getPermissions().contains(permission.toLowerCase())){
                sender.sendMessage(Chat.LIGHT_RED + "The rank " + rank.getCoolName() + Chat.RESET.toString() + Chat.LIGHT_RED + " does not have access to that permission.");
                return;
            }
            rank.getPermissions().remove(permission.toLowerCase());
            rank.save();
            sender.sendMessage(Chat.LIGHT_GREEN + "Removed permission " + Chat.RESET.toString() + permission.toLowerCase() + Chat.LIGHT_GREEN.toString() + " from permissions of rank " + Chat.RESET.toString() + rank.getCoolName() + Chat.RESET.toString() + Chat.LIGHT_GREEN + ".");
        }, 1L);
    }

    @Command(names = {"rank list"}, permission = "Nitrogen.rank.admin", description = "View a list of all ranks.")
    public static void rankList(CommandSender sender){
        Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
            sender.sendMessage(Chat.LIGHT_GRAY.toString() + Chat.STRIKETHROUGH + Chat.LINE);
            sender.sendMessage(Chat.ORANGE.toString() + Chat.BOLD + "Rank " + Chat.LIGHT_GRAY.toString() + "⎜ " + Chat.RESET.toString() + " List");
            sender.sendMessage(" ");
            for(Rank rank : rankHandler.getRanksInOrder()){
                if(sender instanceof Player) {
                    FancyMessage fancyMessage = new FancyMessage();
                    fancyMessage.text(rank.getCoolName() + Chat.RESET.toString() + " - " + rank.getId()).tooltip(
                            Chat.LIGHT_GRAY.toString() + Chat.STRIKETHROUGH + Chat.LINE,
                            ChatColor.GOLD + "Rank ID" + Chat.LIGHT_GRAY + ": " + Chat.RESET.toString() + rank.getId(),
                            ChatColor.GOLD + "Display Name" + Chat.LIGHT_GRAY + ": " + Chat.RESET.toString() + rank.getDisplayName(),
                            ChatColor.GOLD + "Color" + Chat.LIGHT_GRAY + ": " + Chat.RESET.toString() + Chat.format(rank.getColor()) + rank.getColor(),
                            ChatColor.GOLD + "Prefix" + Chat.LIGHT_GRAY + ": " + Chat.RESET.toString() + Chat.format(rank.getPrefix()),
                            ChatColor.GOLD + "Priority" + Chat.LIGHT_GRAY + ": " + Chat.RESET.toString() + Chat.format(String.valueOf(rank.getPriority())),
                            " ",
                            Chat.LIGHT_GRAY.toString() + Chat.ITALIC + "Click to view all rank information...",
                            Chat.LIGHT_GRAY.toString() + Chat.STRIKETHROUGH + Chat.LINE
                    );
                    fancyMessage.command("/rank info " + rank.getId());
                    fancyMessage.send(sender);
                }else{
                    sender.sendMessage(rank.getCoolName() + Chat.RESET.toString() + " - " + rank.getId());
                }
            }
            sender.sendMessage(Chat.LIGHT_GRAY.toString() + Chat.STRIKETHROUGH + Chat.LINE);
        }, 1L);
    }

    @Command(names = {"rank info", "rank information"}, permission = "Nitrogen.rank.admin", description = "View information about a specified rank.")
    public static void rankInfo(CommandSender sender, @Param(name = "rank") Rank rank){
        sender.sendMessage(Chat.LIGHT_GRAY.toString() + Chat.STRIKETHROUGH + Chat.LINE);
        sender.sendMessage(rank.getCoolName() + Chat.RESET.toString() + " Rank " + Chat.LIGHT_GRAY + "⎜ " + Chat.RESET.toString() + "Information");
        sender.sendMessage(Chat.LIGHT_GRAY.toString() + Chat.STRIKETHROUGH + Chat.LINE);
        sender.sendMessage(ChatColor.GOLD + "Rank ID" + Chat.LIGHT_GRAY + ": " + Chat.RESET.toString() + rank.getId());
        sender.sendMessage(ChatColor.GOLD + "Display Name" + Chat.LIGHT_GRAY + ": " + Chat.RESET.toString() + rank.getDisplayName());
        sender.sendMessage(ChatColor.GOLD + "Color" + Chat.LIGHT_GRAY + ": " + Chat.RESET.toString() + Chat.format(rank.getColor()) + rank.getColor());
        sender.sendMessage(ChatColor.GOLD + "Prefix" + Chat.LIGHT_GRAY + ": " + Chat.RESET.toString() + Chat.format(rank.getPrefix()));
        sender.sendMessage(ChatColor.GOLD + "Priority" + Chat.LIGHT_GRAY + ": " + Chat.RESET.toString() + Chat.format(String.valueOf(rank.getPriority())));
        sender.sendMessage(ChatColor.GOLD + "Permissions (" + rank.getPermissions().size() + ")" + Chat.LIGHT_GRAY.toString() + ": " + Chat.RESET.toString() + rank.getPermissions());
        sender.sendMessage(ChatColor.GOLD + "Inherits (" + rank.getInherits().size() + ")" + Chat.LIGHT_GRAY.toString() + ": " + Chat.RESET.toString() + rank.getInherits());
        sender.sendMessage(Chat.LIGHT_GRAY.toString() + Chat.STRIKETHROUGH + Chat.LINE);
    }

}
