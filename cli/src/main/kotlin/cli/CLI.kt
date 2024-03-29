package cli

import cli.argument.ArgumentParser
import cli.argument.DefaultArgumentParser
import cli.function.CLIFunction

/**
 * Command line interface that executes a function based on the command provided.
 *
 * @property functions Map of <command, CLIFunction> pairs to execute based on the command provided
 * @property argumentParser An object to parse command line arguments.
 */
class CLI(
    private val functions: Map<String, CLIFunction>,
    private val argumentParser: ArgumentParser = DefaultArgumentParser(),
) {
    /**
     * Runs the command line interface with the given arguments.
     * The first argument must be the command to run.
     *
     * @param args The arguments to run the command line interface with.
     */
    fun run(args: List<String>) {
        // Check if a command was provided, if not throw an exception.
        if (args.isEmpty()) {
            throw IllegalArgumentException("No command provided")
        }

        // Get the command and check if it exists in the functions map. If not, throw an exception.
        val command = args[0]
        val function =
            functions.getOrElse(command) {
                throw IllegalArgumentException("Command $command not found")
            }

        // Get the rest of the arguments after the command.
        val arguments = getNextArguments(args)

        // Parse the arguments into a map.
        val parsedArguments = argumentParser.parse(arguments)

        // Run the function with the parsed arguments.
        function.run(parsedArguments)
    }

    /**
     * Gets the arguments after the command.
     */
    private fun getNextArguments(args: List<String>): List<String> {
        return args.drop(1)
    }
}
