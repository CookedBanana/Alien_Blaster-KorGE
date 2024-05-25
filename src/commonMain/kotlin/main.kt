import kotlin.random.*
import kotlin.time.Duration.Companion.seconds
import kotlin.math.sqrt
import korlibs.image.color.*
import korlibs.korge.*
import korlibs.korge.view.*
import korlibs.korge.time.*
import korlibs.io.file.std.*
import korlibs.korge.view.collision.*
import korlibs.math.random.*
import korlibs.logger.Console.trace
import korlibs.image.format.*
import korlibs.math.geom.*
import korlibs.math.geom.slice.*
import korlibs.time.*

import AlienBlaster.*

suspend fun main() = Korge(title = "Alien Blaster",
    windowSize = Size(1000, 1440), virtualSize = Size(640, 480),
    backgroundColor = Colors.BLACK) {

    val mainContainer = Container().addTo(this)
    val speed = 100.0  // Set your desired speed here

    val backgroundImage = resourcesVfs["space_bcg.png"].readBitmap()

    val backgroundSprite = Sprite(backgroundImage).apply {
        xy(0, 0)
        width = views.virtualWidth.toDouble()
        height = views.virtualHeight.toDouble()
    }
    mainContainer.addChild(backgroundSprite)

    val circle3 = circle(80.0, Colors["#1157ff"]).xy((width / 2) - 80, (height / 2) - 80)
    val circle2 = circle(70.0, Colors["#5fc3ff"]).xy((width / 2) - 70, (height / 2) - 70)
    val circle1 = circle(60.0, Colors["#1157ff"]).xy((width / 2) - 60, (height / 2) - 60)
    val center = circle(50.0, Colors.BLACK).xy((width / 2) - 50, (height / 2) - 50 )

    val image = resourcesVfs["alien_icon.png"].readBitmapSlice().splitInRows(64, 64)

    interval(1.seconds) {
        val edge = Random.nextInt(4)
        val color = when(edge) {
            0 -> Pink
            1 -> Blue
            2 -> Green
            else -> Red
        }
        val circle12 = circle(40.0, color)
        when (edge) {
            0 -> {
                circle12.xy(Random.nextInt(640), 0).addTo(mainContainer)
                trace("Summon up")
            }
            1 -> {
                circle12.xy(Random.nextInt(640), 480).addTo(mainContainer)
                trace("Summon down")
            }
            2 -> {
                circle12.xy(0, Random.nextInt(480)).addTo(mainContainer)
                trace("Summon left")
            }
            else -> {
                circle12.xy(640, Random.nextInt(480)).addTo(mainContainer)
                trace("Summon right")
            }
        }

        circle12.addUpdater { dt ->
            val dx = (center.x - x)
            val dy = (center.y - y)
            val distance = sqrt(dx * dx + dy * dy)
            if (distance < speed * dt.seconds) {
                removeFromParent()  // Remove the circle when it reaches the center
            } else {
                val unitDx = dx / distance
                val unitDy = dy / distance
                xy(x + unitDx * speed * dt.seconds, y + unitDy * speed * dt.seconds)
            }
        }
    }

    center.onCollision({it == image}){
        trace("Collision detected!")
    }
}
