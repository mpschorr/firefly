package xyz.jeelzzz.firefly.decoders

import xyz.jeelzzz.firefly.ConfigNode
import xyz.jeelzzz.firefly.DecodeContext
import xyz.jeelzzz.firefly.ValueDecoder

class BooleanDecoder : ValueDecoder<Boolean> {
    override fun decode(node: ConfigNode, ctx: DecodeContext): Boolean {
        return node.getBoolean()
    }
}

class DoubleDecoder : ValueDecoder<Double> {
    override fun decode(node: ConfigNode, ctx: DecodeContext): Double {
        return node.getDouble()
    }
}

class IntDecoder : ValueDecoder<Int> {
    override fun decode(node: ConfigNode, ctx: DecodeContext): Int {
        return node.getInt()
    }
}

class LongDecoder : ValueDecoder<Long> {
    override fun decode(node: ConfigNode, ctx: DecodeContext): Long {
        return node.getLong()
    }
}

class StringDecoder : ValueDecoder<String?> {
    override fun decode(node: ConfigNode, ctx: DecodeContext): String? {
        return node.getString()
    }
}