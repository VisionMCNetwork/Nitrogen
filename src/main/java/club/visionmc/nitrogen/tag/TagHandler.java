package club.visionmc.nitrogen.tag;

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
public class TagHandler {

    @Getter
    private static List<Tag> tags = new ArrayList<>();

    private final MongoHandler mongoHandler = Nitrogen.getInstance().getMongoHandler();

    public void init(){
        int index = 0;
        for(Document document : mongoHandler.getTags().find()){
            tags.add(parseTag(document));
            index++;
        }
        Nitrogen.getInstance().log(ChatColor.GREEN + "Loaded a total of " + ChatColor.YELLOW + index + " tags" + (index == 1 ? "" : "s") + ChatColor.GREEN + ".");
    }

    public void recalculateTags(){
        List<Tag> temp = Lists.newArrayList();
        for(Document document : mongoHandler.getTags().find()){
            temp.add(parseTag(document));
        }
        this.tags = temp;
    }

    public List<Tag> getTagsInOrder(){
        List<Tag> tags = Lists.newArrayList(this.tags);
        tags.sort((o1, o2) -> o2.getPriority() - o1.getPriority());
        return tags;
    }

    public Tag getTag(String id){
        for(Tag prefix : tags){
            if(prefix.getId().equalsIgnoreCase(id.toLowerCase())){
                return prefix;
            }
        }
        return null;
    }

    public boolean tagExists(String id){
        for(Tag prefix : tags){
            if(prefix.getId().equals(id.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public void createTag(String name){
        mongoHandler.getTags().insertOne(parsePrefixToDocument(new Tag(name.toLowerCase(), name, "&7[&f" + name + "&7]&f", "&f", 1, true, false)));
    }

    public void deleteTag(Tag tag){
        mongoHandler.getTags().deleteOne(parsePrefixToDocument(tag));
    }

    public Tag parseTag(Document document){
        return new Tag(document.getString("id"), document.getString("displayName"), document.getString("prefix"), document.getString("color"), document.getInteger("priority"), document.getBoolean("grantable"), document.getBoolean("hidden"));
    }

    public Document parsePrefixToDocument(Tag tag){
        return new Document("id", tag.getId())
                .append("displayName", tag.getDisplayName())
                .append("prefix", tag.getPrefix())
                .append("color", tag.getColor())
                .append("priority", tag.getPriority())
                .append("grantable", tag.isGrantable())
                .append("hidden", tag.isHidden());
    }

}
