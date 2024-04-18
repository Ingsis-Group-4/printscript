package cli

import cli.function.Analyze
import org.junit.jupiter.api.Test
import util.CollectorLogger
import kotlin.test.assertEquals

class CLIAnalyzeTest {
    @Test
    fun testSuccessWithEmptyConfigFile() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "analyze" to Analyze(logger = collectorLogger),
                ),
            )

        cli.run(
            listOf(
                "analyze",
                "-f",
                "src/test/resources/analyze/program/println_with_camel_case_variable.ps",
                "-c",
                "src/test/resources/analyze/config/empty_config_file.json",
                "-v",
                "1.0"
            ),
        )

        val logs = collectorLogger.getLogs()

        assertEquals(1, logs.size)
        assertEquals("Static code analysis successful!", logs[0])
    }

    @Test
    fun testFailureWithCamelCaseNamingConfigFile() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "analyze" to Analyze(logger = collectorLogger),
                ),
            )

        cli.run(
            listOf(
                "analyze",
                "-f",
                "src/test/resources/analyze/program/println_with_snake_case_variable.ps",
                "-c",
                "src/test/resources/analyze/config/camel_case_config_file.json",
                "-v",
                "1.0"
            ),
        )

        val logs = collectorLogger.getLogs()

        assertEquals(1, logs.size)
        assertEquals("Rule failed at (line 1, column 5): Variable 'a_variable' does not follow naming rule", logs[0])
    }

    @Test
    fun testSuccessWithCamelCaseNamingConfigFile() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "analyze" to Analyze(logger = collectorLogger),
                ),
            )

        cli.run(
            listOf(
                "analyze",
                "-f",
                "src/test/resources/analyze/program/println_with_camel_case_variable.ps",
                "-c",
                "src/test/resources/analyze/config/camel_case_config_file.json",
                "-v",
                "1.0"
            ),
        )

        val logs = collectorLogger.getLogs()

        assertEquals(1, logs.size)
        assertEquals("Static code analysis successful!", logs[0])
    }

    @Test
    fun testFailureWithSnakeCaseNamingConfigFile() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "analyze" to Analyze(logger = collectorLogger),
                ),
            )

        cli.run(
            listOf(
                "analyze",
                "-f",
                "src/test/resources/analyze/program/println_with_camel_case_variable.ps",
                "-c",
                "src/test/resources/analyze/config/snake_case_config_file.json",
                "-v",
                "1.0"
            ),
        )

        val logs = collectorLogger.getLogs()

        assertEquals(1, logs.size)
        assertEquals("Rule failed at (line 1, column 5): Variable 'aVariable' does not follow naming rule", logs[0])
    }

    @Test
    fun testSuccessWithSnakeCaseNamingConfigFile() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "analyze" to Analyze(logger = collectorLogger),
                ),
            )

        cli.run(
            listOf(
                "analyze",
                "-f",
                "src/test/resources/analyze/program/println_with_snake_case_variable.ps",
                "-c",
                "src/test/resources/analyze/config/snake_case_config_file.json",
                "-v",
                "1.0"
            ),
        )

        val logs = collectorLogger.getLogs()

        assertEquals(1, logs.size)
        assertEquals("Static code analysis successful!", logs[0])
    }

    @Test
    fun testFailureWithCamelCaseNamingConfigFileAndMultipleErrors() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "analyze" to Analyze(logger = collectorLogger),
                ),
            )

        cli.run(
            listOf(
                "analyze",
                "-f",
                "src/test/resources/analyze/program/declarations_with_multiple_namings.ps",
                "-c",
                "src/test/resources/analyze/config/camel_case_config_file.json",
                "-v",
                "1.0"
            ),
        )

        val logs = collectorLogger.getLogs()

        assertEquals(2, logs.size)
        assertEquals("Rule failed at (line 3, column 5): Variable 'a_variable' does not follow naming rule", logs[0])
        assertEquals("Rule failed at (line 4, column 5): Variable 'another_variable' does not follow naming rule", logs[1])
    }

    @Test
    fun testFailureWithPrintlnArgumentConfigFile() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "analyze" to Analyze(logger = collectorLogger),
                ),
            )

        cli.run(
            listOf(
                "analyze",
                "-f",
                "src/test/resources/analyze/program/println_with_sum.ps",
                "-c",
                "src/test/resources/analyze/config/println_argument_and_camel_case_config_file.json",
                "-v",
                "1.0"
            ),
        )

        val logs = collectorLogger.getLogs()

        assertEquals(1, logs.size)
        assertEquals("Rule failed at (line 1, column 9): PrintLn argument does not follow argument rule", logs[0])
    }

    @Test
    fun testSuccessWithPrintlnArgumentConfigFile() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "analyze" to Analyze(logger = collectorLogger),
                ),
            )

        cli.run(
            listOf(
                "analyze",
                "-f",
                "src/test/resources/analyze/program/println_with_camel_case_variable.ps",
                "-c",
                "src/test/resources/analyze/config/println_argument_and_camel_case_config_file.json",
                "-v",
                "1.0"
            ),
        )

        val logs = collectorLogger.getLogs()

        assertEquals(1, logs.size)
        assertEquals("Static code analysis successful!", logs[0])
    }
}
