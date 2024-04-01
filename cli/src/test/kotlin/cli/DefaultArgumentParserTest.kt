package cli

import cli.argument.DefaultArgumentParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.Test

class DefaultArgumentParserTest {
    val parser = DefaultArgumentParser()

    @Test
    fun testParseFlagWithArgument() {
        val args = listOf("-f", "file.txt")

        val result = parser.parse(args)

        assertEquals(1, result.size)
        assertTrue(result.containsKey("-f"))
        assertEquals("file.txt", result["-f"])
    }

    @Test
    fun testParseFlagWithoutArgumentError() {
        val args = listOf("-f")

        try {
            parser.parse(args)
        } catch (e: IllegalArgumentException) {
            assertEquals("Flag -f requires an argument", e.message)
        }
    }

    @Test
    fun testParseMultipleFlags() {
        val args = listOf("-f", "file.txt", "-c", "config.json")

        val result = parser.parse(args)

        assertEquals(2, result.size)
        assertTrue(result.containsKey("-f"))
        assertEquals("file.txt", result["-f"])
        assertTrue(result.containsKey("-c"))
        assertEquals("config.json", result["-c"])
    }
}
