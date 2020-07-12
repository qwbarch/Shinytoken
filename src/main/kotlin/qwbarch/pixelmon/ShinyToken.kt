package qwbarch.pixelmon

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.event.FMLServerStartingEvent
import org.apache.logging.log4j.Logger
import qwbarch.pixelmon.shinytoken.TokenService
import qwbarch.pixelmon.shinytoken.command.BaseCommand
import qwbarch.pixelmon.shinytoken.storage.capability.CapabilityTokenService

@Mod(modid = ShinyToken.MOD_ID, name = ShinyToken.MOD_NAME, version = ShinyToken.VERSION,
        dependencies = "required-after:pixelmon", acceptableRemoteVersions = "*")
class ShinyToken {

    lateinit var logger: Logger private set
    lateinit var tokenService: TokenService private set

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        logger = event.modLog
        logger.info("Loading v$VERSION")
        tokenService = CapabilityTokenService()
    }

    @Mod.EventHandler
    fun serverStarting(event: FMLServerStartingEvent) {
        event.registerServerCommand(BaseCommand())
    }

    companion object {
        const val MOD_ID = "shinytoken"
        const val MOD_NAME = "ShinyToken"
        const val VERSION = "1.0"

        @Mod.Instance(MOD_ID)
        lateinit var INSTANCE: ShinyToken
            private set
    }
}