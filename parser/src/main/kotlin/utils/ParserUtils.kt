package org.example.parser.utils

import ast.ExpressionNode
import ast.IdentifierNode
import org.example.parser.Parser
import org.example.parser.result.FailureResult
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import position.Position
import token.Token
import token.TokenType

/**
 * Returns the token in the list `tokens` at the next index after `index`.
 * @param tokens List of tokens.
 * @param index Index of the current token.
 * @return The token at the next index after `index`.
 */
fun next(tokens: List<Token>, index: Int): Token {
    return tokens[index + 1]
}

/**
 * Returns the token in the list `tokens` at the specified index.
 * @param tokens List of tokens.
 * @param index Index of the token to retrieve.
 * @return The token at the specified index.
 */
fun at(tokens: List<Token>, index: Int): Token {
    return tokens[index]
}

/**
 * Retrieves the syntax subtree parsed by the appropriate parser selected from `parserSelector` based on the type of the given token.
 * @param tokens List of tokens.
 * @param currentIndex Current index in the list of tokens.
 * @param parserSelector Map of token types to parser instances.
 * @return The parser result representing the syntax subtree.
 */
fun getSyntaxSubtree(
    tokens: List<Token>,
    currentIndex: Int,
    parserSelector: Map<TokenType, Parser>
): ParserResult {
    val token = at(tokens, currentIndex)
    if (parserSelector.containsKey(token.type))
        return parserSelector[token.type]?.parse(tokens, currentIndex)!!
    return FailureResult("Invalid token at position ${token.start}", currentIndex)
}

/**
 * Computes the index that follows the current index by a specified step.
 * @param currentIndex Current index.
 * @param step Number of positions to advance from the current index. Default is `1`.
 * @return The index that follows the current index by `step` positions.
 */
fun nextIndex(currentIndex: Int, step: Int = 1): Int {
    return currentIndex + step
}

/**
 * Computes the index that precedes the current index by a specified step.
 * @param currentIndex Current index.
 * @param step Number of positions to retreat from the current index. Default is `1`.
 * @return The index that precedes the current index by `step` positions.
 */
fun prevIndex(currentIndex: Int, step: Int = 1): Int {
    return currentIndex - step
}

/**
 * Checks if the token at the specified index indicates the end of a statement, typically a semicolon.
 * @param tokens List of tokens.
 * @param currentIndex Index of the token to check.
 * @return `true` if the token at `currentIndex` is a semicolon, indicating the end of a statement; otherwise, `false`.
 */
fun isEndOfStatement(tokens: List<Token>, currentIndex: Int): Boolean {
    return at(tokens, currentIndex).type == TokenType.SEMICOLON
}

/**
 * Checks if the specified token indicates the end of a statement, typically a semicolon.
 * @param token The token to check.
 * @return `true` if the token is a semicolon, indicating the end of a statement; otherwise, `false`.
 */
fun isEndOfStatement(token: Token): Boolean {
    return token.type == TokenType.SEMICOLON
}

/**
 * Checks if the token at the specified index in the list `tokens` matches the expected token type.
 * @param tokens List of tokens.
 * @param tokenIndex Index of the token to check.
 * @param expectedType Expected type of the token.
 * @return `true` if the token at `tokenIndex` has the expected type; otherwise, `false`.
 */
fun isTokenValid(tokens: List<Token>, tokenIndex: Int, expectedType: TokenType): Boolean {
    return at(tokens, tokenIndex).type == expectedType
}

/**
 * Parses an operation from the list of tokens.
 *
 * This function takes a list of tokens, the current index, a function to check if the right operator is valid,
 * a function to build the parser result, and a map of token types to parser instances.
 * It then parses the operation and returns the parser result.
 *
 * @param tokens List of tokens.
 * @param currentIndex Current index in the list of tokens.
 * @param isRightOperator Function to check if the right operator is valid.
 * @param buildParserResult Function to build the parser result.
 * @param parserSelector Map of token types to parser instances.
 * @return The parser result representing the parsed operation.
 */
fun parseOperation(
    tokens: List<Token>,
    currentIndex: Int,
    isRightOperator: (List<Token>, Int) -> Boolean,
    buildParserResult: (SuccessResult, SuccessResult, Int) -> ParserResult,
    parserSelector: Map<TokenType, Parser>
): ParserResult {
    if (!isRightOperator(tokens, currentIndex)) {
        return FailureResult("Invalid operation at position ${at(tokens, currentIndex).start}", currentIndex)
    }
    val leftOperandIndex = prevIndex(currentIndex)
    val leftOperand = getSyntaxSubtree(tokens, leftOperandIndex, parserSelector)
    val rightOperandIndex = nextIndex(currentIndex)
    val rightOperand = getSyntaxSubtree(tokens, rightOperandIndex, parserSelector)
    if (leftOperand is SuccessResult && rightOperand is SuccessResult) {
        return buildParserResult(leftOperand, rightOperand, rightOperand.lastValidatedIndex)
    }
    return FailureResult("Invalid operation at position ${at(tokens, currentIndex).start}", currentIndex)
}

