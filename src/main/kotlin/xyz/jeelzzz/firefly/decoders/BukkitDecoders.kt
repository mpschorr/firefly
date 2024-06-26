package xyz.jeelzzz.firefly.decoders

import org.bukkit.Color
import org.bukkit.util.Vector
import xyz.jeelzzz.firefly.ConfigNode
import xyz.jeelzzz.firefly.DecodeContext
import xyz.jeelzzz.firefly.ValueDecoder

class BukkitColorDecoder : ValueDecoder<Color> {
    override fun decode(node: ConfigNode, ctx: DecodeContext): Color {
        return node.getColor()
    }
}

class VectorDecoder : ValueDecoder<Vector> {
    override fun decode(node: ConfigNode, ctx: DecodeContext): Vector {
        return node.getVector()
    }
}