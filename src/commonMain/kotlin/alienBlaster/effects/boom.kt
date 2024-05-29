package alienBlaster.effects

import korlibs.audio.sound.*
import korlibs.io.file.std.*
import korlibs.korge.view.Views

suspend fun playKaboom(views: Views, x: Double, y: Double) {


    val sound = resourcesVfs["sfx/explodify.mp3"].readSound()

    sound.playNoCancel(1.playbackTimes).also { it.volume = 0.2 }
}
