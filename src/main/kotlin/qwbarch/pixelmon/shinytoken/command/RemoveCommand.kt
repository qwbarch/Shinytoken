package qwbarch.pixelmon.shinytoken.command

import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.TextFormatting
import qwbarch.pixelmon.ShinyToken
import qwbarch.pixelmon.shinytoken.MessageUtils

class RemoveCommand : STCommand("remove", "/st remove <playerName> <amount>", 2) {

    private fun sendRemovedTokensMessage(sender: ICommandSender, tokens: Int, target: EntityPlayerMP) {
        val message = "Removed ${TextFormatting.YELLOW}$tokens ${TextFormatting.WHITE}" +
                "${MessageUtils.tokens(tokens)} from ${target.name}."
        if (sender !== target) MessageUtils.sendMessage(sender, message)
        MessageUtils.sendMessage(target, "You lost ${TextFormatting.YELLOW}$tokens " +
                "${TextFormatting.WHITE}shiny ${MessageUtils.tokens(tokens)}.")
        if (sender is EntityPlayerMP) ShinyToken.INSTANCE.logger.info(message)
    }

    override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
        if (args.size > 2 || args.isEmpty()) {
            sendUsage(sender)
        } else {
            try {
                when (args.size) {
                    1 -> {
                        if ((sender is EntityPlayerMP)) {
                            ShinyToken.INSTANCE.tokenService.removeTokens(sender, args[0].toInt())
                            sendRemovedTokensMessage(sender, args[0].toInt(), sender)
                        } else sendUsage(sender)
                    }
                    2 -> {
                        val player = server.playerList.getPlayerByUsername(args[0])
                        if (player == null) MessageUtils.sendPlayerNotAvailable(sender, args[0])
                        else {
                            ShinyToken.INSTANCE.tokenService.removeTokens(player, args[1].toInt())
                            sendRemovedTokensMessage(sender, args[1].toInt(), player)
                        }
                    }
                }
            } catch (exception: NumberFormatException) {
                sendUsage(sender)
            }
        }
    }
}