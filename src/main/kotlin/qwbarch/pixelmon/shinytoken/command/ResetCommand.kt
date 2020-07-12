package qwbarch.pixelmon.shinytoken.command

import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.TextFormatting
import qwbarch.pixelmon.ShinyToken
import qwbarch.pixelmon.shinytoken.MessageUtils

class ResetCommand : STCommand("reset", "/st reset <playerName>") {

    private fun sendResetTokensMessage(sender: ICommandSender, target: EntityPlayerMP) {
        val message = "${TextFormatting.YELLOW}${target.name}${TextFormatting.WHITE}'s tokens have been reset."
        if (sender !== target) MessageUtils.sendMessage(sender, message)
        MessageUtils.sendMessage(target, "Your tokens have been reset.")
        if (sender is EntityPlayerMP) ShinyToken.INSTANCE.logger.info(message)
    }

    override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
        if (args.size > 1) {
            sendUsage(sender)
        } else {
            try {
                when (args.size) {
                    0 -> {
                        if (sender is EntityPlayerMP) {
                            ShinyToken.INSTANCE.tokenService.resetTokens(sender)
                            sendResetTokensMessage(sender, sender)
                        } else sendUsage(sender)
                    }
                    1 -> {
                        val player = server.playerList.getPlayerByUsername(args[0])
                        if (player == null) MessageUtils.sendPlayerNotAvailable(sender, args[0])
                        else {
                            ShinyToken.INSTANCE.tokenService.resetTokens(player)
                            sendResetTokensMessage(sender, player)
                        }
                    }
                }
            } catch (exception: NumberFormatException) {
                sendUsage(sender)
            }
        }
    }
}