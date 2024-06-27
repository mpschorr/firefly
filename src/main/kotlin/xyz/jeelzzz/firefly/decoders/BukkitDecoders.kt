package xyz.jeelzzz.firefly.decoders

import org.bukkit.Color
import org.bukkit.util.Vector
import xyz.jeelzzz.firefly.ConfigNode
import xyz.jeelzzz.firefly.DecodeContext
import xyz.jeelzzz.firefly.ValueDecodeException
import xyz.jeelzzz.firefly.ValueDecoder

class BukkitColorDecoder : ValueDecoder<Color> {
    override fun decode(node: ConfigNode, ctx: DecodeContext): Color {
        if (!node.configuration.isColor(node.bukkitKey))
            throw ValueDecodeException("${node.decodeKey} is not a Bukkit-deserializable Color")

        return node.configuration.getColor(node.bukkitKey)
    }
}

class VectorDecoder : ValueDecoder<Vector> {
    override fun decode(node: ConfigNode, ctx: DecodeContext): Vector {
        if (!node.configuration.isColor(node.bukkitKey))
            throw ValueDecodeException("${node.decodeKey} is not a Bukkit-deserializable Vector")

        return node.configuration.getVector(node.bukkitKey)
    }
}