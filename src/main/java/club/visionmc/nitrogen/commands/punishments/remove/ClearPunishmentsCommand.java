package club.visionmc.nitrogen.commands.punishments.remove;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.punishment.Punishment;
import club.visionmc.nitrogen.punishment.PunishmentType;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.xeon.command.Command;
import club.visionmc.xeon.command.param.Param;
import org.bukkit.command.CommandSender;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class ClearPunishmentsCommand {

    @Command(names = {"clearpunishments"}, permission = "console")
    public static void clearPunishments(CommandSender sender, @Param(name = "type") PunishmentType punishmentType, @Param(name = "reason", wildcard = true) String reason){
        sender.sendMessage(Chat.format("&aClearing all punishments with type of " + punishmentType.name() + "..."));
        int index = 0;
        for(Punishment punishment : Nitrogen.getInstance().getPunishmentHandler().getPunishments()){
            if(punishment.getPunishmentType() == punishmentType){
                if(punishment.getRemovedAt() == 0) {
                    punishment.setRemovedAt(System.currentTimeMillis());
                    punishment.setRemovedBy(Profile.getConsoleUUID());
                    punishment.setRemovedReason(reason);
                    punishment.save();
                    index++;
                }
            }
        }
        sender.sendMessage(Chat.format("&aSuccessfully removed a total of &e" + index + " punishment" + (index == 1 ? "" : "s") + " &awith type &e" + punishmentType.name() + "&a."));
    }

}