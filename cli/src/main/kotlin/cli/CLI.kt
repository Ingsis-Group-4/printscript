package org.example.cli.functions

class CLI(
    private val functions: Map<String, CLIFunction>,
) {
    fun run(args: List<String>) {
        if (args.isEmpty()) {
            TODO("Error case not yet implemented")
        }

        val command = args[0]
        val function =
            functions.getOrElse(command) {
                TODO("Error case not yet implemented")
            }

        function.run(getNextArguments(args))
    }

    private fun getNextArguments(args: List<String>): List<String> {
        return args.drop(1)
    }
}
