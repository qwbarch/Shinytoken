package qwbarch.pixelmon.shinytoken;

import net.minecraftforge.common.config.Config;
import qwbarch.pixelmon.ShinyToken;

@Config(modid = ShinyToken.MOD_ID)
public final class Configuration {

    @Config.Name("Drops Enabled")
    public static boolean isDropsEnabled = true;

    @Config.Comment("0 = ANY, 1 = BOSS, 2 = SHINY")
    @Config.Name("Drop Level")
    @Config.RangeInt(min = 0, max = 2)
    public static int dropLevel = 2;

    @Config.Comment("1/X chance drop rate, where X is Drop Rate. Default is 5% droprate from shiny pokemon.\n" +
            "Increase this value if you want to make it drop less.")
    @Config.Name("Drop Rate")
    @Config.RangeInt(min = 1)
    public static int dropRate = 20;

    @Config.Comment("Amount of shiny tokens per drop")
    @Config.Name("Drop Amount")
    @Config.RangeInt(min = 1)
    public static int dropAmount = 1;
}