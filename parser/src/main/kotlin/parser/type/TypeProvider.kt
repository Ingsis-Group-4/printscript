package parser.type

import ast.VariableType
import token.TokenType

/**
 * Interface that defines a type provider for syntactic analysis.
 * A type provider is responsible for mapping variable types to specific token types.
 */
interface TypeProvider {
    /**
     * Gets the variable type associated with a specific token type.
     * @param tokenType The token type for which to retrieve the variable type.
     * @return The variable type associated with the token type, or null if there is no match.
     */
    fun getType(tokenType: TokenType): VariableType?
}

/**
 * Default implementation of TypeProvider that assigns variable types to known token types.
 * This implementation handles predefined token types such as TokenType.NUMBERTYPE and TokenType.STRINGTYPE.
 * You can add more cases as needed to handle additional token types.
 */
object DefaultTypeProvider : TypeProvider {
    override fun getType(tokenType: TokenType): VariableType? {
        return when (tokenType) {
            TokenType.NUMBERTYPE -> VariableType.NUMBER
            TokenType.STRINGTYPE -> VariableType.STRING
            else -> null
        }
    }
}
