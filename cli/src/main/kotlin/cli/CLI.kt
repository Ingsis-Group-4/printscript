package cli

import cli.argument.ArgumentParser
import cli.argument.DefaultArgumentParser
import cli.function.CLIFunction

class CLI(
    private val functions: Map<String, CLIFunction>,
    private val argumentParser: ArgumentParser = DefaultArgumentParser(),
) {
    fun run(args: List<String>) {
        if (args.isEmpty()) {
            throw IllegalArgumentException("No command provided")
        }

        val command = args[0]
        val function =
            functions.getOrElse(command) {
                throw IllegalArgumentException("Command $command not found")
            }

        val arguments = getNextArguments(args)

        val parsedArguments = argumentParser.parse(arguments)

        function.run(parsedArguments)
    }

    private fun getNextArguments(args: List<String>): List<String> {
        return args.drop(1)
    }
}
