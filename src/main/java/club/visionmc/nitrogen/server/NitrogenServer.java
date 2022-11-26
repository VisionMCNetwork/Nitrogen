package club.visionmc.nitrogen.server;

import club.visionmc.nitrogen.Nitrogen;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.List;
import java.util.UUID;

/**
 * @author Topu
 * @date 11/26/2022
 */
@Getter
@Setter
@Data
@AllArgsConstructor
public class NitrogenServer {

    private String name;
    private String description;
    private double lastTps;
    private List<String> announcements;
    private int lastPlayerCount;
    private int maxPlayerCount;
    private ServerStatus serverStatus;
    private List<UUID> onlinePlayers;

    public void save(){
        Document filter = new Document("name", this.name);
        Document old = Nitrogen.getInstance().getMongoHandler().getServers().find(filter).first();
        if(old == null) return;
        List<String> uuids = Lists.newArrayList();
        for(UUID uuid : onlinePlayers){
            uuids.add(uuid.toString());
        }
        Document replace = new Document("name", this.name)
                .append("description", this.description)
                .append("lastTps", this.lastTps)
                .append("announcements", this.announcements)
                .append("lastPlayerCount", this.lastPlayerCount)
                .append("maxPlayerCount", this.maxPlayerCount)
                .append("serverStatus", this.serverStatus.toString())
                .append("onlinePlayers", uuids);
        Nitrogen.getInstance().getMongoHandler().getServers().replaceOne(old, replace);
    }

}