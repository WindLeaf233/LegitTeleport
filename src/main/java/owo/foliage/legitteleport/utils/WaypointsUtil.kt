package owo.foliage.legitteleport.utils

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
        val mapping = hashMapOf(
            "minecraft:overworld" to "world",
            "minecraft:the_nether" to "world_nether",
            "minecraft:the_end" to "world_end"
        )
        return mapping[dimString] ?: "world"
    }
}