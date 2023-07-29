package owo.foliage.legitteleport.waypoint

data class Waypoint(
    var name: String, val x: Int, val y: Int, val z: Int, val dim: String, val icon: String?
) {
    override fun toString() = "[name:$name,x:$x,y:$y,z:$z,dim:$dim${if (icon != null) ",icon:$icon" else ""}]"
}
