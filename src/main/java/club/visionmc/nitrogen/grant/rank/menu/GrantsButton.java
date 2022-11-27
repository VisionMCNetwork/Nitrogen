package club.visionmc.nitrogen.grant.rank.menu;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.grant.rank.Grant;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.profile.ProfileHandler;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.nitrogen.util.TimeUtil;
import club.visionmc.xeon.menu.Button;
import club.visionmc.xeon.util.TimeUtils;
import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.Date;
import java.util.List;

/**
 * @author Topu
 * @date 11/27/2022
 */
public class GrantsButton extends Button {

    private final ProfileHandler profileHandler = Nitrogen.getInstance().getProfileHandler();

    private final Grant grant;

    public GrantsButton(Grant grant){
        this.grant = grant;
    }

    @Override
    public String getName(Player player) {
        return Chat.ORANGE + TimeUtils.formatIntoCalendarString(new Date(grant.getAddedAt()));
    }

    @Override
    public List<String> getDescription(Player player) {
        List<String> description = Lists.newArrayList();

        Profile addedBy = profileHandler.getProfileOrCreate(grant.getAddedBy());

        description.add(Chat.LIGHT_GRAY.toString() + Chat.STRIKETHROUGH + Chat.LINE);
        description.add(Chat.YELLOW + "Rank" + Chat.LIGHT_GRAY + ": " + Chat.RESET.toString() + Chat.format(grant.getRank().getCoolName()));
        description.add(Chat.YELLOW + "Added By" + Chat.LIGHT_GRAY + ": " + Chat.RESET.toString() + (addedBy == null || grant.getAddedBy().toString().equalsIgnoreCase(Profile.consoleUUID.toString()) ? Chat.DARK_RED.toString() + Chat.BOLD + "Console" : Chat.format(addedBy.getHighestRank().getColor() + addedBy.getUsername())));
        description.add(Chat.YELLOW + "Added Reason" + Chat.LIGHT_GRAY + ": " + Chat.RESET.toString() + grant.getReason());
        if(grant.isActive()){
            description.add(" ");
            description.add(Chat.LIGHT_RED.toString() + Chat.BOLD + "Click to remove this grant.");
            if(grant.getDuration() != Long.MAX_VALUE){
                description.add(Chat.LIGHT_GRAY.toString() + Chat.STRIKETHROUGH + Chat.LINE);
                description.add(Chat.YELLOW + "Grant expires in " + Chat.LIGHT_RED.toString() + grant.getRemainingText() + Chat.RESET.toString() + Chat.YELLOW + ".");
                //TimeUtils.formatLongIntoDetailedString((this.time + this.duration - System.currentTimeMillis()) / 1000)
            }
        }else{
            String removedBy;
            if(grant.getRemovedBy() == null && grant.getDuration() != Long.MAX_VALUE){
                removedBy = Chat.DARK_RED.toString() + Chat.BOLD + "Console";
            }else{
                removedBy = (grant.getRemovedBy().toString().equalsIgnoreCase(Profile.getConsoleUUID().toString()) || grant.getRemovedBy() == null ? Chat.DARK_RED.toString() + Chat.BOLD + "Console" : profileHandler.getProfileOrCreate(grant.getRemovedBy()).getHighestRank().getColor() + profileHandler.getProfile(grant.getRemovedBy()).getUsername());
            }
            description.add(Chat.LIGHT_GRAY.toString() + Chat.STRIKETHROUGH + Chat.LINE);
            description.add(Chat.LIGHT_RED.toString() + Chat.BOLD + "Removed by " + Chat.RESET.toString() + Chat.format(removedBy) + Chat.RESET.toString() + Chat.LIGHT_GRAY + ":");
            description.add(Chat.LIGHT_RED + "The grant was removed for" + Chat.LIGHT_GRAY + ": " + Chat.RESET.toString() + grant.getRemovedReason());
            description.add(Chat.LIGHT_RED + "at " + Chat.ORANGE + TimeUtils.formatIntoCalendarString(new Date(grant.getRemovedAt())) + Chat.LIGHT_RED + ".");
            if(grant.getDuration() != Long.MAX_VALUE){
                description.add(" ");
                description.add(Chat.LIGHT_RED + "Initial Duration" + Chat.LIGHT_GRAY + ": " + Chat.ORANGE + TimeUtil.formatDuration(grant.getDuration()));
            }
        }
        description.add(Chat.LIGHT_GRAY.toString() + Chat.STRIKETHROUGH + Chat.LINE);
        return description;
    }

    @Override
    public Material getMaterial(Player player) {
        return Material.WOOL;
    }

    @Override
    public byte getDamageValue(Player player) {
        return (byte) (!grant.isActive() ? 14 : 5);
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType) {
        if(grant.getRank().getId().equalsIgnoreCase("default")){
            player.sendMessage(Chat.LIGHT_RED + "You do not have permission to remove this grant.");
            return;
        }
        if(grant.isActive()) {
            removalReasonConversation(player);
        }
    }

    private void removalReasonConversation(Player player){
        player.closeInventory();
        ConversationFactory factory = new ConversationFactory(Nitrogen.getInstance()).withModality(true).withPrefix(new NullConversationPrefix()).withFirstPrompt(new StringPrompt() {
            public String getPromptText(ConversationContext context) {
                return Chat.YELLOW + "Please type a reason for this grant to be removed, or type " + Chat.LIGHT_RED + "cancel " + Chat.YELLOW + "to cancel.";
            }

            public Prompt acceptInput(ConversationContext context, String input) {
                if (input.equalsIgnoreCase("cancel")) {
                    context.getForWhom().sendRawMessage(Chat.LIGHT_RED + "Grant removal process aborted.");
                    return Prompt.END_OF_CONVERSATION;
                }
                Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
                    grant.setRemovedReason(input);
                    grant.setRemovedAt(System.currentTimeMillis());
                    grant.setRemovedBy(player.getUniqueId());
                    grant.save();
                    player.sendMessage(Chat.LIGHT_GREEN + "The grant has been removed.");
                }, 1L);

                return Prompt.END_OF_CONVERSATION;
            }
        }).withEscapeSequence("/no").withLocalEcho(false).withTimeout(10).thatExcludesNonPlayersWithMessage("Go away evil console!");
        Conversation con = factory.buildConversation(player);
        player.beginConversation(con);
    }
}