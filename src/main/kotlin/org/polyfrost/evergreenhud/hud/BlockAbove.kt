package org.polyfrost.evergreenhud.hud

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Exclude
import cc.polyfrost.oneconfig.config.annotations.HUD
import cc.polyfrost.oneconfig.config.annotations.Slider
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.hud.SingleTextHud
import cc.polyfrost.oneconfig.libs.universal.USound
import cc.polyfrost.oneconfig.utils.dsl.mc
import net.minecraft.block.BlockBanner
import net.minecraft.block.BlockSign
import net.minecraft.block.BlockVine
import net.minecraft.init.Blocks
import net.minecraft.util.BlockPos

class BlockAbove: Config(Mod("Block Above", ModType.HUD, "/assets/evergreenhud/evergreenhud.svg"), "evergreenhud/blockabove.json", false) {
    @HUD(name = "Main")
    var hud = BlockAboveHud()

    init {
        initialize()
    }

    class BlockAboveHud: SingleTextHud("Above", true, 120, 50) {
        @Switch(
            name = "Notify With Sound"
        )
        var notify = false

        @Slider(
            name = "Notify Height",
            min = 1F,
            max = 10F,
            step = 1
        )
        var notifyHeight = 3

        @Slider(
            name = "Check Height",
            min = 1F,
            max = 30F,
            step = 1
        )
        var checkHeight = 10

        @Exclude
        private var notified = false

        override fun getText(example: Boolean): String {
            if (mc.theWorld == null || mc.thePlayer == null) return "0"

            var above = 0
            for (i in 1..checkHeight) {
                val pos = BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 1 + i, mc.thePlayer.posZ)
                if (pos.y > mc.theWorld!!.height) break

                val state = mc.theWorld!!.getBlockState(pos) ?: continue
                if (state.block == Blocks.air
                    || state.block == Blocks.water
                    || state.block is BlockSign
                    || state.block is BlockVine
                    || state.block is BlockBanner)
                    continue

                above = i

                if (above <= notifyHeight && notify) {
                    if (!notified) {
                        USound.playExpSound()
                        notified = true
                    }
                } else {
                    notified = false
                }

                break
            }

            return above.toString()
        }
    }
}