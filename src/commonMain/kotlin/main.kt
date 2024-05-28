import alienBlaster.ui.*
import kotlin.random.*
import kotlin.time.Duration.Companion.seconds
import korlibs.image.color.*
import korlibs.korge.*
import korlibs.korge.view.*
import korlibs.korge.time.*
import korlibs.io.file.std.*
import korlibs.korge.view.collision.*
import korlibs.logger.Console.trace
import korlibs.image.format.*
import korlibs.korge.input.*
import korlibs.korge.view.Circle
import korlibs.korge.view.align.*
import korlibs.korge.view.onClick
import korlibs.math.geom.*
import korlibs.math.geom.slice.*
import korlibs.math.geom.vector.*
import korlibs.time.*
import alienBlaster.interaction.*
import alienBlaster.effects.playKaboom

suspend fun main() = Korge(title = "Alien Blaster",
    windowSize = Size(1000, 1440), virtualSize = Size(640, 480),
    backgroundColor = Colors.BLACK) {

    val mainContainer = Container().addTo(this)

    val alienImage = resourcesVfs["AlienSprites/alien_basic.png"].readBitmap()
    val backgroundImage = resourcesVfs["space_bcg.png"].readBitmap()

    val backgroundSprite = Sprite(backgroundImage).apply {
        xy(0, 0)
        width = views.virtualWidth.toDouble()
        height = views.virtualHeight.toDouble()
    }
    mainContainer.addChild(backgroundSprite)

    val center = resourcesVfs["Area1/Center.png"].readBitmap()
    //val rings1 = resourcesVfs["Area1/Rings1.png"].readBitmap()
    val rings2 = resourcesVfs["Area1/Rings2.png"].readBitmap()

    val centerImg = image(center).xy((width / 2) - 45, (height / 2) - 45).apply {
        size(90.0, 90.0)}
    /*val rings1Img = image(rings1).xy((width / 2) - 105, (height / 2) - 105).apply {
        size(210.0, 210.0)}*/
    val rings2Img = image(rings2).xy((width / 2) - 125.0, (height / 2) - 125.0).apply {
        size(250.0, 250.0)}


    val ringHitbox1 = Circle(135, Colors.GOLD).xy((width / 2) - 135, (height / 2) - 135).addTo(mainContainer)
    val ringHitbox2 = Circle(80, Colors.GREEN).xy((width / 2) - 80, (height / 2) - 80).addTo(mainContainer)

    // Create a list to store all enemies
    val enemies = mutableListOf<BasicAlien>()

    interval(1.seconds) {
        val edge = Random.nextInt(4)
        val enemy = BasicAlien(views, alienImage, centerImg)
        stage.addChild(enemy.basicAlienSprite)
        enemies.add(enemy) // Add the new enemy to the list

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
    // Create a list to store enemies to be removed
    val enemiesToRemove = mutableListOf<BasicAlien>()

    // Check each enemy for a collision
    for (enemy in enemies) {
        if (enemy.basicAlienSprite.pos.isInRing((ringHitbox1.radius),
                (ringHitbox2.radius), Point(width / 2, height / 2))) {
            enemy.basicAlienSprite.removeFromParent()
            playKaboom(views, enemy.x, enemy.y)
            enemiesToRemove.add(enemy) // Add the enemy to the removal list
        }
    }

    // Remove the enemies from the main list
    enemies.removeAll(enemiesToRemove)
}
}
