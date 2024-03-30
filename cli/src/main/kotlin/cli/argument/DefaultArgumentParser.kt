package cli.argument

class DefaultArgumentParser : ArgumentParser {
    override fun parse(args: List<String>): Map<String, String> {
        val parsedArguments = mutableMapOf<String, String>()
        var index = 0

        while (index < args.size) {
            val currentArgument = args[index]

            if (isValidFlag(currentArgument)) {
                if (isFlagWithArgument(currentArgument)) {
                    if (flagHasNoArgument(args, index)) {
                        throw IllegalArgumentException("Flag $currentArgument requires an argument")
                    }
                    addFlagWithArgument(args, parsedArguments, index)
                    index += 2
                    continue
                } else {
                    addFlagWithoutArgument(parsedArguments, currentArgument)
                    index++
                    continue
                }
            }
            index++
        }

        return parsedArguments
    }

    private fun addFlagWithArgument(
        args: List<String>,
        parsedArguments: MutableMap<String, String>,
        index: Int,
    ) {
        val flag = args[index]
        val argument = args[index + 1]
        parsedArguments[flag] = argument
    }

    private fun addFlagWithoutArgument(
        parsedArguments: MutableMap<String, String>,
        currentArgument: String,
    ) {
        parsedArguments[currentArgument] = ""
    }

    private fun flagHasNoArgument(
        args: List<String>,
        index: Int,
    ): Boolean {
        return index + 1 >= args.size
    }

    private fun isValidFlag(flag: String): Boolean {
        return listOf(
            "-f",
            "-v",
            "-c",
        ).contains(flag)
    }

    private fun isFlagWithArgument(flag: String): Boolean {
        return listOf(
            "-f",
            "-v",
            "-c",
        ).contains(flag)
    }
}
