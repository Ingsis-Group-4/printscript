package cli

import cli.function.Format
import org.junit.jupiter.api.Assertions.assertEquals
import writer.LogWriter
import kotlin.test.Test

class CLIFormatTest {
    @Test
    fun `test_001 String Declaration`() {
        val expected = "let a : String = \"String\";"
        val logWriter = LogWriter()
        val cli =
            CLI(
                mapOf(
                    "format" to Format(writer = logWriter),
                ),
            )
        cli.run(listOf("format", "-f", "src/test/resources/format/program/test_001_result.ps"))

        val actual = logWriter.getOutput()
        assertEquals(expected, actual)
    }

    @Test
    fun `test_002 Int Declaration`() {
        val expected = "let secretNumber : Number = 1000;"
        val logWriter = LogWriter()
        val cli =
            CLI(
                mapOf(
                    "format" to Format(writer = logWriter),
                ),
            )
        cli.run(listOf("format", "-f", "src/test/resources/format/program/test_002_result.ps"))

        val actual = logWriter.getOutput()
        assertEquals(expected, actual)
    }

    @Test
    fun `test_003 Two Statements`() {
        val expected = "let first : Number = 1;\nlet second : String = \"2\";"
        val logWriter = LogWriter()
        val cli =
            CLI(
                mapOf(
                    "format" to Format(writer = logWriter),
                ),
            )
        cli.run(listOf("format", "-f", "src/test/resources/format/program/test_003_result.ps"))

        val actual = logWriter.getOutput()
        assertEquals(expected, actual)
    }

    @Test
    fun `test_004 Print Statement`() {
        val expected = "let a : Number = 1;\n\n\nprintln(1 + a);"
        val logWriter = LogWriter()
        val cli =
            CLI(
                mapOf(
                    "format" to Format(writer = logWriter),
                ),
            )
        cli.run(listOf("format", "-f", "src/test/resources/format/program/test_004_result.ps"))

        val actual = logWriter.getOutput()
        assertEquals(expected, actual)
    }
}
