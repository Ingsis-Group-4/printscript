package formatter.rule.factory

import formatter.rule.LineBreakAfterSemicolonRule
import formatter.rule.Rule
import formatter.rule.WhitespaceBeforeAndAfterOperatorRule
import formatter.rule.WhitespaceBetweenTokensRule

class FormatterRuleFactory {
    private val rules : List<Rule> = listOf(
        LineBreakAfterSemicolonRule(true),
        WhitespaceBetweenTokensRule(true),
        WhitespaceBeforeAndAfterOperatorRule(true),


    )
}
