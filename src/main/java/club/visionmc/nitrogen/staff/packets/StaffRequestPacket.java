package club.visionmc.nitrogen.staff.packets;

import club.visionmc.nitrogen.redis.RedisPacket;
import com.google.gson.JsonObject;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class StaffRequestPacket extends RedisPacket {

    private String player;
    private String server;
    private String message;

    public StaffRequestPacket(){

    }

    public StaffRequestPacket(String sender, String server, String message){
        this.player = sender;
        this.server = server;
        this.message = message;
    }

    @Override
    public String getIdentifier() {
        return "request";
    }

    @Override
    public JsonObject serialized() {
        JsonObject object = new JsonObject();
        object.addProperty("player", this.player);
        object.addProperty("server", this.server);
        object.addProperty("message", this.message);
        return object;
    }
}