package cli

import org.example.cli.functions.CLI

fun main(args: Array<String>) {
    val cli =
        CLI(
            mapOf(
                "interpret" to Interpret(),
            ),
        )

    cli.run(args.toList())
}
