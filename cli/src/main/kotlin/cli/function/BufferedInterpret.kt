package cli.function

import cli.util.generateBufferedAST
import interpreter.StatementInterpreter
import interpreter.factory.getInterpreterMap
import lexer.LineLexer
import lexer.getTokenMap
import logger.ConsoleLogger
import logger.Logger
import parser.Parser
import parser.factory.StatementParserFactory

/**
 * Interprets the given source code by statement.
 *
 * @param lexer The lexer to use.
 * @param parser The parser to use.
 * @param logger The logger to use.
 */

class BufferedInterpret(
    private val lexer: LineLexer = LineLexer(getTokenMap()),
    private val parser: Parser = StatementParserFactory.create(),
    private val logger: Logger = ConsoleLogger(),
) : CLIFunction {
    override fun run(args: Map<String, String>) {
        val ast = generateBufferedAST(lexer, parser, args)

        val result = StatementInterpreter(getInterpreterMap()).interpret(ast)

        for (log in result.logs) {
            logger.log(log)
        }
    }
}
