package org.polyfrost.evergreenhud.hud

import org.polyfrost.evergreenhud.utils.Facing
import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.HUD
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.hud.SingleTextHud
import cc.polyfrost.oneconfig.utils.dsl.mc

class Direction: Config(Mod("Direction", ModType.HUD), "evergreenhud/direction.json", false) {
    @HUD(name = "Main")
    var hud = DirectionHud()

    init {
        initialize()
    }

    class DirectionHud: SingleTextHud("Direction", true, 260, 10) {

        @Switch(
            name = "Abbreviated"
        )
        var abbreviated = false

        override fun getText(example: Boolean): String {
            if (mc.thePlayer == null) return "Unknown"

            val facing = Facing.parse(mc.thePlayer!!.rotationYaw)
            return if (abbreviated) facing.abbreviated else facing.full
        }

    }
}