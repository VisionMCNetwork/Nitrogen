package club.visionmc.nitrogen.server;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.database.MongoHandler;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.nitrogen.util.TPSUtils;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.bson.Document;

import java.util.List;
import java.util.UUID;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class ServerHandler {

    @Getter private List<NitrogenServer> servers = Lists.newArrayList();

    private final MongoHandler mongoHandler = Nitrogen.getInstance().getMongoHandler();

    public void init(){
        int index = 0;
        for(Document document : mongoHandler.getServers().find()){
            servers.add(parseServer(document));
            index++;
        }
        if(!serverExists(Nitrogen.getInstance().getServer().getServerName())){
            createServer(Nitrogen.getInstance().getServer().getServerName());
        }
        Nitrogen.getInstance().log(Chat.LIGHT_GREEN + "Loaded a total of " + Chat.YELLOW + index + " server" + (index == 1 ? "" : "s") + Chat.LIGHT_GREEN + ".");
    }

    public void recalculateServers(){
        List<NitrogenServer> temp = Lists.newArrayList();
        for(Document document : mongoHandler.getServers().find()){
            temp.add(parseServer(document));
        }
        this.servers = temp;
        getServer(Nitrogen.getInstance().getServer().getServerName()).setServerStatus(ServerStatus.ONLINE);
    }

    public void refreshServerData(){
        NitrogenServer server = getServer(Nitrogen.getInstance().getServer().getServerName());
        server.setMaxPlayerCount(Nitrogen.getInstance().getServer().getMaxPlayers());
        server.setLastPlayerCount(Nitrogen.getInstance().getServer().getOnlinePlayers().size());
        server.setLastTps(TPSUtils.getTPS());
        server.save();
    }

    public void createServer(String name){
        if(!serverExists(name)) {
            NitrogenServer server = new NitrogenServer(name, "No description defined.", 0, Lists.newArrayList(), 0, 0, ServerStatus.ONLINE, Lists.newArrayList());
            mongoHandler.getServers().insertOne(parseServerToDocument(server));
        }
    }

    public void deleteServer(NitrogenServer server){
        Document document = parseServerToDocument(server);
        mongoHandler.getServers().deleteOne(document);
    }

    public NitrogenServer getServer(String name){
        if(serverExists(name)){
            for(NitrogenServer server : servers){
                if(server.getName().equalsIgnoreCase(name)){
                    return server;
                }
            }
        }
        return null;
    }

    public boolean serverExists(String name){
        for(NitrogenServer server : servers){
            if(server.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }


    public NitrogenServer parseServer(Document document){
        List<UUID> uuids = Lists.newArrayList();
        for(String uuid : (List<String>) document.get("onlinePlayers")){
            uuids.add(UUID.fromString(uuid));
        }
        return new NitrogenServer(document.getString("name"), document.getString("description"), document.getDouble("lastTps"), (List<String>) document.get("announcements"), document.getInteger("lastPlayerCount"), document.getInteger("maxPlayerCount"), ServerStatus.valueOf(document.getString("serverStatus")), uuids);
    }

    public Document parseServerToDocument(NitrogenServer server){
        List<String> uuids = Lists.newArrayList();
        for(UUID uuid : server.getOnlinePlayers()){
            uuids.add(uuid.toString());
        }
        return new Document("name", server.getName())
                .append("description", server.getDescription())
                .append("lastTps", server.getLastTps())
                .append("announcements", server.getAnnouncements())
                .append("lastPlayerCount", server.getLastPlayerCount())
                .append("maxPlayerCount", server.getMaxPlayerCount())
                .append("serverStatus", server.getServerStatus().toString())
                .append("onlinePlayers", uuids);
    }




}