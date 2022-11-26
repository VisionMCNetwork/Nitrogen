package club.visionmc.nitrogen.profile;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author Topu
 * @date 11/26/2022
 */
@Getter @Setter
public class Profile {

    private UUID uuid;
    private String username = "";
    private String prefix = "";
    private String suffix = "";
    private String color = "";
    private String connectedServer;

    private List<String> activePermissions;
    private HashMap<String, String> permissions;

    private long firstJoined = System.currentTimeMillis();
    private long lastJoined = System.currentTimeMillis();
    private long lastQuit = System.currentTimeMillis();


}
