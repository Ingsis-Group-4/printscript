package cli

import cli.function.Verify
import org.junit.jupiter.api.assertThrows
import util.CollectorLogger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CLIVerifyTest {
    @Test
    fun testValidDeclaration() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "verify" to Verify(logger = collectorLogger),
                ),
            )

        cli.run(
            listOf(
                "verify",
                "-f",
                "src/test/resources/verify/valid_declaration.ps",
                "-v",
                "1.0",
            ),
        )

        val logs = collectorLogger.getLogs()

        assertEquals(1, logs.size)
        assertEquals("Verification successful!", logs[0])
    }

    @Test
    fun testInvalidDeclaration() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "verify" to Verify(logger = collectorLogger),
                ),
            )

        assertThrows<IllegalArgumentException> {
            cli.run(
                listOf(
                    "verify",
                    "-f",
                    "src/test/resources/verify/invalid_declaration.ps",
                    "-v",
                    "1.0",
                ),
            )
        }

        try {
            cli.run(
                listOf(
                    "verify",
                    "-f",
                    "src/test/resources/verify/invalid_declaration.ps",
                    "-v",
                    "1.0",
                ),
            )
        } catch (e: IllegalArgumentException) {
            assertEquals("Parsing Error: Expected a let token followed by an identifier and a colon token at position 0", e.message)
        }
    }

    @Test
    fun testKeyWordAsIdentifier() {
        val collectorLogger = CollectorLogger()

        val cli =
            CLI(
                mapOf(
                    "verify" to Verify(logger = collectorLogger),
                ),
            )

        assertThrows<IllegalArgumentException> {
            cli.run(
                listOf(
                    "verify",
                    "-f",
                    "src/test/resources/verify/keyword_as_identifier.ps",
                    "-v",
                    "1.1",
                ),
            )
        }

        try {
            cli.run(
                listOf(
                    "verify",
                    "-f",
                    "src/test/resources/verify/keyword_as_identifier.ps",
                    "-v",
                    "1.1",
                ),
            )
        } catch (e: IllegalArgumentException) {
            assertEquals("Parsing Error: Expected a let token followed by an identifier and a colon token at position 0", e.message)
        }
    }

    @Test
    fun mockTest(){
        assertTrue(false)
    }
}
