package xyz.jeelzzz.firefly

import org.bukkit.configuration.file.YamlConfiguration
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ListDecodeTest {

    val loader = FireflyLoader()

    @Test
    fun testPrimitiveListDecode() {
        data class Data(
            val list: List<String>
        )

        val section = YamlConfiguration()
        section.loadFromString("list: [\"abc\", \"def\", \"xyz\"]")

        val result = loader.decode<Data>(section)
        assertIs<List<String>>(result.list)
        assertEquals(result.list[0], "abc")
        assertEquals(result.list[1], "def")
        assertEquals(result.list[2], "xyz")
    }

    @Test
    fun testDataClassListDecode() {
        data class Item(
            val abc: String,
            val xyz: Int,
        )
        data class Data(
            val list: List<Item>
        )

        val section = YamlConfiguration()
        section.loadFromString("""
            list:
                - abc: foo
                  xyz: 123
                - abc: bar
                  xyz: 789
        """.trimIndent())


        val result = loader.decode<Data>(section)
        assertIs<List<Item>>(result.list)
        assertEquals(result.list[0].abc, "foo")
        assertEquals(result.list[0].xyz, 123)
        assertEquals(result.list[1].abc, "bar")
        assertEquals(result.list[1].xyz, 789)
    }

    @Test
    fun testDataClassListWithSubDecode() {
        data class Sub(
            val foo: Int,
            val bar: String
        )
        data class Item(
            val scalar: Int,
            val sub: Sub
        )
        data class Data(
            val list: List<Item>
        )

        val section = YamlConfiguration()
        section.loadFromString("""
            list:
                - scalar: 727
                  sub:
                    foo: 272
                    bar: hello
                - scalar: 123
                  sub:
                    foo: 321
                    bar: world
        """.trimIndent())

        val result = loader.decode<Data>(section)
        assertIs<List<Item>>(result.list)
        assertEquals(2, result.list.size)
        assertEquals(727, result.list[0].scalar)
        assertEquals(272, result.list[0].sub.foo)
        assertEquals("hello", result.list[0].sub.bar)
        assertEquals(123, result.list[1].scalar)
        assertEquals(321, result.list[1].sub.foo)
        assertEquals("world", result.list[1].sub.bar)
    }
}