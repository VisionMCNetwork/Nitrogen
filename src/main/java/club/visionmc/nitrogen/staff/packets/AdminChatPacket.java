package club.visionmc.nitrogen.staff.packets;

import club.visionmc.nitrogen.redis.RedisPacket;
import com.google.gson.JsonObject;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class AdminChatPacket extends RedisPacket {


    private String sender;

    private String server;

    private String message;

    public String getIdentifier() {
        return "admin-chat";
    }

    public JsonObject serialized() {
        JsonObject object = new JsonObject();
        object.addProperty("sender", this.sender);
        object.addProperty("server", this.server);
        object.addProperty("message", this.message);
        return object;
    }

    public AdminChatPacket(String sender, String server, String message) {
        this.sender = sender;
        this.server = server;
        this.message = message;
    }

    public AdminChatPacket() {}


}