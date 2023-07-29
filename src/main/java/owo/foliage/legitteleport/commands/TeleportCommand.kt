package owo.foliage.legitteleport.commands

import dev.rollczi.litecommands.argument.Arg
import dev.rollczi.litecommands.command.execute.Execute
import dev.rollczi.litecommands.command.permission.Permission
import dev.rollczi.litecommands.command.route.Route
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
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
        Bukkit.broadcast(Component.text("${sender.name} teleported to ${to.name}"))
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
                Component.text("${sender.name} teleported to waypoint ", NamedTextColor.GREEN).append(
                    Component.text(
                        "[${waypoint.name}] (${location.x}, ${location.y}, ${location.z} :: ${location.world.name}",
                        NamedTextColor.BLUE
                    )
                )
            )
        } else {
            sender.sendMessage(
                Component.text(
                    "The location (${location.x}, ${location.y}, ${location.z} :: ${location.world.name}) is not a valid waypoint!",
                    NamedTextColor.RED
                )
            )
        }
    }

    @Execute(required = 4) // compatible with VoxelMap
    fun teleportSelfToLocation(sender: Player, @Arg target: Player, @Arg location: Location) {
        if (sender == target) teleportLocation(sender, location)
    }

    @Execute(required = 1)
    fun teleportWaypoint(sender: Player, @Arg waypoint: Waypoint) {
        val world = Bukkit.getWorld(WaypointsUtil.dimToWorldName(waypoint.dim)) ?: sender.world
        if (sender.world == world) {
            sender.teleport(Location(world, waypoint.x.toDouble(), waypoint.y.toDouble(), waypoint.z.toDouble()))
            Bukkit.broadcast(
                Component.text("${sender.name} teleported to waypoint ", NamedTextColor.GREEN).append(
                    Component.text(
                        "[${waypoint.name}] (${waypoint.x.toDouble()}, ${waypoint.y.toDouble()}, ${waypoint.z.toDouble()} :: ${world.name}",
                        NamedTextColor.BLUE
                    )
                )
            )
        } else sender.sendMessage(Component.text("Can not travel between different worlds!", NamedTextColor.RED))
    }
}