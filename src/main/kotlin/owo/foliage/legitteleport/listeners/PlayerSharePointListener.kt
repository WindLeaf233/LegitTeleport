package owo.foliage.legitteleport.listeners

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import owo.foliage.legitteleport.LegitTeleport.Companion.mm
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
                Bukkit.broadcast(mm.deserialize("%s shared a waypoint: %s".format(e.player.name, message)))
                waypointManager.addWaypoint(waypoint)
            } else {
                e.player.sendMessage(
                    mm.deserialize(
                        "<red>There is already an existent waypoint [%s] pointing to location %s!</red>".format(
                            matched.name, WaypointsUtil.buildLocationString(WaypointsUtil.getLocation(matched))
                        )
                    )
                )
            }
        }
    }
}
