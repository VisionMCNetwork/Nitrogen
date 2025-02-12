package club.visionmc.nitrogen.util;

import org.bukkit.entity.Player;
import java.util.UUID;
import java.util.HashMap;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class Cooldowns {

    public static HashMap<String, HashMap<UUID, Long>> cooldown;

    public static void createCooldown(final String k) {
        if (Cooldowns.cooldown.containsKey(k)) {
            throw new IllegalArgumentException("Cooldown already exists.");
        }
        Cooldowns.cooldown.put(k, new HashMap<UUID, Long>());
    }

    public static HashMap<UUID, Long> getCooldownMap(final String k) {
        if (Cooldowns.cooldown.containsKey(k)) {
            return Cooldowns.cooldown.get(k);
        }
        return null;
    }

    public static void addCooldown(final String k, final Player p, final int seconds) {
        if (!Cooldowns.cooldown.containsKey(k)) {
            throw new IllegalArgumentException(k + " does not exist");
        }
        final long next = System.currentTimeMillis() + seconds * 1000L;
        Cooldowns.cooldown.get(k).put(p.getUniqueId(), next);
    }

    public static boolean isOnCooldown(final String k, final Player p) {
        return Cooldowns.cooldown.containsKey(k) && Cooldowns.cooldown.get(k).containsKey(p.getUniqueId()) && System.currentTimeMillis() <= Cooldowns.cooldown.get(k).get(p.getUniqueId());
    }

    public static boolean isOnCooldown(final String k, final UUID uuid) {
        return Cooldowns.cooldown.containsKey(k) && Cooldowns.cooldown.get(k).containsKey(uuid) && System.currentTimeMillis() <= Cooldowns.cooldown.get(k).get(uuid);
    }

    public static int getCooldownForPlayerInt(final String k, final Player p) {
        return (int)(Cooldowns.cooldown.get(k).get(p.getUniqueId()) - System.currentTimeMillis()) / 1000;
    }

    public static long getCooldownForPlayerLong(final String k, final Player p) {
        return Cooldowns.cooldown.get(k).get(p.getUniqueId()) - System.currentTimeMillis();
    }

    public static void removeCooldown(final String k, final Player p) {
        if (!Cooldowns.cooldown.containsKey(k)) {
            throw new IllegalArgumentException(k + " does not exist");
        }
        Cooldowns.cooldown.get(k).remove(p.getUniqueId());
    }

    public static void removeCooldown(final String k, final UUID uuid) {
        if (!Cooldowns.cooldown.containsKey(k)) {
            throw new IllegalArgumentException(k + " does not exist");
        }
        Cooldowns.cooldown.get(k).remove(uuid);
    }

    static {
        Cooldowns.cooldown = new HashMap<String, HashMap<UUID, Long>>();
    }
}