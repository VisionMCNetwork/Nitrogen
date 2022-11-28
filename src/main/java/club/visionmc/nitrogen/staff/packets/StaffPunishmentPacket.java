package club.visionmc.nitrogen.staff.packets;

import club.visionmc.nitrogen.punishment.Punishment;
import club.visionmc.nitrogen.redis.RedisPacket;
import com.google.gson.JsonObject;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class StaffPunishmentPacket extends RedisPacket {

    private final Punishment punishment;
    private final boolean announce;

    public StaffPunishmentPacket(Punishment punishment, boolean announce){
        this.punishment = punishment;
        this.announce = announce;
    }

    public StaffPunishmentPacket(){
        this.punishment = null;
        this.announce = false;
    }

    @Override
    public String getIdentifier() {
        return "punishment-packet";
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
        return jsonObject;
    }
}