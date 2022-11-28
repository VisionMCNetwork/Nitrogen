package club.visionmc.nitrogen.staff.packets;

import club.visionmc.nitrogen.redis.RedisPacket;
import com.google.gson.JsonObject;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class StaffReportPacket extends RedisPacket {

    private String player;
    private String reported;
    private String server;
    private String message;

    public StaffReportPacket(){

    }

    public StaffReportPacket(String sender, String reported, String server, String message){
        this.player = sender;
        this.reported = reported;
        this.server = server;
        this.message = message;
    }

    @Override
    public String getIdentifier() {
        return "report";
    }

    @Override
    public JsonObject serialized() {
        JsonObject object = new JsonObject();
        object.addProperty("player", this.player);
        object.addProperty("reported", this.reported);
        object.addProperty("server", this.server);
        object.addProperty("message", this.message);
        return object;
    }
}