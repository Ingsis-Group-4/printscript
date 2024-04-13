package cli.util

import ast.AST
import lexer.Lexer
import lexer.LineLexer
import parser.Parser
import parser.result.FailureResult
import parser.result.SuccessResult
import token.Token
import java.io.File

/**
 * Function to generate the AST from the given source code.
 *
 * @param lexer The lexer to use.
 * @param parser The parser to use.
 * @param args The arguments to run the function with.
 * @return The AST generated from the source code.
 */
fun generateAST(
    lexer: Lexer,
    parser: Parser,
    args: Map<String, String>,
): AST {
    val file = File(getFilePath(args)).readText()
    val tokens = lexer.lex(file)
    return parseTokens(parser, tokens)
}

fun generateBufferedAST(
    lexer: LineLexer,
    parser: Parser,
    args: Map<String, String>,
): AST {
    val file = File(getFilePath(args)).readText()
    val tokens = lexer.lex(file, 0)
    return parseTokens(parser, tokens)
}

/**
 * Gets the file path from the command line arguments.
 */
fun getFilePath(args: Map<String, String>): String {
    return args["-f"] ?: throw IllegalArgumentException("No program file path provided")
}

private fun parseTokens(
    parser: Parser,
    tokens: List<Token>,
): AST {
// TODO: Add the exact position, not the index of the last token
    return when (val parserResult = parser.parse(tokens, 0)) {
        is SuccessResult -> parserResult.value
        is FailureResult -> throw IllegalArgumentException("Parsing Error: ${parserResult.message}")
    }
}
