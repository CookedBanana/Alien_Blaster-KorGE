import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import korlibs.image.bitmap.*
import korlibs.logger.Console.trace
import kotlin.math.*
import korlibs.korge.view.*
import korlibs.korge.view.collision.*
import korlibs.time.*

class BasicAlien(val views: Views, image: Bitmap, val centerImg: Image) :
    CoroutineScope by CoroutineScope(Dispatchers.Default) {

    val spd = 100.0

    val basicAlienSprite = Sprite(image).apply { // Use the image passed to the constructor
        size(30.0, 30.0) // Set the size of the sprite
    }

    init {
        basicAlienSprite.addUpdater { dt ->
            val dx = (centerImg.x - basicAlienSprite.x + 25.0)
            val dy = (centerImg.y - basicAlienSprite.y + 25.0)
            val distance = sqrt(dx * dx + dy * dy)
            val unitDx = dx / distance
            val unitDy = dy / distance
            basicAlienSprite.xy(
                basicAlienSprite.x + unitDx * spd * dt.seconds,
                basicAlienSprite.y + unitDy * spd * dt.seconds
            )
            basicAlienSprite.onCollision({ it == centerImg }) {

                basicAlienSprite.removeFromParent()
                trace("Collision detected!")
            }
        }
    }
}
