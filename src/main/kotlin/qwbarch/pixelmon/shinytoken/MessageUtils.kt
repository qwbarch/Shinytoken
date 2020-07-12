package qwbarch.pixelmon.shinytoken

import net.minecraft.command.ICommandSender
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextFormatting
import qwbarch.pixelmon.ShinyToken

object MessageUtils {

    fun sendNonPlayerMessage(sender: ICommandSender) {
        sender.sendMessage(TextComponentString("This command is for players only."))
    }

    fun sendMessage(sender: ICommandSender, message: String) {
        sender.sendMessage(TextComponentString("[${TextFormatting.YELLOW}${ShinyToken.MOD_NAME}" +
                "${TextFormatting.WHITE}] $message"))
    }

    fun sendPlayerNotAvailable(sender: ICommandSender, player: String) {
        sendMessage(sender, "The player $player is not available.")
    }

    fun tokens(amount: Int): String = "token${if (amount == 1) "" else "s"}"
}