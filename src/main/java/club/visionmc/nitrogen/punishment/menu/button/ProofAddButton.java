package club.visionmc.nitrogen.punishment.menu.button;

import club.visionmc.nitrogen.Nitrogen;
import club.visionmc.nitrogen.punishment.Punishment;
import club.visionmc.nitrogen.util.Chat;
import club.visionmc.xeon.menu.Button;
import org.bukkit.Material;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;

/**
 * @author Topu
 * @date 11/28/2022
 */
public class ProofAddButton extends Button {

    private final Punishment punishment;

    public ProofAddButton(Punishment punishment){
        this.punishment = punishment;
    }

    @Override
    public String getName(Player player) {
        return Chat.format("&aAdd Proof Entry");
    }

    @Override
    public List<String> getDescription(Player player) {
        return null;
    }

    @Override
    public Material getMaterial(Player player) {
        return Material.SIGN;
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType) {
        startAddConversation(player);
    }

    private void startAddConversation(Player player){
        player.closeInventory();
        ConversationFactory factory = new ConversationFactory(Nitrogen.getInstance()).withModality(true).withPrefix(new NullConversationPrefix()).withFirstPrompt(new StringPrompt() {
            public String getPromptText(ConversationContext context) {
                return Chat.YELLOW + "Please type a proof entry to submit, or type " + Chat.LIGHT_RED + "cancel " + Chat.YELLOW + "to cancel.";
            }
            public Prompt acceptInput(ConversationContext context, String input) {
                if (input.equalsIgnoreCase("cancel")) {
                    context.getForWhom().sendRawMessage(Chat.LIGHT_RED + "Proof entry submission process aborted.");
                    return Prompt.END_OF_CONVERSATION;
                }
                punishment.save();
                if(punishment.getProof().contains(input)){
                    player.sendMessage(Chat.LIGHT_RED + "This punishment already has a proof entry similar to the one you input.");
                    return Prompt.END_OF_CONVERSATION;
                }
                Nitrogen.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Nitrogen.getInstance(), () -> {
                    List<String> proof = punishment.getProof();
                    proof.add(input);
                    punishment.setProof(proof);
                    punishment.save();
                    player.sendMessage(Chat.format("&aSuccessfully added proof entry: &r") + input);
                }, 1L);

                return Prompt.END_OF_CONVERSATION;
            }
        }).withEscapeSequence("/no").withLocalEcho(false).withTimeout(10).thatExcludesNonPlayersWithMessage("Go away evil console!");
        Conversation con = factory.buildConversation(player);
        player.beginConversation(con);
    }

}
