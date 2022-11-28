package club.visionmc.nitrogen.redis;

import com.google.gson.JsonObject;

/**
 * @author Topu
 * @date 11/28/2022
 */
public abstract class RedisPacket {

    public abstract String getIdentifier();

    public abstract JsonObject serialized();
}
