package cli.function

import ast.ProgramNode
import cli.util.generateAST
import interpreter.ProgramInterpreter
import lexer.Lexer
import lexer.getTokenMap
import logger.ConsoleLogger
import logger.Logger
import parser.Parser
import parser.factory.ProgramParserFactory

class Interpret(
    private val lexer: Lexer = Lexer(getTokenMap()),
    private val parser: Parser = ProgramParserFactory.create(),
    private val logger: Logger = ConsoleLogger(),
) : CLIFunction {
    override fun run(args: Map<String, String>) {
        val ast = generateAST(lexer, parser, args)

        ProgramInterpreter(ast as ProgramNode, logger).interpret()
    }
}
