package cli.function

import cli.util.generateAST
import cli.util.getVersion
import lexer.Lexer
import lexer.getTokenMap
import logger.ConsoleLogger
import logger.Logger
import parser.factory.ProgramParserFactory

/**
 * Builds the AST and throws error if the result is failure. Otherwise, logs the success message.
 *
 * @param lexer The lexer to use.
 * @param parser The parser to use.
 */
class Verify(
    private val logger: Logger = ConsoleLogger(),
) : CLIFunction {
    override fun run(args: Map<String, String>) {
        val version = getVersion(args)

        val lexer = Lexer(getTokenMap(version))
        val parser = ProgramParserFactory.create(version)

        generateAST(lexer, parser, args)

        logger.log("Verification successful!")
    }
}
