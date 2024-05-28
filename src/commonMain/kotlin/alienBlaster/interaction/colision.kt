package alienBlaster.interaction

import korlibs.math.geom.*

fun Point.isInRing(outerRadius: Double, innerRadius: Double, center: Point): Boolean {
    val dx = this.x - center.x
    val dy = this.y - center.y
    val distanceSquared = dx * dx + dy * dy
    return distanceSquared <= outerRadius * outerRadius && distanceSquared >= innerRadius * innerRadius
}
