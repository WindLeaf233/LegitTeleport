package owo.foliage.legitteleport.listeners

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import owo.foliage.legitteleport.LegitTeleport.Companion.waypointManager
import owo.foliage.legitteleport.utils.WaypointsUtil

class PlayerSharePointListener : Listener {
    @EventHandler
    fun onAsyncChat(e: AsyncChatEvent) {
        val message = (e.message() as TextComponent).content().trim()
        if (WaypointsUtil.checkWaypointMessage(message)) {
            e.isCancelled = true
            Bukkit.broadcast(Component.text("${e.player.name} shared a waypoint: $message"))
            val waypoint = WaypointsUtil.parseStringToWaypoint(message)
            waypointManager.addWaypoint(waypoint)
        }
    }
}
