package xyz.jeelzzz.firefly

import org.bukkit.Color
import org.bukkit.util.Vector
import xyz.jeelzzz.firefly.decoders.*
import kotlin.reflect.KType
import kotlin.reflect.full.createType

class FireflyLoaderOptions {
    val decoders: MutableMap<KType,ValueDecoder<*>> = mutableMapOf(
        Boolean::class.createType() to BooleanDecoder(),
        Double::class.createType() to DoubleDecoder(),
        Int::class.createType() to IntDecoder(),
        Long::class.createType() to LongDecoder(),
        String::class.createType() to StringDecoder(),
        Color::class.createType() to BukkitColorDecoder(),
        Vector::class.createType() to VectorDecoder(),
    )

    inline fun <reified T> addDecoder(decoder: ValueDecoder<*>) {
        decoders[T::class.createType()] = decoder
    }
}