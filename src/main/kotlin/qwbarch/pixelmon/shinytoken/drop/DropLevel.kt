package qwbarch.pixelmon.shinytoken.drop

enum class DropLevel {

    ANY, BOSS, SHINY;

    companion object {
        fun parse(ordinal: Int) = values()[ordinal]
    }
}