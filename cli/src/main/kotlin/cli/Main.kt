package cli

import cli.function.Analyze
import cli.function.Interpret
import logger.ConsoleLogger

fun main(args: Array<String>) {
    val cli =
        CLI(
            mapOf(
                "interpret" to Interpret(logger = ConsoleLogger()),
                "analyze" to Analyze(),
            ),
        )

    cli.run(args.toList())
}
