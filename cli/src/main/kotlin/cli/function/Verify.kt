package cli.function

import cli.util.generateAST
import lexer.Lexer
import lexer.getTokenMap
import logger.ConsoleLogger
import logger.Logger
import parser.Parser
import parser.factory.ProgramParserFactory

/**
 * Builds the AST and throws error if the result is failure. Otherwise, logs the success message.
 *
 * @param lexer The lexer to use.
 * @param parser The parser to use.
 */
class Verify(
    private val lexer: Lexer = Lexer(getTokenMap()),
    private val parser: Parser = ProgramParserFactory.create(),
    private val logger: Logger = ConsoleLogger(),
) : CLIFunction {
    override fun run(args: Map<String, String>) {
        generateAST(lexer, parser, args)

        logger.log("Verification successful!")
    }
}
