package cli.argument

/**
 * Parses command line arguments into a map of flag-value pairs.
 */
interface ArgumentParser {
    fun parse(args: List<String>): Map<String, String>
}
