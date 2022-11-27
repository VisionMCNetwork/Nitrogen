package club.visionmc.nitrogen.database;

import club.visionmc.nitrogen.Nitrogen;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * @author Topu
 * @date 11/26/2022
 */
public class MongoHandler {

    @Getter private MongoClient mongoClient;
    @Getter private MongoDatabase mongoDatabase;
    @Getter private MongoCollection<Document> ranks;
    @Getter private MongoCollection<Document> profiles;
    @Getter private MongoCollection<Document> punishments;
    @Getter private MongoCollection<Document> grants;
    @Getter private MongoCollection<Document> tagGrants;
    @Getter private MongoCollection<Document> servers;
    @Getter private MongoCollection<Document> tags;

    public void connect(){
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        try{
            mongoDatabase = mongoClient.getDatabase("Nitrogen");
            ranks = mongoDatabase.getCollection("ranks");
            profiles = mongoDatabase.getCollection("profiles");
            punishments = mongoDatabase.getCollection("punishments");
            grants = mongoDatabase.getCollection("grants");
            tagGrants = mongoDatabase.getCollection("taggrants");
            servers = mongoDatabase.getCollection("servers");
            tags = mongoDatabase.getCollection("tags");
            Nitrogen.getInstance().getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Successfully connected to Mongo Database System.");
        }catch(Exception e){
            Nitrogen.getInstance().getServer().getConsoleSender().sendMessage(ChatColor.RED + "Unable to connect to Mongo Database System.");
            Bukkit.getServer().getPluginManager().disablePlugin(Nitrogen.getInstance());
        }
    }

}