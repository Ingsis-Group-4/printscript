package cli.util

import ast.AST
import lexer.Lexer
import parser.Parser
import parser.result.FailureResult
import parser.result.SuccessResult
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
    // Get the file path from the command line arguments
    val filePath = getFilePath(args)
    // Read the file into a string
    val file = File(filePath).readText()

    // Lex the file
    val tokens = lexer.lex(file)

    // Parse the tokens and return the AST
    when (val parserResult = parser.parse(tokens, 0)) {
        is SuccessResult -> {
            return parserResult.value
        }
        is FailureResult -> {
            // TODO: Add the exact position, not the index of the last token
            throw IllegalArgumentException(
                "Parsing Error: ${parserResult.message}",
            )
        }
    }
}

/**
 * Gets the file path from the command line arguments.
 */
private fun getFilePath(args: Map<String, String>): String {
    return if (args.containsKey("-f")) {
        args["-f"]!!
    } else {
        throw IllegalArgumentException("No program file path provided")
    }
}
