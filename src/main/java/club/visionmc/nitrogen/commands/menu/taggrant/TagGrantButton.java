package club.visionmc.nitrogen.commands.menu.taggrant;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.grant.tag.TagGrant;
import club.visionmc.nitrogen.profile.Profile;
import club.visionmc.nitrogen.tag.Tag;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.nitrogen.util.ColorUtils;
import club.visionmc.nitrogen.util.TimeUtil;
import club.visionmc.xeon.menu.Button;
import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;
import java.util.UUID;

/**
 * @author Topu
 * @date 11/27/2022
 */
public class TagGrantButton extends Button {

    private final Profile target;
    private final Tag tag;

    public TagGrantButton(Profile target, Tag tag){
        this.target = target;
        this.tag = tag;
    }

    @Override
    public String getName(Player player) {
        return tag.getDisplayName();
    }

    @Override
    public List<String> getDescription(Player player) {
        List<String> description = Lists.newArrayList();
        description.add(Chat.LIGHT_GRAY.toString() + Chat.STRIKETHROUGH + Chat.LINE);
        description.add(Chat.INDIGO + "Click to grant " + Chat.RESET.toString() + Chat.format(target.getHighestTag().getColor() + target.getUsername()) + Chat.RESET.toString() + Chat.INDIGO + " the " + Chat.RESET.toString() + tag.getDisplayName() + Chat.RESET.toString() + Chat.INDIGO + " tag.");
        description.add(Chat.LIGHT_GRAY.toString() + Chat.STRIKETHROUGH + Chat.LINE);
        return description;
    }

    @Override
    public Material getMaterial(Player player) {
        return Material.WOOL;
    }

    @Override
    public byte getDamageValue(Player player) {
        return (byte) ColorUtils.getWoolColor(tag.getColor());
    }



    @Override
    public void clicked(Player player, int slot, ClickType clickType) {
        startReasonConversation(player, new TagGrant(UUID.randomUUID(), target.getUuid(), player.getUniqueId(), tag, System.currentTimeMillis(), null, 0L, null, null, null));
    }

    private void startReasonConversation(Player player, TagGrant grant){
        player.closeInventory();
        ConversationFactory factory = new ConversationFactory(Nitrogen.getInstance()).withModality(true).withPrefix(new NullConversationPrefix()).withFirstPrompt(new StringPrompt() {
            public String getPromptText(ConversationContext context) {
                return Chat.YELLOW + "Please type a reason for this grant, or type " + Chat.LIGHT_RED + "cancel " + Chat.YELLOW + "to cancel.";
            }

            public Prompt acceptInput(ConversationContext context, String input) {
                if (input.equalsIgnoreCase("cancel")) {
                    context.getForWhom().sendRawMessage(Chat.LIGHT_RED + "Tag Grant creation process aborted.");
                    return Prompt.END_OF_CONVERSATION;
                }
                Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
                    grant.setReason(input);
                    startDurationConversation(player, grant);
                }, 1L);

                return Prompt.END_OF_CONVERSATION;
            }
        }).withEscapeSequence("/no").withLocalEcho(false).withTimeout(10).thatExcludesNonPlayersWithMessage("Go away evil console!");
        Conversation con = factory.buildConversation(player);
        player.beginConversation(con);
    }

    private void startDurationConversation(Player player, TagGrant grant) {
        player.closeInventory();
        ConversationFactory factory = new ConversationFactory(Nitrogen.getInstance()).withModality(true).withPrefix(new NullConversationPrefix()).withFirstPrompt(new StringPrompt() {
            public String getPromptText(ConversationContext context) {
                return Chat.YELLOW + "Please type a duration for this grant; type \"perm\" for permanent, or type " + Chat.LIGHT_RED + "cancel " + Chat.YELLOW + "to cancel.";
            }

            public Prompt acceptInput(ConversationContext context, String input) {
                if (input.equalsIgnoreCase("cancel")) {
                    context.getForWhom().sendRawMessage(Chat.LIGHT_RED + "Grant creation process aborted.");
                    return Prompt.END_OF_CONVERSATION;
                }
                if(TimeUtil.parseTime(input) <= 0 && !input.startsWith("perm")){
                    player.sendMessage(Chat.LIGHT_RED + "Invalid time provided, grant removal aborted.");
                    return Prompt.END_OF_CONVERSATION;
                }
                Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
                    grant.setDuration((input.startsWith("perm") ? Long.MAX_VALUE : TimeUtil.parseTime(input)));
                    Nitrogen.getInstance().getTagGrantHandler().createTagGrant(grant);
                    player.sendMessage(Chat.LIGHT_GREEN + "Successfully granted " + Chat.RESET.toString() + Chat.format(tag.getColor() + target.getUsername()) + Chat.RESET.toString() + Chat.LIGHT_GREEN + " the "
                            + Chat.RESET.toString() + tag.getDisplayName() + Chat.RESET.toString() + Chat.LIGHT_GREEN + " rank "
                            + (grant.getDuration() == Long.MAX_VALUE ? "indefinitely" : "for a period of " + Chat.YELLOW + TimeUtil.formatDuration(grant.getDuration()) + Chat.LIGHT_GREEN + " due to " + Chat.YELLOW + grant.getReason() + Chat.LIGHT_GREEN + "."));
                }, 1L);
                Player target = Nitrogen.getInstance().getServer().getPlayer(grant.getTarget());
                if(target != null){
                    target.sendMessage(Chat.format("&aYou have been granted &r" + grant.getTag().getColor() + grant.getTag().getDisplayName() + " &arank " + (grant.getDuration() == Long.MAX_VALUE ? "indefinitely" : "for a period of &e" + grant.getRemainingText() + "&r") + "&a."));
                }
                return Prompt.END_OF_CONVERSATION;
            }
        }).withEscapeSequence("/no").withLocalEcho(false).withTimeout(10).thatExcludesNonPlayersWithMessage("Go away evil console!");
        Conversation con = factory.buildConversation(player);
        player.beginConversation(con);
    }
}