package cli.function

interface CLIFunction {
    fun run(args: Map<String, String>)
}
