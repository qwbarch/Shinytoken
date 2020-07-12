package qwbarch.pixelmon.shinytoken

import net.minecraft.entity.player.EntityPlayerMP

interface TokenService {

    fun getTokens(player: EntityPlayerMP): Int
    fun addTokens(player: EntityPlayerMP, amount: Int)
    fun removeTokens(player: EntityPlayerMP, amount: Int)
    fun resetTokens(player: EntityPlayerMP)

    fun setLoginNotified(player: EntityPlayerMP, notified: Boolean)
    fun isLoginNotified(player: EntityPlayerMP): Boolean
}