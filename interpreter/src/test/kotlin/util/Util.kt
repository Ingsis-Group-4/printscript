package util

import ast.AST
import lexer.Lexer
import lexer.getTokenMap
import parser.Parser
import parser.factory.StatementParserFactory
import parser.result.FailureResult
import parser.result.SuccessResult

fun getAstFromString(
    input: String,
    lexer: Lexer = Lexer(getTokenMap()),
    parser: Parser = StatementParserFactory.create(),
): AST {
    when (val result = parser.parse(lexer.lex(input))) {
        is SuccessResult -> return result.value
        is FailureResult -> throw Exception("Parse error: ${result.message}")
    }
}
