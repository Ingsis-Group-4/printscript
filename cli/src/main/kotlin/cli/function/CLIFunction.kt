package cli.function

/**
 * Represents a command line interface function.
 */
interface CLIFunction {
    /**
     * Runs the command line interface function with the given arguments.
     *
     * @param args The arguments to run the function with.
     */
    fun run(args: Map<String, String>)
}
