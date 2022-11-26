package club.visionmc.nitrogen.tag;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.rank.Rank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Topu
 * @date 11/26/2022
 */
@Getter @Setter
@Data
@AllArgsConstructor
public class Tag {

    private String id;
    private String displayName;
    private String prefix;
    private String color;

    private int priority;

    private boolean grantable;
    private boolean hidden;

    public void save(){
        Document filter = new Document("id", this.id);
        Document old = Nitrogen.getInstance().getMongoHandler().getTags().find(filter).first();
        if(old == null) return;
        Document replace = new Document("id", this.id)
                .append("displayName", this.displayName)
                .append("prefix", this.prefix)
                .append("color", this.color)
                .append("priority", this.priority)
                .append("grantable", this.grantable)
                .append("hidden", this.hidden);
        Nitrogen.getInstance().getMongoHandler().getTags().replaceOne(old, replace);
    }
}
