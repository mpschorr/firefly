package xyz.jeelzzz.firefly

/**
 * An interface for decoding config node values
 * @param <T> the type of value that this decoder handles
 */
interface ValueDecoder<T> {
    /**
     * Decodes a configuration node into a value of type T
     *
     * @param node the configuration node to decode
     * @return the decoded value of type T
     */
    fun decode(node: ConfigNode, ctx: DecodeContext): T
}