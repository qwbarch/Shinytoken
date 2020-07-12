package qwbarch.pixelmon.shinytoken.storage.capability

import net.minecraft.entity.player.EntityPlayerMP
import net.minecraftforge.common.capabilities.CapabilityManager
import qwbarch.pixelmon.shinytoken.TokenService

//Must be created during preInit stage
class CapabilityTokenService : TokenService {

    init {
        CapabilityManager.INSTANCE.register(TokenData::class.java, TokenStorage()) { TokenData() }
    }

    override fun getTokens(player: EntityPlayerMP): Int =
            player.getCapability(TokenSerializer.TOKEN_CAPABILITY, null)!!.value

    override fun addTokens(player: EntityPlayerMP, amount: Int) {
        player.getCapability(TokenSerializer.TOKEN_CAPABILITY, null)!!.value += amount
    }

    override fun removeTokens(player: EntityPlayerMP, amount: Int) {
        player.getCapability(TokenSerializer.TOKEN_CAPABILITY, null)!!.value -= amount
        if (getTokens(player) < 0) resetTokens(player)
    }

    override fun resetTokens(player: EntityPlayerMP) {
        player.getCapability(TokenSerializer.TOKEN_CAPABILITY, null)!!.value = 0
    }

    override fun setLoginNotified(player: EntityPlayerMP, notified: Boolean) {
        player.getCapability(TokenSerializer.TOKEN_CAPABILITY, null)!!.isLoginNotified = notified
    }

    override fun isLoginNotified(player: EntityPlayerMP): Boolean =
            player.getCapability(TokenSerializer.TOKEN_CAPABILITY, null)!!.isLoginNotified
}