package owo.foliage.legitteleport.listeners

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import owo.foliage.legitteleport.LegitTeleport.Companion.waypointManager
import owo.foliage.legitteleport.utils.WaypointsUtil
import owo.foliage.legitteleport.waypoint.Waypoint

class PlayerSharePointListener : Listener {
    @EventHandler
    fun onAsyncChat(e: AsyncChatEvent) {
        val message = (e.message() as TextComponent).content().trim()
        if (WaypointsUtil.checkWaypointMessage(message)) {
            e.isCancelled = true
            val waypoint = WaypointsUtil.parseStringToWaypoint(message)
            val matched: Waypoint? = waypointManager.matchWaypoint(waypoint)
            if (matched == null || waypoint.name == matched.name) {
                Bukkit.broadcast(Component.text("${e.player.name} shared a waypoint: $message"))
                waypointManager.addWaypoint(waypoint)
            } else {
                e.player.sendMessage(
                    Component.text(
                        "There is already a waypoint [${matched.name}] pointing to location ${
                            WaypointsUtil.buildLocationString(WaypointsUtil.getLocation(matched))
                        }!", NamedTextColor.RED
                    )
                )
            }
        }
    }
}
