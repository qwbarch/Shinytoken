package qwbarch.pixelmon.shinytoken

import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent
import qwbarch.pixelmon.ShinyToken

@Mod.EventBusSubscriber
object LoginNotifier {

    @SubscribeEvent
    @JvmStatic
    fun onLogin(event: PlayerEvent.PlayerLoggedInEvent) {
        val player = event.player
        if (player is EntityPlayerMP && ShinyToken.INSTANCE.tokenService.isLoginNotified(player)) {
            val tokens = ShinyToken.INSTANCE.tokenService.getTokens(player)
            MessageUtils.sendMessage(player, "You currently have ${TextFormatting.YELLOW}" +
                    "$tokens ${TextFormatting.WHITE}shiny ${MessageUtils.tokens(tokens)}.\n" +
                    "For a list of sub-commands, type /${ShinyToken.MOD_ID}")
        }
    }
}