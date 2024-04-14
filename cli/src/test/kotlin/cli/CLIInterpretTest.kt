package cli

import cli.function.Interpret
import util.CollectorLogger
import kotlin.test.Test
import kotlin.test.assertEquals

class CLIInterpretTest {
    @Test
    fun testInterpretWithPrintlnWithLiteral() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "interpret" to Interpret(logger = collectorLogger),
                ),
            )

        cli.run(listOf("interpret", "-f", "src/test/resources/interpret/println_with_literal.ps"))

        val logs = collectorLogger.getLogs()

        assertEquals(1, logs.size)
        assertEquals("test", logs[0])
    }

    @Test
    fun testInterpretWithPrintlnWithNullVariable() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "interpret" to Interpret(logger = collectorLogger),
                ),
            )

        cli.run(listOf("interpret", "-f", "src/test/resources/interpret/println_with_null_variable.ps"))

        val logs = collectorLogger.getLogs()

        assertEquals(1, logs.size)
        assertEquals("null", logs[0])
    }

    @Test
    fun testInterpretWithPrintlnWithVariable() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "interpret" to Interpret(logger = collectorLogger),
                ),
            )

        cli.run(listOf("interpret", "-f", "src/test/resources/interpret/println_with_variable.ps"))

        val logs = collectorLogger.getLogs()

        assertEquals(1, logs.size)
        assertEquals("5.0", logs[0])
    }

    @Test
    fun testInterpretWithPrintlnWithSum() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "interpret" to Interpret(logger = collectorLogger),
                ),
            )

        cli.run(listOf("interpret", "-f", "src/test/resources/interpret/println_with_sum.ps"))

        val logs = collectorLogger.getLogs()

        assertEquals(1, logs.size)
        assertEquals("10", logs[0])
    }

    @Test
    fun testMultiplePrintsWithLiteral() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "interpret" to Interpret(logger = collectorLogger),
                ),
            )

        cli.run(listOf("interpret", "-f", "src/test/resources/interpret/multiple_print_with_literal.ps"))

        val logs = collectorLogger.getLogs()

        assertEquals(2, logs.size)
        assertEquals("hola", logs[0])
        assertEquals("1", logs[1])
    }
}
