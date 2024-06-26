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
        val decoder = DataClassDecoder(this)
        val rootNode = ConfigNode(configuration, "")
        val ctx = DecodeContext(this.options)
        return decoder.decode(T::class, rootNode, ctx)
    }
}