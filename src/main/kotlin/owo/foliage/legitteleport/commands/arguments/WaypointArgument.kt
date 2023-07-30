package owo.foliage.legitteleport.commands.arguments

import dev.rollczi.litecommands.argument.ArgumentName
import dev.rollczi.litecommands.argument.simple.OneArgument
import dev.rollczi.litecommands.command.LiteInvocation
import dev.rollczi.litecommands.suggestion.Suggestion
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import owo.foliage.legitteleport.LegitTeleport.Companion.waypointManager
import owo.foliage.legitteleport.utils.WaypointsUtil
import owo.foliage.legitteleport.waypoint.Waypoint
import panda.std.Option
import panda.std.Result
import java.util.stream.Collectors

@ArgumentName("waypointName")
class WaypointArgument : OneArgument<Waypoint> {
    override fun parse(invocation: LiteInvocation, argument: String): Result<Waypoint, *> =
        Option.of(waypointManager.getWaypoints().find {
            it.name == argument && (invocation.sender().handle as Player).world == Bukkit.getWorld(
                WaypointsUtil.dimToWorldName(
                    it.dim
                )
            )
        }).toResult("&cWaypoint not found!")

    override fun suggest(invocation: LiteInvocation): MutableList<Suggestion> =
        waypointManager.getWaypoints().stream().filter {
            (invocation.sender().handle as Player).world == Bukkit.getWorld(WaypointsUtil.dimToWorldName(it.dim))
        }.map { it.name }.map(Suggestion::of).collect(Collectors.toList())
}