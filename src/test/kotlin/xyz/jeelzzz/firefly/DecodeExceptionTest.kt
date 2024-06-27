package xyz.jeelzzz.firefly

import org.bukkit.configuration.MemoryConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertContains

class DecodeExceptionTest {
    private val loader = FireflyLoader()

    @Test
    fun testWrongPrimitiveException() {
        val exception = assertThrows<ValueDecodeException> {
            data class Data(
                val abc: Int
            )
            val section = MemoryConfiguration()
            section.set("abc", "blah")
            val result = loader.decode<Data>(section)
        }

        assertContains(exception.message ?: "", "not an Int")
    }

    @Test
    fun testListException() {
        val exception = assertThrows<ValueDecodeException> {
            data class Data(
                val abc: List<Int>
            )
            val section = MemoryConfiguration()
            section.set("abc", listOf(1, "abc"))
            val result = loader.decode<Data>(section)
        }

        assertContains(exception.message ?: "", "not an Int")
    }

    @Test
    fun testNotMapException() {
        val exception = assertThrows<ValueDecodeException> {
            data class Sub(
                val abc: Int
            )
            data class Data(
                val sub: Sub
            )

            val section = YamlConfiguration()
            section.loadFromString("""
                abc: 1
            """.trimIndent())

            val result = loader.decode<Data>(section)
        }

        assertContains(exception.message ?: "", "a configuration section")
    }
}