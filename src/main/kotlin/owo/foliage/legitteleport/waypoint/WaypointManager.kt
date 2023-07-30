package owo.foliage.legitteleport.waypoint


import com.alibaba.fastjson2.JSON
import owo.foliage.legitteleport.utils.FileUtil
import owo.foliage.legitteleport.utils.WaypointsUtil

class WaypointManager {
    private val waypoints = arrayListOf<Waypoint>()
    private val path = FileUtil.getResourcePath("waypoints.json")

    init {
        refreshWaypoints()
    }

    private fun refreshWaypoints() {
        waypoints.clear()
        val json = FileUtil.read(path, "[]")
        val list: ArrayList<String> = JSON.parseObject(json, ArrayList::class.java) as ArrayList<String>
        list.forEach {
            val instance = WaypointsUtil.parseStringToWaypoint(it)
            waypoints.add(instance)
        }
    }

    private fun refreshFile() {
        val string = JSON.toJSONString(waypoints.map { it.toString() })
        FileUtil.save(string, path)
    }

    fun addWaypoint(waypoint: Waypoint) {
        waypoint.name = waypoint.name.replace(" ", "_")
        val wp = waypoints.find { it.name == waypoint.name }
        if (wp != null) waypoints.remove(waypoint)
        waypoints.add(waypoint)
        refreshFile()
    }

    fun getWaypoints() = waypoints

    fun removeWaypoint(waypoint: Waypoint) {
        waypoints.remove(waypoint)
        refreshFile()
    }

    fun matchWaypoint(waypoint: Waypoint) =
        waypoints.find { it.x == waypoint.x && it.y == waypoint.y && it.z == waypoint.z && it.dim == waypoint.dim }
}