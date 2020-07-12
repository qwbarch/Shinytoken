package qwbarch.pixelmon.shinytoken.command

import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.TextFormatting
import qwbarch.pixelmon.ShinyToken
import qwbarch.pixelmon.shinytoken.MessageUtils

class CheckCommand : STCommand("check", "/st check") {

    override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
        if (sender is EntityPlayerMP) {
            if (args.isNotEmpty()) sendUsage(sender)
            else {
                val tokens = ShinyToken.INSTANCE.tokenService.getTokens(sender)
                MessageUtils.sendMessage(sender, "You currently have ${TextFormatting.YELLOW}$tokens " +
                        "${TextFormatting.WHITE}${MessageUtils.tokens(tokens)}.")
            }
        } else MessageUtils.sendNonPlayerMessage(sender)
    }
}