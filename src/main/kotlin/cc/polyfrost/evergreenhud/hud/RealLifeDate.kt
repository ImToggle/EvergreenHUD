package cc.polyfrost.evergreenhud.hud

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.HUD
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.hud.SingleTextHud
import java.text.SimpleDateFormat
import java.util.*

class RealLifeDate : Config(Mod("IRL Date", ModType.HUD, "/assets/evergreenhud/evergreenhud.svg"), "evergreenhud/irldate.json", false) {
    @HUD(name = "Main")
    var hud = RealLifeDateHud()

    init {
        initialize()
    }

    class RealLifeDateHud : SingleTextHud("Date", true, 120, 20) {

        @Switch(name = "Full Length Date")
        var fullLengthDate = false

        @Switch(name = "Month Before Date")
        var monthBeforeDate = false

        @Switch(name = "Show Day of Week")
        var dayOfWeek = false

        @Switch(name = "Show Year")
        var showYear = false

        override fun getText(example: Boolean): String = SimpleDateFormat(String.format(if (dayOfWeek && fullLengthDate) "EEEE, " else "", if (dayOfWeek && !fullLengthDate) "EEE, " else "", if (monthBeforeDate && fullLengthDate) "MMMM" else "", if (monthBeforeDate && !fullLengthDate) "MMM" else "", "dd", if (!monthBeforeDate && fullLengthDate) "MMMM" else "", if (!monthBeforeDate && !fullLengthDate) "MMM" else "", if (showYear) "YYYY" else ""))
            .format(Calendar.getInstance().time).uppercase()
    }
}