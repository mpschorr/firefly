package xyz.jeelzzz.firefly

import org.bukkit.configuration.ConfigurationSection
import xyz.jeelzzz.firefly.decoders.DataClassDecoder

class FireflyLoader {

    val options: FireflyLoaderOptions

    constructor() {
        this.options = FireflyLoaderOptions()
    }

    constructor(options: FireflyLoaderOptions?) {
        this.options = options ?: FireflyLoaderOptions()
    }

    inline fun <reified T : Any> decode(configuration: ConfigurationSection): T {
//        TODO issues so i dont forget
//        need to recursively do data classes
//        could call this method again with the sub config butttt
//        that might not work for lists
//        god i fucking hate this
//        good morning btw you cutie :3

        val decoder = DataClassDecoder(this)
        val rootNode = ConfigNode(configuration, "")
        return decoder.decode(T::class, rootNode, options)
//        return ""
    }
}