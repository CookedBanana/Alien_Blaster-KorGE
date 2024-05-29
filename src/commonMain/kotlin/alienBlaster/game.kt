package alienBlaster

import BasicAlien
import alienBlaster.effects.*
import alienBlaster.interaction.*
import korlibs.image.color.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.input.*
import korlibs.korge.time.*
import korlibs.korge.view.*
import korlibs.korge.view.Circle
import korlibs.logger.Console.trace
import korlibs.math.geom.*
import kotlin.random.*
import kotlin.time.Duration.Companion.seconds

suspend fun game(mainContainer: Container, width: Double, height: Double, views: Views, stage: Stage) {

    val alienImage = resourcesVfs["AlienSprites/alien_basic.png"].readBitmap()

    val center = resourcesVfs["Area1/Center.png"].readBitmap()
    val rings2 = resourcesVfs["Area1/Rings2.png"].readBitmap()

    val centerImg = stage.image(center).xy((width / 2) - 45, (height / 2) - 45).apply {
        size(90.0, 90.0)}
    val rings2Img = stage.image(rings2).xy((width / 2) - 125.0, (height / 2) - 125.0).apply {
        size(250.0, 250.0)}

    val ringHitbox1 = Circle(135, Colors.TRANSPARENT).xy((width / 2) - 135, (height / 2) - 135).addTo(mainContainer)
    val ringHitbox2 = Circle(80, Colors.TRANSPARENT).xy((width / 2) - 80, (height / 2) - 80).addTo(mainContainer)

    var counter = 0

    val counterText = stage.text("Pokonania: $counter") {
        position(views.virtualWidth - 100, 20) // Adjust these values to position the counter
        color = Colors.WHITE
    }

    val enemies = mutableListOf<BasicAlien>()

    stage.interval(1.seconds) {
        val edge = Random.nextInt(4)
        val enemy = BasicAlien(views, alienImage, centerImg)
        stage.addChild(enemy.basicAlienSprite)
        enemies.add(enemy)

        when (edge) {
            0 -> {
                enemy.basicAlienSprite.xy(Random.nextInt(640), 0)
                trace("Summon up")
            }

            1 -> {
                enemy.basicAlienSprite.xy(Random.nextInt(640), 480)
                trace("Summon down")
            }

            2 -> {
                enemy.basicAlienSprite.xy(0, Random.nextInt(480))
                trace("Summon left")
            }

            else -> {
                enemy.basicAlienSprite.xy(640, Random.nextInt(480))
                trace("Summon right")
            }
        }
    }

    stage.onClick {
        val enemiesToRemove = mutableListOf<BasicAlien>()

        for (enemy in enemies) {
            if (enemy.basicAlienSprite.pos.isInRing((ringHitbox1.radius),
                    (ringHitbox2.radius), Point(width / 2, height / 2))) {
                enemy.basicAlienSprite.removeFromParent()
                playKaboom(views, enemy.x, enemy.y)
                counter++
                counterText.text = "Pokonania: $counter"
                enemiesToRemove.add(enemy)
            }
        }

        enemies.removeAll(enemiesToRemove)
    }
}
