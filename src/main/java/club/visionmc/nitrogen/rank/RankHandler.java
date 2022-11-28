package club.visionmc.nitrogen.rank;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.database.MongoHandler;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class RankHandler {

    @Getter
    private static List<Rank> ranks = new ArrayList<>();

    @Getter private final MongoHandler mongoHandler = Nitrogen.getInstance().getMongoHandler();

    public void init(){
        int index = 0;
        if(!rankExistsInDB("Default")){
            createRank("Default");
        }
        for(Document document : mongoHandler.getRanks().find()){
            ranks.add(new Rank(
                    document.getString("id"),
                    document.getString("displayName"),
                    document.getString("prefix"),
                    document.getString("color"),
                    document.getInteger("priority"),
                    document.getBoolean("staff"),
                    document.getBoolean("hidden"),
                    document.getBoolean("grantable"),
                    document.getBoolean("defaultRank"),
                    (List<String>) document.get("inherits"),
                    (List<String>) document.get("permissions")

            ));
            index++;
        }
        Nitrogen.getInstance().log(ChatColor.GREEN + "Loaded a total of " + ChatColor.YELLOW + index + " rank" + (index == 1 ? "" : "s") + ChatColor.GREEN + ".");
    }

    public void recalculateRanks(){
        List<Rank> temp = Lists.newArrayList();
        for(Document document : mongoHandler.getRanks().find()){
            temp.add(new Rank(
                    document.getString("displayName"),
                    document.getString("id"),
                    document.getString("prefix"),
                    document.getString("color"),
                    document.getInteger("priority"),
                    document.getBoolean("staff"),
                    document.getBoolean("hidden"),
                    document.getBoolean("grantable"),
                    document.getBoolean("defaultRank"),
                    (List<String>) document.get("inherits"),
                    (List<String>) document.get("permissions")
            ));
        }
        this.ranks = temp;
    }

    public boolean rankExistsInDB(String id){
        Document filter = new Document("id", id);
        Document document = mongoHandler.getRanks().find(filter).first();
        return (document != null);
    }

    public boolean rankExists(String id){
        id = id.toLowerCase();
        for(Rank rank : ranks){
            if(rank.getId().equalsIgnoreCase(id)){
                return true;
            }
        }
        return false;
    }

    public List<Rank> getRanksInOrder(){
        List<Rank> ranks = Lists.newArrayList(this.ranks);
        ranks.sort((o1, o2) -> o2.getPriority() - o1.getPriority());
        return ranks;
    }

    public void createRank(String name){
        if(rankExists(name.toLowerCase())) return;
        Rank rank = new Rank(name.toLowerCase(), name, "&f", "&f", 1, false, false, true, false, Lists.newArrayList(), Lists.newArrayList());
        Document document = new Document(
                "id", rank.getId())
                .append("displayName", rank.getDisplayName())
                .append("prefix", rank.getPrefix())
                .append("color", rank.getColor())
                .append("priority", rank.getPriority())
                .append("staff", rank.isStaff())
                .append("hidden", rank.isHidden())
                .append("grantable", rank.isGrantable())
                .append("defaultRank", rank.isDefaultRank())
                .append("inherits", rank.getInherits())
                .append("permissions", rank.getPermissions());
        mongoHandler.getRanks().insertOne(document);
    }

    public void deleteRank(String id){
        id = id.toLowerCase();
        if(!rankExists(id)) return;
        Document filter = new Document("id", id.toLowerCase());
        Document document = getMongoHandler().getRanks().find(filter).first();
        if(document == null) return;
        getMongoHandler().getRanks().deleteOne(document);
    }

    public Rank getRank(String id){
        id = id.toLowerCase();
        if(!rankExists(id.toLowerCase())) return null;
        for(Rank rank : ranks){
            String id2 = rank.getId().toLowerCase();
            if(id2.equalsIgnoreCase(id)){
                return rank;
            }
        }
        return null;
    }

}
