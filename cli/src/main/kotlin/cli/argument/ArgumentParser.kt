package cli.argument

interface ArgumentParser {
    fun parse(args: List<String>): Map<String, String>
}

class DefaultArgumentParser : ArgumentParser {
    override fun parse(args: List<String>): Map<String, String> {
        val parsedArguments = mutableMapOf<String, String>()
        var index = 0

        while (index < args.size) {
            val currentArgument = args[index]

            if (isValidFlag(currentArgument)) {
                if (isFlagWithArgument(currentArgument)) {
                    parsedArguments[currentArgument] = args[index + 1]
                    index += 2
                    continue
                } else {
                    parsedArguments[currentArgument] = ""
                    index++
                    continue
                }
            }
            index++
        }

        return parsedArguments
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
