package cli.argument

interface ArgumentParser {
    fun parse(args: List<String>): Map<String, String>
}
