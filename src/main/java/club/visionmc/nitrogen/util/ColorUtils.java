package club.visionmc.nitrogen.util;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class ColorUtils {

    public static short getWoolColor(String str){
        if(str.contains("&1") || str.contains("&9")) return 11;
        if(str.contains("&2")) return 13;
        if(str.contains("&3")) return 9;
        if(str.contains("&4") || str.contains("&c")) return 14;
        if(str.contains("&5")) return 10;
        if(str.contains("&6")) return 1;
        if(str.contains("&7")) return 8;
        if(str.contains("&8")) return 7;
        if(str.contains("&a")) return 5;
        if(str.contains("&b")) return 3;
        if(str.contains("&d")) return 6;
        if(str.contains("&e")) return 4;
        return 0;
    }

    public static DyeColor getDyeColor(String str){
        if(str.contains("&1") || str.contains("&9")) return DyeColor.BLUE;
        if(str.contains("&2")) return DyeColor.GREEN;
        if(str.contains("&3")) return DyeColor.CYAN;
        if(str.contains("&4") || str.contains("&c")) return DyeColor.RED;
        if(str.contains("&5")) return DyeColor.PURPLE;
        if(str.contains("&6")) return DyeColor.ORANGE;
        if(str.contains("&7")) return DyeColor.GRAY;
        if(str.contains("&8")) return DyeColor.SILVER;
        if(str.contains("&a")) return DyeColor.LIME;
        if(str.contains("&b")) return DyeColor.LIGHT_BLUE;
        if(str.contains("&d")) return DyeColor.PINK;
        if(str.contains("&e")) return DyeColor.YELLOW;
        return DyeColor.WHITE;
    }

    public static ChatColor convertToChatColor(String color){
        ChatColor toReturn;
        if(color.contains("&1")){
            toReturn = Chat.DARK_BLUE;
        }else if(color.contains("&2")){
            toReturn = Chat.DARK_GREEN;
        }else if(color.contains("&3")){
            toReturn = Chat.CYAN;
        }else if(color.contains("&4")){
            toReturn = Chat.DARK_RED;
        }else if(color.contains("&5")){
            toReturn = Chat.PURPLE;
        }else if(color.contains("&6")){
            toReturn = Chat.ORANGE;
        }else if(color.contains("&7")){
            toReturn = Chat.LIGHT_GRAY;
        }else if(color.contains("&8")){
            toReturn = Chat.DARK_GRAY;
        }else if(color.contains("&9")){
            toReturn = Chat.INDIGO;
        }else if(color.contains("&a")){
            toReturn = Chat.LIGHT_GREEN;
        }else if(color.contains("&b")){
            toReturn = Chat.LIGHT_BLUE;
        }else if(color.contains("&c")){
            toReturn = Chat.LIGHT_RED;
        }else if(color.contains("&d")){
            toReturn = Chat.PINK;
        }else if(color.contains("&e")){
            toReturn = Chat.YELLOW;
        }else if(color.contains("&f")){
            toReturn = Chat.WHITE;
        }else if(color.contains("&k")) {
            toReturn = Chat.OBFUSCATION;
        }else{
            toReturn = Chat.RESET;
        }
        return toReturn;
    }

    public static ChatColor italic(String color){
        return color.contains("&o") ? Chat.ITALIC : null;
    }

    public static ChatColor bold(String color){
        return color.contains("&l") ? Chat.BOLD : null;
    }

}