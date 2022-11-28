package club.visionmc.nitrogen.rank;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.event.rank.RankSaveEvent;
import club.visionmc.nitrogen.util.Chat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.List;

/**
 * @author Topu
 * @date 11/26/2022
 */
@Getter
@Setter
@Data
@AllArgsConstructor
public class Rank {

    private String id;
    private String displayName;
    private String prefix;
    private String color;

    private int priority;
    private boolean staff;
    private boolean hidden;
    private boolean grantable;
    private boolean defaultRank;

    private List<String> inherits;
    private List<String> permissions;

    public String getCoolName(){
        return Chat.format(this.color + this.displayName);
    }

    public void save() {
        Document filter = new Document("id", this.id);
        Document old = Nitrogen.getInstance().getMongoHandler().getRanks().find(filter).first();
        if (old == null) return;
        Document replace = new Document("id", this.id)
                .append("displayName", this.displayName)
                .append("prefix", this.prefix)
                .append("color", this.color)
                .append("priority", this.priority)
                .append("staff", this.staff)
                .append("hidden", this.hidden)
                .append("grantable", this.grantable)
                .append("defaultRank", this.defaultRank)
                .append("inherits", this.inherits)
                .append("permissions", this.permissions);
        Nitrogen.getInstance().getMongoHandler().getRanks().replaceOne(old, replace);
        Nitrogen.getInstance().getServer().getPluginManager().callEvent(new RankSaveEvent(this));
    }

}
