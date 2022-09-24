package qwbarch.pixelmon.shinytoken.storage.capability

import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import qwbarch.pixelmon.ShinyToken

@Mod.EventBusSubscriber
object TokenSubscriber {

    private val TOKEN_RESOURCE = ResourceLocation(ShinyToken.MOD_ID, "token")

    @SubscribeEvent
    @JvmStatic
    fun attachCapability(event: AttachCapabilitiesEvent<Entity>) {
        if (event.`object` is EntityPlayerMP) event.addCapability(TOKEN_RESOURCE, TokenSerializer())
    }

    @SubscribeEvent
    @JvmStatic
    fun onPlayerClone(event: PlayerEvent.Clone) {
        //If player gets killed or transfer dimensions, tokens is copied from
        //old player to new player object
        val new = event.entityPlayer.getCapability(TokenSerializer.TOKEN_CAPABILITY, null)!!
        val old = event.original.getCapability(TokenSerializer.TOKEN_CAPABILITY, null)!!
        new.value = old.value
        new.isLoginNotified = old.isLoginNotified
    }
}