package qwbarch.pixelmon.shinytoken.command

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.server.command.CommandTreeBase
import qwbarch.pixelmon.ShinyToken

abstract class STCommand(private val name: String,
                         private val usage: String,
                         private val permLevel: Int = 1) : CommandBase() {

    fun sendUsage(sender: ICommandSender) {
        sender.sendMessage(TextComponentString("Correct usage: ${getUsage(sender)}"))
    }

    override fun getName(): String = name

    override fun getUsage(sender: ICommandSender): String = usage

    override fun checkPermission(server: MinecraftServer, sender: ICommandSender): Boolean {
        return sender.canUseCommand(permLevel,
                "${ShinyToken.MOD_ID}." + (if (permLevel == 1) "command" else "admin") + ".$name")
    }
}

class BaseCommand : CommandTreeBase() {

    private val aliases = arrayOf("st").toList()

    init {
        addSubcommand(CheckCommand())
        addSubcommand(ConvertCommand())
        addSubcommand(AddCommand())
        addSubcommand(RemoveCommand())
        addSubcommand(ResetCommand())
        addSubcommand(LoginCommand())
    }

    override fun getName(): String = ShinyToken.MOD_ID

    override fun getUsage(sender: ICommandSender): String = "/${ShinyToken.MOD_ID}"

    override fun getAliases(): List<String> = aliases

    override fun checkPermission(server: MinecraftServer, sender: ICommandSender): Boolean {
        return sender.canUseCommand(1, "${ShinyToken.MOD_ID}.command.base")
    }
}