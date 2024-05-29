package alienBlaster

import BasicAlien
import alienBlaster.interaction.*
import korlibs.audio.sound.*
import korlibs.image.color.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.input.*
import korlibs.korge.time.*
import korlibs.korge.view.*
import korlibs.korge.view.Circle
import korlibs.korge.view.collision.*
import korlibs.math.geom.*
import kotlin.random.*
import kotlin.time.Duration.Companion.seconds

suspend fun game(mainContainer: Container, width: Double, height: Double, views: Views, stage: Stage) {

    val sound = resourcesVfs["sfx/explodify.mp3"].readSound()

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
        position(views.virtualWidth - 100, 20)
        color = Colors.WHITE
    }

    val enemies = mutableListOf<BasicAlien>()
    val collidedEnemies = mutableSetOf<BasicAlien>()

    stage.interval(1.seconds) {
        val edge = Random.nextInt(4)
        val enemy = BasicAlien(views, alienImage, centerImg)
        stage.addChild(enemy.basicAlienSprite)
        enemies.add(enemy)

        when (edge) {
            0 -> {
                enemy.basicAlienSprite.xy(Random.nextInt(640), 0)
            }

            1 -> {
                enemy.basicAlienSprite.xy(Random.nextInt(640), 480)
            }

            2 -> {
                enemy.basicAlienSprite.xy(0, Random.nextInt(480))
            }

            else -> {
                enemy.basicAlienSprite.xy(640, Random.nextInt(480))
            }
        }
    }

    fun checkCenterCollisions() {
        for (enemy in enemies) {
            if (enemy.basicAlienSprite.collidesWith(centerImg) && enemy !in collidedEnemies) {
                counter--
                counterText.text = "Pokonania: $counter"
                collidedEnemies.add(enemy)
            }
        }
    }

    fun checkRingCollisions() {
        val enemiesToRemove = mutableListOf<BasicAlien>()

        for (enemy in enemies) {
            if (enemy.basicAlienSprite.pos.isInRing((ringHitbox1.radius),
                    (ringHitbox2.radius), Point(width / 2, height / 2))) {
                enemy.basicAlienSprite.removeFromParent()
                sound.playNoCancel(1.playbackTimes).also { it.volume = 0.2 }
                enemiesToRemove.add(enemy)
            }
        }

        if (enemiesToRemove.isNotEmpty()) {
            enemies.removeAll(enemiesToRemove)
            counter += enemiesToRemove.size
            counterText.text = "Pokonania: $counter"
        } else{
            counter--
            counterText.text = "Pokonania: $counter"
        }
    }

    stage.addUpdater { checkCenterCollisions() }

    stage.onClick {
        checkRingCollisions()
    }
}
