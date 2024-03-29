package lexer

import token.TokenMatcher
import token.TokenType
import java.util.EnumMap

fun getTokenMap(): EnumMap<TokenType, TokenMatcher> {
    val tokenMap: EnumMap<TokenType, TokenMatcher> = EnumMap(TokenType::class.java)

    // KEYWORDS
    tokenMap[TokenType.LET] = TokenMatcher(TokenType.LET, "let")
    tokenMap[TokenType.PRINTLN] = TokenMatcher(TokenType.PRINTLN, "println")

    // TYPES
    tokenMap[TokenType.STRINGTYPE] = TokenMatcher(TokenType.STRINGTYPE, "String")
    tokenMap[TokenType.NUMBERTYPE] = TokenMatcher(TokenType.NUMBERTYPE, "Number")

    // IDENTIFIERS
    tokenMap[TokenType.IDENTIFIER] = TokenMatcher(TokenType.IDENTIFIER, "[a-zA-Z_][a-zA-Z0-9_]*")

    // OPERATORS
    tokenMap[TokenType.SUM] = TokenMatcher(TokenType.SUM, "\\+")
    tokenMap[TokenType.SUBTRACTION] = TokenMatcher(TokenType.SUBTRACTION, "-")
    tokenMap[TokenType.MULTIPLICATION] = TokenMatcher(TokenType.MULTIPLICATION, "\\*")
    tokenMap[TokenType.DIVISION] = TokenMatcher(TokenType.DIVISION, "/")
    tokenMap[TokenType.ASSIGNATION] = TokenMatcher(TokenType.ASSIGNATION, "=")
    tokenMap[TokenType.SEMICOLON] = TokenMatcher(TokenType.SEMICOLON, ";")
    tokenMap[TokenType.COLON] = TokenMatcher(TokenType.COLON, ":")
    tokenMap[TokenType.OPENPARENTHESIS] = TokenMatcher(TokenType.OPENPARENTHESIS, "\\(")
    tokenMap[TokenType.CLOSEPARENTHESIS] = TokenMatcher(TokenType.CLOSEPARENTHESIS, "\\)")

    // LITERALS
    tokenMap[TokenType.STRING] = TokenMatcher(TokenType.STRING, "\'[^\']*\'|\"[^\"]*\"")
    tokenMap[TokenType.NUMBER] = TokenMatcher(TokenType.NUMBER, "[0-9]+")
    return tokenMap
}
