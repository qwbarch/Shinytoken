package qwbarch.pixelmon.shinytoken.drop

import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent
import com.pixelmonmod.pixelmon.enums.EnumBossMode
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import qwbarch.pixelmon.ShinyToken
import qwbarch.pixelmon.shinytoken.MessageUtils

class DropSystem(private val dropLevel: DropLevel, private val dropRate: Int,
                 private val dropAmount: Int) {

    @SubscribeEvent
    fun onBeatWildPixelmon(event: BeatWildPixelmonEvent) {
        val pokemon = event.wpp.faintedPokemon.entity
        if (dropLevel == DropLevel.ANY) {
            dropToken(event.player, pokemon.pokemonName)
        } else {
            //If pokemon is not a boss, is shiny, and drop is set to shiny, drop it
            //Else if pokemon is a boss, and drop is set to boss, drop it
            if (pokemon.bossMode == EnumBossMode.NotBoss) {
                if (pokemon.pokemonData.isShiny && dropLevel == DropLevel.SHINY) {
                    dropToken(event.player, pokemon.pokemonName)
                }
            } else if (dropLevel == DropLevel.BOSS) {
                dropToken(event.player, pokemon.pokemonName)
            }
        }
    }

    private fun dropToken(player: EntityPlayerMP, pokemonName: String) {
        if ((0 until dropRate).random() == 0) {
            ShinyToken.INSTANCE.logger.info("${player.name} earned $dropAmount token" +
                    "${MessageUtils.tokens(dropAmount)} through drops")
            ShinyToken.INSTANCE.tokenService.addTokens(player, dropAmount)
            MessageUtils.sendMessage(player, "You earned ${TextFormatting.YELLOW}$dropAmount " +
                    "${TextFormatting.WHITE}${MessageUtils.tokens(dropAmount)} from defeating " +
                    "${TextFormatting.YELLOW}$pokemonName")
        }
    }
}