package token

import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.String

class TokenMatcher {
    private val pattern: Pattern

    constructor(type: TokenType, regex: String) {
        this.pattern = Pattern.compile(String.format("(?<%s>%s)", type, regex))
    }

    constructor(matchers: List<TokenMatcher>) {
        val regex = matchers.joinToString("|") { it.pattern.toString() }
        this.pattern = Pattern.compile(regex)
    }

    fun getMatcher(input: String): Matcher {
        return pattern.matcher(input)
    }
}
