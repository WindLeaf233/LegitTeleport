package owo.foliage.legitteleport

import dev.rollczi.litecommands.LiteCommands
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual
import dev.rollczi.litecommands.bukkit.tools.BukkitPlayerArgument
import net.kyori.adventure.text.logger.slf4j.ComponentLogger
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import owo.foliage.legitteleport.commands.TeleportCommand
import owo.foliage.legitteleport.commands.WaypointCommand
import owo.foliage.legitteleport.commands.arguments.LocationArgument
import owo.foliage.legitteleport.commands.arguments.WaypointArgument
import owo.foliage.legitteleport.handlers.InvalidUsageHandler
import owo.foliage.legitteleport.listeners.PlayerSharePointListener
import owo.foliage.legitteleport.utils.FileUtil
import owo.foliage.legitteleport.waypoint.Waypoint
import owo.foliage.legitteleport.waypoint.WaypointManager


class LegitTeleport : JavaPlugin() {
    companion object {
        lateinit var instance: LegitTeleport
        lateinit var waypointManager: WaypointManager
        lateinit var logger: ComponentLogger
    }

    private val liteCommands: LiteCommands<CommandSender> =
        LiteBukkitFactory.builder(server, "LegitTeleport").argumentMultilevel(Location::class.java, LocationArgument())
            .argumentMultilevel(Waypoint::class.java, WaypointArgument())
            .argument(Player::class.java, BukkitPlayerArgument(server, "&cPlayer not found!"))
            .contextualBind(Player::class.java, BukkitOnlyPlayerContextual("&cThis command is only for players!"))
            .command(TeleportCommand::class.java).command(WaypointCommand::class.java)
            .invalidUsageHandler(InvalidUsageHandler()).register()

    override fun onEnable() {
        instance = this
        Companion.logger = componentLogger
        FileUtil.saveResource("waypoints.json")
        reloadConfig()
        waypointManager = WaypointManager()
        server.pluginManager.registerEvents(PlayerSharePointListener(), this)
    }

    override fun onDisable() {
        liteCommands.platform.unregisterAll()
    }
}