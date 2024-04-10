package cli.function

import cli.util.generateAST
import interpreter.ProgramInterpreter
import lexer.Lexer
import lexer.getTokenMap
import logger.ConsoleLogger
import logger.Logger
import parser.Parser
import parser.factory.ProgramParserFactory

/**
 * Interprets the given source code.
 *
 * @param lexer The lexer to use.
 * @param parser The parser to use.
 * @param logger The logger to use.
 */
class Interpret(
    private val lexer: Lexer = Lexer(getTokenMap()),
    private val parser: Parser = ProgramParserFactory.create(),
    private val logger: Logger = ConsoleLogger(),
) : CLIFunction {
    override fun run(args: Map<String, String>) {
        // Generate the AST from the source code
        val ast = generateAST(lexer, parser, args)

        // Interpret the AST
        val result = ProgramInterpreter().interpret(ast)
        for (log in result.logs) {
            logger.log(log)
        }
    }
}
