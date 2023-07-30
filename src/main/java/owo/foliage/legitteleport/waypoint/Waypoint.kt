package owo.foliage.legitteleport.waypoint

data class Waypoint(
    var name: String, val x: Int, val y: Int, val z: Int, val dim: String, val icon: String?
) {
    override fun toString() = "[name:%s,x:%d,y:%d,z:%d,dim:%s%s]".format(
        name,
        x,
        y,
        z,
        dim,
        if (icon != null) ",icon:%s".format(icon) else ""
    )
}
