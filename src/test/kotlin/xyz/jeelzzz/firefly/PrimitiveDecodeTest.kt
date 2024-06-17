package xyz.jeelzzz.firefly

import org.bukkit.configuration.MemoryConfiguration
import kotlin.test.Test
import kotlin.test.assertEquals

class PrimitiveDecodeTest {
    private val loader = FireflyLoader()

    @Test
    fun testStringDecode() {
        data class Data (
            val abc: String,
        )

        val section = MemoryConfiguration()
        section.set("abc", "blah")

        val result = loader.decode<Data>(section)
        assertEquals(Data("blah"), result)
    }

    @Test
    fun testIntDecode() {
        data class Data (
            val xyz: Int,
        )

        val section = MemoryConfiguration()
        section.set("xyz", 80085)

        val result = loader.decode<Data>(section)
        assertEquals(Data(80085), result)
    }

    @Test
    fun testMixedDecode() {
        data class Data(
            val abc: String,
            val xyz: Int,
        )

        val section = MemoryConfiguration()
        section.set("abc", "foo")
        section.set("xyz", 123)

        val result = loader.decode<Data>(section)
        assertEquals("foo", result.abc)
        assertEquals(123, result.xyz)
    }
}