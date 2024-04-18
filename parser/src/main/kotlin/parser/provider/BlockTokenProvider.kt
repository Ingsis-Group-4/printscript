package parser.provider

import token.TokenType

interface BlockTokenProvider {
    fun getNestedBlockTokens(): List<TokenType>
}

object BlockTokenProviderV2 : BlockTokenProvider {
    override fun getNestedBlockTokens(): List<TokenType> {
        return listOf(TokenType.ELSE)
    }
}

object BlockTokenProviderV1 : BlockTokenProvider {
    override fun getNestedBlockTokens(): List<TokenType> {
        return listOf()
    }
}
