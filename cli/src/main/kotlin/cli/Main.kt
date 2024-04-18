package cli

import cli.function.Analyze
import cli.function.Format
import cli.function.Interpret
import cli.function.Verify

fun main(args: Array<String>) {
    val cli =
        CLI(
            mapOf(
                "interpret" to Interpret(),
                "analyze" to Analyze(),
                "verify" to Verify(),
                "format" to Format(),
            ),
        )

    cli.run(args.toList())
}
