package club.visionmc.nitrogen.punishment;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Topu
 * @date 11/26/2022
 */
@Getter
@AllArgsConstructor
public enum PunishmentType {

    WARN("Warns", "warned", "warned", "unwarned"),
    MUTE("Mutes", "muted", "muted", "unmuted"),
    BAN("Bans", "banned", "banned", "unbanned"),
    BLACKLIST("Blacklists", "blacklisted", "blacklisted", "unblacklisted");

    private final String menuDisplay;
    private final String messageDisplay;
    private final String kickMessageDisplay;
    private final String messageDisplayRemoved;

}
