package qwbarch.pixelmon.shinytoken.storage.capability

import net.minecraft.nbt.NBTBase
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable

class TokenSerializer : ICapabilitySerializable<NBTBase> {

    private var instance = TOKEN_CAPABILITY.defaultInstance!!

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean =
            capability === TOKEN_CAPABILITY

    override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? =
            if (hasCapability(capability, facing)) TOKEN_CAPABILITY.cast<T>(instance) else null

    override fun deserializeNBT(nbt: NBTBase?) {
        TOKEN_CAPABILITY.storage.readNBT(TOKEN_CAPABILITY, instance, null, nbt)
    }

    override fun serializeNBT(): NBTBase =
            TOKEN_CAPABILITY.storage.writeNBT(TOKEN_CAPABILITY, instance, null)!!

    companion object {
        @CapabilityInject(TokenData::class)
        lateinit var TOKEN_CAPABILITY: Capability<TokenData>
            private set
    }
}