package club.visionmc.nitrogen.util;

import club.visionmc.nitrogen.Nitrogen;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class PermissionUtils {

    private static final Map<UUID, PermissionAttachment> attachments = Maps.newHashMap();

    public static void updatePermissions(Player player){
        try {
            try {
                if (getAttachment(player) != null) player.removeAttachment(getAttachment(player));
            } catch (IllegalArgumentException ignored) {}
            PermissionAttachment attachment = player.addAttachment(Nitrogen.getInstance());

            List<String> permissions = Lists.newArrayList(Nitrogen.getInstance().getProfileHandler().getProfileOrCreate(player.getUniqueId()).getPermissions());
            for (String permission : permissions) {
                attachment.setPermission((permission.startsWith("-") ? permission.substring(1) : permission), !permission.startsWith("-"));
            }
            attachments.put(player.getUniqueId(), attachment);
            player.recalculatePermissions();
        }catch(ConcurrentModificationException ignored){}
    }

    public static PermissionAttachment getAttachment(Player player){
        return attachments.getOrDefault(player.getUniqueId(), null);
    }

}
