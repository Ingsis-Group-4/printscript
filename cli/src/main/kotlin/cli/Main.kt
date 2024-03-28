package cli

import cli.function.Analyze
import cli.function.Interpret

fun main(args: Array<String>) {
    val cli =
        CLI(
            mapOf(
                "interpret" to Interpret(),
                "analyze" to Analyze(),
            ),
        )

    cli.run(args.toList())
}
