package owo.foliage.legitteleport.commands

import dev.rollczi.litecommands.argument.Arg
import dev.rollczi.litecommands.command.execute.Execute
import dev.rollczi.litecommands.command.permission.Permission
import dev.rollczi.litecommands.command.route.Route
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import owo.foliage.legitteleport.LegitTeleport.Companion.waypointManager
import owo.foliage.legitteleport.utils.WaypointsUtil
import owo.foliage.legitteleport.waypoint.Waypoint

@Route(name = "waypoint", aliases = ["wp"])
@Permission("legitteleport.waypoint")
class WaypointCommand {
    @Execute(route = "remove")
    fun removeCommand(sender: CommandSender, @Arg waypoint: Waypoint) {
        waypointManager.removeWaypoint(waypoint)
        Bukkit.broadcast(
            Component.text("${sender.name} removed the waypoint ", NamedTextColor.YELLOW).append(
                Component.text(
                    "[${waypoint.name}] (${waypoint.x.toDouble()}, ${waypoint.y.toDouble()}, ${waypoint.z.toDouble()} :: ${
                        WaypointsUtil.dimToWorldName(waypoint.dim)
                    })", NamedTextColor.BLUE
                )
            )
        )
    }

    @Execute(route = "list")
    fun listCommand(sender: CommandSender) {
        val waypoints = waypointManager.getWaypoints()
        if (waypoints.size != 0) {
            if (sender is Player) {
                sender.sendMessage(
                    Component.text(
                        "Available waypoints: ${
                            waypoints.joinToString(", ") {
                                "§${if (Bukkit.getWorld(WaypointsUtil.dimToWorldName(it.dim)) == sender.world) "a" else "c"}${it.name}(${
                                    WaypointsUtil.dimToWorldName(
                                        it.dim
                                    )
                                })§r"
                            }
                        }"
                    )
                )
            } else {
                sender.sendMessage(Component.text("Available waypoints: ${waypoints.joinToString(", ")}"))
            }
        } else sender.sendMessage(Component.text("There is no available waypoints!", NamedTextColor.RED))
    }
}