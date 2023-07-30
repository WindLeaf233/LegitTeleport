package owo.foliage.legitteleport.commands

import dev.rollczi.litecommands.argument.Arg
import dev.rollczi.litecommands.command.execute.Execute
import dev.rollczi.litecommands.command.permission.Permission
import dev.rollczi.litecommands.command.route.Route
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
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
            Component.text("${sender.name} removed the waypoint ", TextColor.color(194, 169, 15)).append(
                Component.text(WaypointsUtil.buildWaypointString(waypoint), NamedTextColor.DARK_AQUA)
            )
        )
    }

    @Execute(route = "list")
    fun listCommand(sender: CommandSender) {
        val waypoints = waypointManager.getWaypoints()
        if (waypoints.size != 0) {
            sender.sendMessage(Component.text("Available waypoints: "))
            for (worldName in Bukkit.getWorlds().map { it.name }) {
                if (worldName in waypoints.map { WaypointsUtil.dimToWorldName(it.dim) }) {
                    sender.sendMessage(if (sender is Player) Component.text("  $worldName > ${
                        waypoints.filter { WaypointsUtil.dimToWorldName(it.dim) == worldName }
                            .joinToString(", ") { it.name }
                    }", if (worldName == sender.world.name) NamedTextColor.GREEN else NamedTextColor.RED)
                    else Component.text("$worldName >> ${waypoints.joinToString(", ")}"))
                }
            }
        } else sender.sendMessage(Component.text("There is no available waypoints!", NamedTextColor.RED))
    }
}