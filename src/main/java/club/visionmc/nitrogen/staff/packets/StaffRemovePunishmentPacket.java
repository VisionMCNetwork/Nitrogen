package club.visionmc.nitrogen.staff.packets;

import club.visionmc.nitrogen.punishment.Punishment;
import club.visionmc.nitrogen.redis.RedisPacket;
import club.visionmc.nitrogen.server.NitrogenServer;
import com.google.gson.JsonObject;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class StaffRemovePunishmentPacket extends RedisPacket {

    private final Punishment punishment;
    private final boolean announce;
    private final NitrogenServer server;

    public StaffRemovePunishmentPacket(Punishment punishment, boolean announce, NitrogenServer server){
        this.punishment = punishment;
        this.announce = announce;
        this.server = server;
    }

    public StaffRemovePunishmentPacket(){
        this.punishment = null;
        this.server = null;
        this.announce = false;
    }

    @Override
    public String getIdentifier() {
        return "punishment-remove-packet";
    }

    @Override
    public JsonObject serialized() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uuid", punishment.getUuid().toString());
        jsonObject.addProperty("punishmentType", punishment.getPunishmentType().name());
        jsonObject.addProperty("target", punishment.getTarget().toString());
        jsonObject.addProperty("addedBy", punishment.getAddedBy().toString());
        jsonObject.addProperty("server", punishment.getAddedServer().getName());
        jsonObject.addProperty("reason", punishment.getReason());
        jsonObject.addProperty("duration", punishment.getDuration());
        jsonObject.addProperty("remainingText", punishment.getRemainingText());
        jsonObject.addProperty("announce", announce);
        jsonObject.addProperty("removedBy", punishment.getRemovedBy().toString());
        jsonObject.addProperty("removedReason", punishment.getRemovedReason());
        jsonObject.addProperty("removedServer", server.getName());
        return jsonObject;
    }
}