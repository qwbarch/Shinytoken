package qwbarch.pixelmon.shinytoken.storage.capability

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability

class TokenStorage : Capability.IStorage<TokenData> {

    override fun readNBT(capability: Capability<TokenData>?, instance: TokenData?, side: EnumFacing?, nbt: NBTBase?) {
        instance!!.value = (nbt as NBTTagCompound).getInteger("token")
        instance.isLoginNotified = nbt.getBoolean("login")
    }

    override fun writeNBT(capability: Capability<TokenData>?, instance: TokenData?, side: EnumFacing?): NBTBase? {
        val nbt = NBTTagCompound()
        nbt.setInteger("token", instance!!.value)
        nbt.setBoolean("login", instance.isLoginNotified)
        return nbt
    }
}