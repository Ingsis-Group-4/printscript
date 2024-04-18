package cli

import cli.function.Format
import org.junit.jupiter.api.Assertions.assertEquals
import writer.LogWriter
import kotlin.test.Test

class CLIFormatTest {
    @Test
    fun `test_001 String Declaration`() {
        val expected = "const a : string = \"String\";"
        val logWriter = LogWriter()
        val cli =
            CLI(
                mapOf(
                    "format" to Format(writer = logWriter),
                ),
            )
        cli.run(
            listOf(
                "format",
                "-f",
                "src/test/resources/format/program/test_001_result.ps",
                "-c",
                "src/test/resources/format/config/formatter.test.config.json",
            ),
        )

        val actual = logWriter.getOutput()
        assertEquals(expected, actual)
    }

    @Test
    fun `test_002 Int Declaration`() {
        val expected = "let secretNumber : number = 1000;"
        val logWriter = LogWriter()
        val cli =
            CLI(
                mapOf(
                    "format" to Format(writer = logWriter),
                ),
            )
        cli.run(
            listOf(
                "format",
                "-f",
                "src/test/resources/format/program/test_002_result.ps",
                "-c",
                "src/test/resources/format/config/formatter.test.config.json",
            ),
        )

        val actual = logWriter.getOutput()
        assertEquals(expected, actual)
    }

    @Test
    fun `test_003 Two Statements`() {
        val expected = "let first : number = 1;\nlet second : string = \"2\";"
        val logWriter = LogWriter()
        val cli =
            CLI(
                mapOf(
                    "format" to Format(writer = logWriter),
                ),
            )
        cli.run(
            listOf(
                "format",
                "-f",
                "src/test/resources/format/program/test_003_result.ps",
                "-c",
                "src/test/resources/format/config/formatter.test.config.json",
            ),
        )

        val actual = logWriter.getOutput()
        assertEquals(expected, actual)
    }

    @Test
    fun `test_004 Print Statement`() {
        val expected = "let a : number = 1;\n\n\nprintln(1 + a);"
        val logWriter = LogWriter()
        val cli =
            CLI(
                mapOf(
                    "format" to Format(writer = logWriter),
                ),
            )
        cli.run(
            listOf(
                "format",
                "-f",
                "src/test/resources/format/program/test_004_result.ps",
                "-c",
                "src/test/resources/format/config/formatter.test.config.json",
            ),
        )

        val actual = logWriter.getOutput()
        assertEquals(expected, actual)
    }

    @Test
    fun `test_005 If Statement`() {
        val expected = "let a : boolean = true;\nif (a)\n{\n\n\n  println(1);\n}\nconst b : boolean = false;"
        val logWriter = LogWriter()
        val cli =
            CLI(
                mapOf(
                    "format" to Format(writer = logWriter),
                ),
            )
        cli.run(
            listOf(
                "format",
                "-f",
                "src/test/resources/format/program/test_005_result.ps",
                "-c",
                "src/test/resources/format/config/formatter.test.config.json",
            ),
        )

        val actual = logWriter.getOutput()
        assertEquals(expected, actual)
    }

    @Test
    fun `test_006 If Else Statement`() {
        val expected = "let a : boolean = true;\nif (a)\n{\n\n\n  println(1);\n} else\n{\n\n\n  println(2);\n}"
        val logWriter = LogWriter()
        val cli =
            CLI(
                mapOf(
                    "format" to Format(writer = logWriter),
                ),
            )
        cli.run(
            listOf(
                "format",
                "-f",
                "src/test/resources/format/program/test_006_result.ps",
                "-c",
                "src/test/resources/format/config/formatter.test.config.json",
            ),
        )

        val actual = logWriter.getOutput()
        assertEquals(expected, actual)
    }
}
