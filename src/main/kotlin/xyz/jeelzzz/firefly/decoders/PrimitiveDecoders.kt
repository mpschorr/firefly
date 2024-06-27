package xyz.jeelzzz.firefly.decoders

import xyz.jeelzzz.firefly.ConfigNode
import xyz.jeelzzz.firefly.DecodeContext
import xyz.jeelzzz.firefly.ValueDecodeException
import xyz.jeelzzz.firefly.ValueDecoder

class BooleanDecoder : ValueDecoder<Boolean> {
    override fun decode(node: ConfigNode, ctx: DecodeContext): Boolean {
        if (!node.isBoolean())
            throw ValueDecodeException("${node.decodeKey} was not a Boolean")
        return node.getBoolean()
    }
}

class DoubleDecoder : ValueDecoder<Double> {
    override fun decode(node: ConfigNode, ctx: DecodeContext): Double {
        if (!node.isDouble())
            throw ValueDecodeException("${node.decodeKey} was not a Double")
        return node.getDouble()
    }
}

class IntDecoder : ValueDecoder<Int> {
    override fun decode(node: ConfigNode, ctx: DecodeContext): Int {
        if (!node.isInt())
            throw ValueDecodeException("${node.decodeKey} was not an Int")
        return node.getInt()
    }
}

class LongDecoder : ValueDecoder<Long> {
    override fun decode(node: ConfigNode, ctx: DecodeContext): Long {
        if (!node.isLong())
            throw ValueDecodeException("${node.decodeKey} was not a Long")
        return node.getLong()
    }
}

class StringDecoder : ValueDecoder<String?> {
    override fun decode(node: ConfigNode, ctx: DecodeContext): String? {
        if (!node.isString())
            throw ValueDecodeException("${node.decodeKey} was not a String")
        return node.getString()
    }
}