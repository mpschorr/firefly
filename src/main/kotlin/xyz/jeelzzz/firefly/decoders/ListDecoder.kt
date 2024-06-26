package xyz.jeelzzz.firefly.decoders

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.MemoryConfiguration
import xyz.jeelzzz.firefly.ConfigNode
import xyz.jeelzzz.firefly.DecodeContext
import xyz.jeelzzz.firefly.ValueDecoder
import kotlin.reflect.KClass

class ListDecoder {
    fun <T : Any> decode(decoder: ValueDecoder<out T>, node: ConfigNode, ctx: DecodeContext): List<T> {
        if (!node.configuration.isList(node.key))
            throw IllegalArgumentException("Property ${node.key} was expected to be a List but was not")

        // Create fake configuration for list this is so stupid
        val fakeSection = MemoryConfiguration()
        val elements = node.configuration.getList(node.key)

        return elements.map {
            fakeSection.set("tmp", it)
            val elementNode = ConfigNode(fakeSection, "tmp")
            decoder.decode(elementNode, ctx)
        }
    }

    fun <T : Any> decodeDataClass(decoder: DataClassDecoder, kClass: KClass<T>, node: ConfigNode, ctx: DecodeContext): List<T> {
        if (!node.configuration.isList(node.key))
            throw IllegalArgumentException("Property ${node.key} was expected to be a List but was not")
        if (!kClass.isData)
            throw IllegalArgumentException("Generic type of list ${node.key} was not a data class")

        val elements = node.configuration.getList(node.key)

        return elements.map map@{
            if (it !is HashMap<*,*>)
                throw IllegalArgumentException("Element was not a map")

            val elementConfig = mapToConfig(it)
            val elementNode = ConfigNode(elementConfig, "")
            decoder.decode(kClass, elementNode, ctx)
        }
    }

    private fun mapToConfig(map: HashMap<*,*>): ConfigurationSection {
        val section = MemoryConfiguration()
        map.forEach { (key, value) ->
            val configValue =
                if (value is HashMap<*,*>)
                    mapToConfig(value)
                else
                    value

            section.set(key.toString(), configValue)
        }

        return section
    }
}