package owo.foliage.legitteleport.commands

import dev.rollczi.litecommands.argument.Arg
import dev.rollczi.litecommands.command.execute.Execute
import dev.rollczi.litecommands.command.permission.Permission
import dev.rollczi.litecommands.command.route.Route
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import owo.foliage.legitteleport.LegitTeleport.Companion.mm
import owo.foliage.legitteleport.LegitTeleport.Companion.waypointManager
import owo.foliage.legitteleport.utils.WaypointsUtil
import owo.foliage.legitteleport.waypoint.Waypoint
import java.util.*

@Route(name = "teleport", aliases = ["tp"])
@Permission("legitteleport.teleport")
class TeleportCommand {
    @Execute(required = 1)
    fun teleportPlayer(sender: Player, @Arg to: Player) {
        sender.teleport(to)
        Bukkit.broadcast(mm.deserialize("<dark_green>%s teleported to %s</dark_green>".format(sender.name, to.name)))
    }

    @Execute(required = 3)
    fun teleportLocation(sender: Player, @Arg location: Location) {
        location.world = sender.location.world
        val waypoints = waypointManager.getWaypoints()
        val waypoint = waypoints.find {
            it.x.toDouble() == location.x && it.y.toDouble() == location.y && it.z.toDouble() == location.z && WaypointsUtil.dimToWorldName(
                it.dim
            ) == location.world.name
        }
        if (waypoint != null) {
            sender.teleport(location)
            Bukkit.broadcast(
                mm.deserialize(
                    "<dark_green>%s teleported to the waypoint</dark_green> <dark_aqua>%s</dark_aqua> ".format(
                        sender.name, WaypointsUtil.buildWaypointString(waypoint)
                    )
                )
            )
        } else sender.sendMessage(
            mm.deserialize(
                "<red>The location %s is not a valid waypoint!</red>".format(
                    WaypointsUtil.buildLocationString(
                        location
                    )
                )
            )
        )
    }

    @Execute(required = 4) // be compatible with VoxelMap
    fun teleportSelfToLocation(sender: Player, @Arg target: Player, @Arg location: Location) {
        if (sender == target) teleportLocation(sender, location)
    }

    @Execute(required = 1)
    fun teleportWaypoint(sender: Player, @Arg waypoint: Waypoint) {
        val world = Bukkit.getWorld(WaypointsUtil.dimToWorldName(waypoint.dim)) ?: sender.world
        if (sender.world == world) {
            sender.teleport(WaypointsUtil.getLocation(waypoint))
            Bukkit.broadcast(
                mm.deserialize(
                    "<dark_green>%s teleported to the waypoint</dark_green> <dark_aqua>%s</dark_aqua>".format(
                        sender.name, WaypointsUtil.buildWaypointString(waypoint)
                    )
                )
            )
        } else sender.sendMessage(mm.deserialize("<red>Can not travel between different worlds!</red>"))
    }
}