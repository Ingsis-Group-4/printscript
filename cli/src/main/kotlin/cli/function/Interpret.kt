package cli.function

import ast.ProgramNode
import cli.util.generateAST
import org.example.cli.functions.CLIFunction
import org.example.interpreter.ProgramInterpreter
import org.example.lexer.Lexer
import org.example.lexer.getTokenMap
import org.example.parser.Parser
import org.example.parser.factory.ProgramParserFactory

class Interpret(
    private val lexer: Lexer = Lexer(getTokenMap()),
    private val parser: Parser = ProgramParserFactory.create(),
) : CLIFunction {
    override fun run(args: Map<String, String>) {
        val ast = generateAST(lexer, parser, args)

        ProgramInterpreter(ast as ProgramNode).interpret()
    }
}
