package qwbarch.pixelmon.shinytoken.command

import com.pixelmonmod.pixelmon.Pixelmon
import com.pixelmonmod.pixelmon.api.storage.StoragePosition
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.ServerChatEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import qwbarch.pixelmon.ShinyToken
import qwbarch.pixelmon.shinytoken.MessageUtils

class ConvertCommand : STCommand("convert", "/st convert [partySlot]") {

    override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
        if (sender is EntityPlayerMP) {
            if (args.isEmpty() || args.size > 1) sendUsage(sender)
            else {
                try {
                    //Check if party slot out of bounds
                    val position = args[0].toInt() - 1
                    if (position < 0 || position > 5) {
                        sendUsage(sender)
                    } else {
                        val pokemon = Pixelmon.storageManager.getPokemon(sender,
                                StoragePosition(-1, position))
                        if (pokemon == null) MessageUtils.sendMessage(sender, "The selected party slot is empty.")
                        else {
                            //If player has a token, convert pokemon after confirmation
                            if (ShinyToken.INSTANCE.tokenService.getTokens(sender) > 0) {
                                MessageUtils.sendMessage(sender, "Are you sure you want to convert " +
                                        "${TextFormatting.YELLOW}${pokemon.displayName}" +
                                        "${TextFormatting.WHITE}?\nType ${TextFormatting.YELLOW}confirm " +
                                        "${TextFormatting.WHITE}to continue.")
                                val acceptListener = object {
                                    @SubscribeEvent
                                    fun onServerChat(event: ServerChatEvent) {
                                        if (event.player === sender) {
                                            MinecraftForge.EVENT_BUS.unregister(this)
                                            event.isCanceled = true
                                            if (event.message == "confirm") {
                                                pokemon.isShiny = !pokemon.isShiny
                                                ShinyToken.INSTANCE.tokenService.removeTokens(sender, 1)
                                                MessageUtils.sendMessage(sender,
                                                        "Your ${TextFormatting.YELLOW}${pokemon.displayName} " +
                                                                "${TextFormatting.WHITE} is now a " +
                                                                "${if (pokemon.isShiny) "" else "non-"}shiny!")
                                            } else MessageUtils.sendMessage(sender, "Pokemon conversion has been cancelled.")
                                        }
                                    }
                                }
                                MinecraftForge.EVENT_BUS.register(acceptListener)
                            } else MessageUtils.sendMessage(sender, "You need at least 1 token to use this command.")
                        }
                    }
                } catch (exception: NumberFormatException) {
                    sendUsage(sender)
                }
            }
        } else MessageUtils.sendNonPlayerMessage(sender)
    }
}