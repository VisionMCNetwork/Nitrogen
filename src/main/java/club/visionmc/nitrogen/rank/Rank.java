package club.visionmc.nitrogen.rank;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Topu
 * @date 11/26/2022
 */
@Getter @Setter
public class Rank {

    private UUID uuid;
    private String name = "";
    private String prefix = "§f";
    private String suffix = "§f";
    private String color = "§f";
    private String displayName = "";

    private int priority = 0;
    private boolean staff = false;
    private boolean hidden = false;
    private boolean grantable = true;
    private boolean defaultRank = false;

    private ArrayList<UUID> inherits;
    private Map<String, String> permissions;

    @Getter
    private static List<Rank> ranks = new ArrayList<>();

}
