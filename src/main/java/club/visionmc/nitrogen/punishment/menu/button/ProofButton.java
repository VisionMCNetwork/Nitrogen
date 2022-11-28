package club.visionmc.nitrogen.punishment.menu.button;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.punishment.Punishment;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.xeon.menu.Button;
import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class ProofButton extends Button {

    private final Punishment punishment;
    private final String entry;

    public ProofButton(Punishment punishment, String entry){
        this.punishment = punishment;
        this.entry = entry;
    }

    @Override
    public String getName(Player player) {
        return Chat.YELLOW + entry;
    }

    @Override
    public List<String> getDescription(Player player) {
        List<String> description = Lists.newArrayList();
        description.add(Chat.format("&7&m") + Chat.LINE);
        description.add(Chat.format("&c&lRight click to remove this proof entry."));
        description.add(Chat.format("&a&lLeft click to view this proof entry."));
        description.add(Chat.format("&7&m") + Chat.LINE);
        return description;
    }

    @Override
    public Material getMaterial(Player player) {
        return Material.WOOL;
    }

    @Override
    public byte getDamageValue(Player player) {
        return (byte) 5;
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType) {
        switch(clickType){
            case LEFT: {
                player.sendMessage(Chat.YELLOW + entry);
                break;
            }case RIGHT: {
                startRemoveConversation(player);
            } default: {

            }
        }
    }

    private void startRemoveConversation(Player player){
        player.closeInventory();
        ConversationFactory factory = new ConversationFactory(Nitrogen.getInstance()).withModality(true).withPrefix(new NullConversationPrefix()).withFirstPrompt(new StringPrompt() {
            public String getPromptText(ConversationContext context) {
                return Chat.YELLOW + "Please type " + Chat.LIGHT_RED + "confirm" + Chat.YELLOW + " to confirm removal of this proof entry, or type " + Chat.LIGHT_RED + "cancel " + Chat.YELLOW + "to cancel.";
            }

            public Prompt acceptInput(ConversationContext context, String input) {
                if (input.equalsIgnoreCase("cancel")) {
                    context.getForWhom().sendRawMessage(Chat.LIGHT_RED + "Proof entry removal process aborted.");
                    return Prompt.END_OF_CONVERSATION;
                } else if (input.equalsIgnoreCase("confirm")) {
                    Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
                        punishment.getProof().remove(entry);
                        punishment.save();
                        player.sendMessage(Chat.format("&aSuccessfully removed the proof entry."));
                    }, 1L);

                    return Prompt.END_OF_CONVERSATION;
                }else{
                    player.sendMessage(Chat.format("&cInvalid response; proof entry removal process aborted."));
                    return Prompt.END_OF_CONVERSATION;
                }
            }
        }).withEscapeSequence("/no").withLocalEcho(false).withTimeout(10).thatExcludesNonPlayersWithMessage("Go away evil console!");
        Conversation con = factory.buildConversation(player);
        player.beginConversation(con);
    }
}