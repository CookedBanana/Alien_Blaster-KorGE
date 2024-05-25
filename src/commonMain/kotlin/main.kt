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

suspend fun main() = Korge(title = "Alien Blaster",
    windowSize = Size(1000, 1000), virtualSize = Size(640, 480),
    backgroundColor = Colors.BLACK) {

    val backgroundImage = resourcesVfs["space_bcg.png"].readBitmap()

    val mainContainer = Container().addTo(this)

    val backgroundSprite = Sprite(backgroundImage).apply {
        xy(0, 0)
        width = views.virtualWidth.toDouble()
        height = views.virtualHeight.toDouble()
    }
    mainContainer.addChild(backgroundSprite)

    //val sceneContainer = sceneContainer()
    val circle3 = circle(80.0, Colors["#1157ff"]).xy((width / 2) - 80, (height / 2) - 80)
    val circle2 = circle(70.0, Colors["#5fc3ff"]).xy((width / 2) - 70, (height / 2) - 70)
    val circle1 = circle(60.0, Colors["#1157ff"]).xy((width / 2) - 60, (height / 2) - 60)
    val center = circle(50.0, Colors.BLACK).xy((width / 2) - 50, (height / 2) - 50 )

    val image = resourcesVfs["alien_icon.png"].readBitmapSlice().splitInRows(64, 64)

    interval(1.seconds) {
        val edge = Random[0, 3]
        if(edge == 0){
            val circle12 = circle(40.0, Colors["#1157ff"]).xy(0, Random[0, 480])}
        else if(edge == 1){
            val circle12 = circle(40.0, Colors["#1157ff"]).xy(640, Random[0, 480])}
        else if(edge == 2){
            val circle12 = circle(40.0, Colors["#1157ff"]).xy(Random[0, 640], 480)}
        else{
            val circle12 = circle(40.0, Colors["#1157ff"]).xy(Random[0, 640], 0)}
    }

    center.onCollision({it == image}){
    trace("Collision detected!")
}}

