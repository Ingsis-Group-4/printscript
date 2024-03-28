package cli.util

import ast.AST
import org.example.lexer.Lexer
import org.example.parser.Parser
import org.example.parser.result.FailureResult
import org.example.parser.result.SuccessResult
import java.io.File

fun generateAST(
    lexer: Lexer,
    parser: Parser,
    args: Map<String, String>,
): AST {
    val filePath = getFilePath(args)
    val file = File(filePath).readText()

    val tokens = lexer.lex(file)

    when (val parserResult = parser.parse(tokens, 0)) {
        is SuccessResult -> {
            return parserResult.value as AST
        }
        is FailureResult -> {
            throw IllegalArgumentException("Error: ${parserResult.message}")
        }
    }
}

private fun getFilePath(args: Map<String, String>): String {
    return if (args.containsKey("-f")) {
        args["-f"]!!
    } else {
        throw IllegalArgumentException("No file path provided")
    }
}
