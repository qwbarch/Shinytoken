package qwbarch.pixelmon.shinytoken.command

import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer
import qwbarch.pixelmon.ShinyToken
import qwbarch.pixelmon.shinytoken.MessageUtils

class LoginCommand : STCommand("login", "/st login show|hide") {

    override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
        if (sender is EntityPlayerMP) {
            if (args.size == 1) {
                when (args[0]) {
                    "show" -> {
                        ShinyToken.INSTANCE.tokenService.setLoginNotified(sender, true)
                        MessageUtils.sendMessage(sender, "Login notifications enabled.")
                    }
                    "hide" -> {
                        ShinyToken.INSTANCE.tokenService.setLoginNotified(sender, false)
                        MessageUtils.sendMessage(sender, "Login notifications disabled.")
                    }
                    else -> sendUsage(sender)
                }
            } else sendUsage(sender)
        } else MessageUtils.sendNonPlayerMessage(sender)
    }
}