package owo.foliage.legitteleport.commands

import dev.rollczi.litecommands.argument.Arg
import dev.rollczi.litecommands.command.execute.Execute
import dev.rollczi.litecommands.command.permission.Permission
import dev.rollczi.litecommands.command.route.Route
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import owo.foliage.legitteleport.LegitTeleport.Companion.mm
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
            mm.deserialize(
                "<#c2a90f>%s removed the waypoint</#c2a90f> <dark_aqua>%s</dark_aqua>".format(
                    sender.name, WaypointsUtil.buildWaypointString(waypoint)
                )
            )
        )
    }

    @Execute(route = "list")
    fun listCommand(sender: CommandSender) {
        val waypoints = waypointManager.getWaypoints()
        if (waypoints.size != 0) {
            sender.sendMessage(mm.deserialize("Available waypoints: "))
            for (worldName in Bukkit.getWorlds().map { it.name }) {
                if (worldName in waypoints.map { WaypointsUtil.dimToWorldName(it.dim) }) {
                    sender.sendMessage(
                        if (sender is Player) {
                            val color = if (worldName == sender.world.name) "green" else "red"
                            mm.deserialize(
                                "<%s> %s > %s</%s>".format(color,
                                    worldName,
                                    waypoints.filter { WaypointsUtil.dimToWorldName(it.dim) == worldName }
                                        .joinToString(", ") { it.name },
                                    color
                                )
                            )
                        } else mm.deserialize(
                            "%s > %s".format(
                                worldName,
                                waypoints.filter { WaypointsUtil.dimToWorldName(it.dim) == worldName }
                                    .joinToString(", ") { it.name })
                        )
                    )
                }
            }
        } else sender.sendMessage(mm.deserialize("<red>There is no available waypoints!</red>"))
    }
}