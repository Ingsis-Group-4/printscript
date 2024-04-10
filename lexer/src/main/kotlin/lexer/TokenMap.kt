package lexer

import token.TokenMatcher
import token.TokenType
import version.Version
import java.util.EnumMap

fun getTokenMap(versionNumber: Version = Version.V1): EnumMap<TokenType, TokenMatcher> {
    val tokenMap: EnumMap<TokenType, TokenMatcher> = EnumMap(TokenType::class.java)

    // KEYWORDS
    tokenMap[TokenType.LET] = TokenMatcher(TokenType.LET, "\\blet\\b")
    tokenMap[TokenType.PRINTLN] = TokenMatcher(TokenType.PRINTLN, "\\bprintln\\b")

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

    // UNKNOWN
    tokenMap[TokenType.UNKNOWN] = TokenMatcher(TokenType.UNKNOWN, "[^ \\n]")

    when (versionNumber) {
        Version.V1 -> {
            return tokenMap
        }
        Version.V2 -> {
            // KEYWORDS
            tokenMap[TokenType.CONST] = TokenMatcher(TokenType.CONST, "\\bconst\\b")
            tokenMap[TokenType.BOOLEANTYPE] = TokenMatcher(TokenType.BOOLEANTYPE, "Boolean")
            tokenMap[TokenType.IF] = TokenMatcher(TokenType.IF, "\\bif\\b")
            tokenMap[TokenType.ELSE] = TokenMatcher(TokenType.ELSE, "\\belse\\b")
            tokenMap[TokenType.READINPUT] = TokenMatcher(TokenType.READINPUT, "\\breadInput\\b")

            // TYPES
            tokenMap[TokenType.BOOLEAN] = TokenMatcher(TokenType.BOOLEAN, "(?:true|false)")

            // OPERATORS
            tokenMap[TokenType.OPENCURLY] = TokenMatcher(TokenType.OPENCURLY, "\\{")
            tokenMap[TokenType.CLOSECURLY] = TokenMatcher(TokenType.CLOSECURLY, "\\}")

            return tokenMap
        }
    }
}
