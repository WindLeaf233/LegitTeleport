package owo.foliage.legitteleport.commands.arguments

import dev.rollczi.litecommands.argument.ArgumentName
import dev.rollczi.litecommands.argument.simple.MultilevelArgument
import dev.rollczi.litecommands.command.LiteInvocation
import dev.rollczi.litecommands.suggestion.Suggestion
import org.bukkit.Location
import org.bukkit.entity.Player
import panda.std.Result
import kotlin.math.roundToInt

@ArgumentName("x y z")
class LocationArgument : MultilevelArgument<Location> {
    override fun parseMultilevel(invocation: LiteInvocation, vararg arguments: String): Result<Location, *> =
        Result.supplyThrowing(NumberFormatException::class.java) {
            val x = arguments[0].toDouble()
            val y = arguments[1].toDouble()
            val z = arguments[2].toDouble()
            return@supplyThrowing Location(null, x, y, z)
        }

    override fun validate(invocation: LiteInvocation, suggestion: Suggestion): Boolean {
        for (suggest in suggestion.multilevelList()) {
            if (!suggest.matches(Regex("-?[\\d.]+"))) {
                return false
            }
        }
        return true
    }

    override fun suggest(invocation: LiteInvocation): MutableList<Suggestion> {
        val location = (invocation.sender().handle as Player).location
        return mutableListOf(Suggestion.multilevel(location.x.roundToInt().toString(), location.y.roundToInt().toString(), location.z.roundToInt().toString()))
    }

    override fun countMultilevel() = 3
}