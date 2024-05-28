package alienBlaster.effects

import korlibs.audio.sound.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.*
import korlibs.korge.view.*
import korlibs.math.geom.slice.*
import kotlin.time.Duration.Companion.milliseconds

suspend fun playKaboom(x: Double, y: Double) = Korge {
    val animationexp = SpriteAnimation(
        resourcesVfs["gfx/exp2.jpg"].readBitmapSlice().splitInRows(64, 64),
        60.milliseconds
    )

    val sound = resourcesVfs["sfx/explodify.mp3"].readSound()

    val sprite = sprite(animationexp) {
        position(x, y)
        scale = 2.0
    }

    sprite.playAnimationLooped(animationexp)

    sound.play()
}
