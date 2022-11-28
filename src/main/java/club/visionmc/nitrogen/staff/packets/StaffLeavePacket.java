package club.visionmc.nitrogen.staff.packets;

import club.visionmc.nitrogen.redis.RedisPacket;
import com.google.gson.JsonObject;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class StaffLeavePacket extends RedisPacket {

    private final String server;

    private final String player;

    public StaffLeavePacket(){
        this.server = null;
        this.player = null;
    }

    public StaffLeavePacket(String server, String player) {
        this.server = server;
        this.player = player;
    }

    @Override
    public String getIdentifier() {
        return "staff-quit";
    }

    @Override
    public JsonObject serialized() {
        JsonObject object = new JsonObject();
        object.addProperty("server", this.server);
        object.addProperty("player", this.player);
        return object;

    }

}