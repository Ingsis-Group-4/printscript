package cli

import cli.function.Analyze
import cli.function.BufferedInterpret
import cli.function.Format
import cli.function.Verify

fun main(args: Array<String>) {
    val cli =
        CLI(
            mapOf(
                "interpret" to BufferedInterpret(),
                "analyze" to Analyze(),
                "verify" to Verify(),
                "format" to Format(),
            ),
        )

    cli.run(args.toList())
}
