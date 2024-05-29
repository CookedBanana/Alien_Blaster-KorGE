import korlibs.image.color.*
import korlibs.image.format.*
import korlibs.korge.*
import korlibs.korge.view.*
import korlibs.io.file.std.*
import korlibs.korge.view.collision.*
import korlibs.math.geom.*
import alienBlaster.game
import korlibs.image.text.*
import korlibs.korge.input.*
import korlibs.korge.view.onClick
import korlibs.audio.sound.*
import korlibs.io.async.*

suspend fun main() = Korge(title = "Alien Blaster",
    windowSize = Size(1000, 1440), virtualSize = Size(640, 480),
    backgroundColor = Colors.BLACK) {

    val mainContainer = Container().addTo(this)

    val backgroundImage = resourcesVfs["space_bcg.png"].readBitmap()

    val backgroundSprite = Sprite(backgroundImage).apply {
        xy(0, 0)
        width = views.virtualWidth.toDouble()
        height = views.virtualHeight.toDouble()
    }.addTo(mainContainer)

    val titleText = text("Alien Blaster") {
        position(views.virtualWidth / 2 - width / 2, views.virtualHeight / 2 - 100)
        color = Colors.WHITE
    }.addTo(mainContainer)

    val explanationText = text("") {
        position(views.virtualWidth / 2 - width / 2, views.virtualHeight / 2 - 30)
        color = Colors.WHITE
    }.addTo(mainContainer)

    val buttonText = "Zacznij gre"
    val button = solidRect(width = 200, height = 50, color = Colors.BLUEVIOLET) {
        position(views.virtualWidth / 2 - width / 2, views.virtualHeight / 2 + height / 2)
    }.addTo(mainContainer)

    val buttonLabel = mainContainer.text(buttonText) {
        position(button.x + button.width / 2 - width / 2, button.y + button.height / 2 - height / 2)
        color = Colors.WHITE
    }.addTo(mainContainer)

    button.onClick {
        titleText.removeFromParent()
        button.removeFromParent()
        buttonLabel.removeFromParent()
        launchImmediately {
            game(mainContainer, views.virtualWidth.toDouble(), views.virtualHeight.toDouble(), views, stage)
        }
    }

}
