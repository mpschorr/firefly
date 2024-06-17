package xyz.jeelzzz.firefly.decoders

import org.bukkit.Color
import org.bukkit.util.Vector
import xyz.jeelzzz.firefly.ConfigNode
import xyz.jeelzzz.firefly.ValueDecoder

class BukkitColorDecoder : ValueDecoder<Color> {
    override fun decode(node: ConfigNode): Color {
        return node.getColor()
    }
}

class VectorDecoder : ValueDecoder<Vector> {
    override fun decode(node: ConfigNode): Vector {
        return node.getVector()
    }
}