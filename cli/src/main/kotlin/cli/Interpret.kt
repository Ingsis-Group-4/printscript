package cli

import ast.ProgramNode
import org.example.cli.functions.CLIFunction
import org.example.interpreter.ProgramInterpreter
import org.example.lexer.Lexer
import org.example.lexer.getTokenMap
import org.example.parser.ProgramParser
import org.example.parser.factory.ProgramParserFactory
import org.example.parser.result.FailureResult
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import java.io.File

class Interpret : CLIFunction {
    override fun run(args: List<String>) {
        if (!hasEnoughArguments(args)) {
            TODO("Error case not yet implemented")
        }

        val filePath = getFilePath(args)
        val program = File(filePath).readText()


        val lexer = Lexer(getTokenMap())
        val parser = ProgramParserFactory.create()

        val tokens = lexer.lex(program)

        val parserResult = parser.parse(tokens, 0)

        when(parserResult) {
            is SuccessResult -> {
                ProgramInterpreter(parserResult.value as ProgramNode).interpret()
            }
            is FailureResult -> {
                println("Error: ${parserResult.message}")
            }
        }

    }

    private fun hasEnoughArguments(args: List<String>) = args.size >= 2

    private fun getFilePath(args: List<String>): String {
        val index = 0
        while (index < args.size) {
            if (argumentIsFileFlag(args[index])) {
                return args[index + 1]
            }
        }
        return ""
    }

    private fun argumentIsFileFlag(arg: String): Boolean {
        return arg.startsWith("-f")
    }
}
