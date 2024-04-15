package cli

import cli.function.BufferedInterpret
import util.CollectorLogger
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class CLIBufferedInterpretTest {
    @Ignore
    @Test
    fun testInterpretWithPrintlnWithLiteral() {
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
    }
}
