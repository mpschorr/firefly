package xyz.jeelzzz.firefly.decoders

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.MemoryConfiguration
import xyz.jeelzzz.firefly.ConfigNode
import xyz.jeelzzz.firefly.DecodeContext
import xyz.jeelzzz.firefly.ValueDecodeException
import xyz.jeelzzz.firefly.ValueDecoder
import kotlin.reflect.KClass

class ListDecoder {
    fun <T : Any> decode(decoder: ValueDecoder<out T>, node: ConfigNode, ctx: DecodeContext): List<T> {
        if (!node.configuration.isList(node.bukkitKey))
            throw ValueDecodeException("Property ${node.decodeKey} was not a List")

        // Create fake configuration for list this is so stupid
        val fakeSection = MemoryConfiguration()
        val elements = node.configuration.getList(node.bukkitKey)

        return elements.mapIndexed { index, it ->
            fakeSection.set("_", it)
            val elementNode = ConfigNode(fakeSection, "_", "${node.decodeKey}.${index}")
            decoder.decode(elementNode, ctx)
        }
    }

    fun <T : Any> decodeDataClass(decoder: DataClassDecoder, kClass: KClass<T>, node: ConfigNode, ctx: DecodeContext): List<T> {
        if (!node.configuration.isList(node.bukkitKey))
            throw ValueDecodeException("Property ${node.decodeKey} was not a List")
        if (!kClass.isData)
            throw ValueDecodeException("List type of ${node.decodeKey} was not a data class")

        val elements = node.configuration.getList(node.bukkitKey)

        return elements.mapIndexed map@{ index, it ->
            if (it !is HashMap<*,*>)
                throw IllegalArgumentException("Element was not a map")

            val elementConfig = mapToConfig(it)
            val elementNode = ConfigNode(elementConfig, "", "${node.decodeKey}.${index}")
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