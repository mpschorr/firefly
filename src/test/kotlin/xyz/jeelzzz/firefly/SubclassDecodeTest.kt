package xyz.jeelzzz.firefly

import org.bukkit.configuration.MemoryConfiguration
import kotlin.test.Test
import kotlin.test.assertEquals

data class Root(
    val abc: String,
    val xyz: Sub
) {
    data class Sub(
        val foo: String
    )
}

class SubclassDecodeTest {
    @Test
    fun testSubclassDecode() {
        val section = MemoryConfiguration()
        section.set("abc", "hello")
        section.set("xyz.foo", "world")

        val loader = FireflyLoader()
        val result = loader.decode<Root>(section)

        val expectedResult = Root("hello", Root.Sub("world"))
        assertEquals(expectedResult, result)
    }
}