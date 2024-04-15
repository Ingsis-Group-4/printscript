package cli

import cli.function.BufferedInterpret
import util.CollectorLogger
import kotlin.test.Test
import kotlin.test.assertEquals

class CLIBufferedInterpretTest {
    @Test
    fun testNotIf() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "interpret" to BufferedInterpret(logger = collectorLogger),
                ),
            )

        cli.run(listOf("interpret", "-f", "src/test/resources/interpret/if.ps"))

        val logs = collectorLogger.getLogs()

        assertEquals(1, logs.size)
        assertEquals("outside of conditional", logs[0])
    }

    @Test
    fun testIfNotElse() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "interpret" to BufferedInterpret(logger = collectorLogger),
                ),
            )

        cli.run(listOf("interpret", "-f", "src/test/resources/interpret/else_statement_false.ps"))

        val logs = collectorLogger.getLogs()

        assertEquals(2, logs.size)
        assertEquals("else statement working correctly", logs[0])
        assertEquals("outside of conditional", logs[1])
    }

    @Test
    fun testIf() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "interpret" to BufferedInterpret(logger = collectorLogger),
                ),
            )

        cli.run(listOf("interpret", "-f", "src/test/resources/interpret/if_statement_true.ps"))

        val logs = collectorLogger.getLogs()

        assertEquals(2, logs.size)
        assertEquals("if statement working correctly", logs[0])
        assertEquals("outside of conditional", logs[1])
    }

    @Test
    fun testNotIfYesElse() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "interpret" to BufferedInterpret(logger = collectorLogger),
                ),
            )

        cli.run(listOf("interpret", "-f", "src/test/resources/interpret/else_statement_true.ps"))

        val logs = collectorLogger.getLogs()

        assertEquals(2, logs.size)
        assertEquals("else statement working correctly", logs[0])
        assertEquals("outside of conditional", logs[1])
    }
}
