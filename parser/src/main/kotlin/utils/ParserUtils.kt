package org.example.parser.utils

import ast.ExpressionNode
import ast.IdentifierNode
import org.example.parser.Parser
import org.example.parser.result.FailureResult
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
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

fun prev(tokens: List<Token>, index: Int): Token {
    return tokens[index - 1]
}

/**
 * Retrieves the syntax subtree parsed by the appropriate parser selected from `parserSelector` based on the type of the given token.
 * @param token Token for which to retrieve the syntax subtree.
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
 * Parses the syntax subtree for an assignment statement.
 * @param tokens List of tokens.
 * @param currentIndex Current index in the list of tokens.
 * @param identifierNode Identifier node representing the variable being assigned.
 * @param buildParserResult Function to build the parser result.
 * @param parserSelector Map of token types to parser instances.
 * @return The parser result representing the syntax subtree for the assignment statement.
 */
fun parseAssignationSyntax(
    tokens: List<Token>,
    currentIndex: Int,
    identifierNode: IdentifierNode,
    buildParserResult: (IdentifierNode, ExpressionNode, Int) -> ParserResult,
    parserSelector: Map<TokenType, Parser>
): ParserResult {
    // Retrieve the syntax subtree parsed by the appropriate parser based on the type of the given token
    return when (val syntaxSubtreeResult = getSyntaxSubtree(tokens, currentIndex, parserSelector)) {
        is SuccessResult -> {
            // Calculate the index of the semicolon following the syntax subtree
            val semicolonIndex = nextIndex(syntaxSubtreeResult.lastValidatedIndex)
            // Check if the semicolon is present at the expected position
            if (!isEndOfStatement(tokens, semicolonIndex)) {
                return FailureResult("Expected a semicolon at position $semicolonIndex", semicolonIndex)
            }
            // Build the parser result using the provided function
            buildParserResult(identifierNode, syntaxSubtreeResult.value as ExpressionNode, semicolonIndex)
        }

        is FailureResult -> syntaxSubtreeResult
    }
}

