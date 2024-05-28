package alienBlaster.effects

import korlibs.audio.sound.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.view.Views
import korlibs.korge.view.*
import korlibs.math.geom.slice.*
import kotlin.time.Duration.Companion.milliseconds

suspend fun playKaboom(views: Views, x: Double, y: Double) {

    val animationexp = SpriteAnimation(
        resourcesVfs["gfx/exp2.jpg"].readBitmapSlice().splitInRows(64, 64),
        60.milliseconds
    )

    val sound = resourcesVfs["sfx/explodify.mp3"].readSound()

    val sprite = views.stage.sprite(animationexp) {
        position(x, y)
        scale(0.5)
    }

    sprite.playAnimation(animationexp)

    sound.playNoCancel(1.playbackTimes).also { it.volume = 0.7 }
}
