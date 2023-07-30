package owo.foliage.legitteleport.utils

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import owo.foliage.legitteleport.waypoint.Waypoint

object WaypointsUtil {
    private val regexPattern =
        Regex("\\[name:([^,\\[\\]]+),\\s*x:(-?\\d+),\\s*y:(-?\\d+),\\s*z:(-?\\d+),\\s*dim:([^,\\[\\]]+)(?:,\\s*icon:([^,\\[\\]]+))?\\]")

    fun checkWaypointMessage(message: String) = regexPattern.find(message) != null

    fun parseStringToWaypoint(string: String): Waypoint {
        val matchResult: MatchResult = regexPattern.find(string)!!
        val (name, x, y, z, dim, icon) = matchResult.destructured
        return Waypoint(name, x.toInt(), y.toInt(), z.toInt(), dim, if (icon == "") null else icon)
    }

    fun dimToWorldName(dimString: String): String {
        val worlds = Bukkit.getWorlds()
        val worldNormal = worlds.find { it.environment == World.Environment.NORMAL }
        val worldNether = worlds.find { it.environment == World.Environment.NETHER }
        val worldTheEnd = worlds.find { it.environment == World.Environment.THE_END }
        val mapping = hashMapOf(
            "minecraft:overworld" to worldNormal?.name,
            "minecraft:the_nether" to worldNether?.name,
            "minecraft:the_end" to worldTheEnd?.name
        )
        return mapping[dimString] ?: worldNormal?.name ?: "world"
    }

    fun getLocation(waypoint: Waypoint) = Location(
        Bukkit.getWorld(dimToWorldName(waypoint.dim)),
        waypoint.x.toDouble(),
        waypoint.y.toDouble(),
        waypoint.z.toDouble()
    )

    fun buildWaypointString(waypoint: Waypoint) = "[%s] (%.1f, %.1f, %.1f | %s)".format(
        waypoint.name, waypoint.x.toDouble(), waypoint.y.toDouble(), waypoint.z.toDouble(), dimToWorldName(waypoint.dim)
    )

    fun buildLocationString(location: Location) =
        "(%.1f, %.1f, %.1f | %s)".format(location.x, location.y, location.z, location.world.name)
}