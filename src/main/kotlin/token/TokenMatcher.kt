package org.example.token

import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.String

public class TokenMatcher {
   private val pattern:Pattern

    constructor(type: TokenType, regex:kotlin.String){
        this.pattern = Pattern.compile(String.format("(?<%s>%s)", type, regex))
    }
    constructor(matchers:List<TokenMatcher>){
        val regex = matchers.joinToString("|") { it.pattern.toString() }
        this.pattern = Pattern.compile(regex)
    }
    public fun getMatcher(input:String): Matcher {
        return pattern.matcher(input)
    }


}