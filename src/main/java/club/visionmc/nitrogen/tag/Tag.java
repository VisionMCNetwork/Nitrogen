package club.visionmc.nitrogen.tag;

import club.visionmc.nitrogen.rank.Rank;
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
public class Tag {

    private UUID uuid;
    private String name = "";
    private String prefix = "§f";
    private String color = "§f";
    private String displayName = "";

    private int priority = 0;

    private boolean grantable = true;
    private boolean hidden = false;

    @Getter
    private static List<Tag> tags = new ArrayList<>();
}
