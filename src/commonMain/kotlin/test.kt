import korlibs.audio.sound.*
import korlibs.image.color.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.time.*
import korlibs.korge.view.*
import korlibs.korge.view.collision.*
import korlibs.logger.Console.trace
import korlibs.math.geom.*
import korlibs.math.geom.slice.*
import korlibs.math.random.*
import korlibs.time.*
import kotlin.random.*


suspend fun mainen() = Korge(windowSize = Size(1500, 1000), virtualSize = Size(640, 480),
    backgroundColor = Colors["#2b2b2b"]) {
    //val sceneContainer = sceneContainer()
    val circle = circle(100.0).xy((width / 2) - 100, (height / 2))
    val rect = solidRect(50.0, 50.0, Colors["#1157ff"]).xy(200, 125)

    val bitmap = resourcesVfs["alien_icon.png"].readBitmap()
    val image = image(bitmap).scale(3).xy(100, 80).apply {
        rotation =  Angle.fromDegrees( rotation.degrees + 1)}

    circle.onCollision({it == rect}){
        circle.color = Colors.GOLD
        val rect2 = solidRect(200.0, 200.0, Colors["#1157ff"]).xy(300, 225)
        trace("Collision detected!")
    }

    stage.mouse {
        move {
            val mouseX = views.input.mousePos.x - 100
            val mouseY = views.input.mousePos.y - 100
            circle.xy(mouseX, mouseY)
        }
    }


    val animationexp = SpriteAnimation(
        resourcesVfs["gfx/exp2.jpg"].readBitmapSlice().splitInRows(64, 64),
        60.milliseconds
    )

    val randomexp = Random(0L)
    interval(0.02.seconds) {
        sprite(animationexp).xy(randomexp[0, 640], randomexp[0, 480]).also { it.blendMode = BlendMode.SCREEN }
            .also { sprite -> sprite.onAnimationCompleted { sprite.removeFromParent() } }
            .playAnimation()
    }


    val sound = resourcesVfs["sfx/explodify.mp3"].readSound()
    val animations = SpriteAnimation(
        resourcesVfs["gfx/exp2.jpg"].readBitmapSlice().splitInRows(64, 64),
        60.milliseconds
    )

    val randoms = Random(0L)
    interval(.25.seconds) {
        sound.playNoCancel(1.playbackTimes).also { it.volume = 0.3 }
        sprite(animations).xy(randoms[0, 250], randoms[0, 250]).also { it.blendMode = BlendMode.SCREEN }
            .also { sprite -> sprite.onAnimationCompleted { sprite.removeFromParent() } }
            .playAnimation()
    }
}
