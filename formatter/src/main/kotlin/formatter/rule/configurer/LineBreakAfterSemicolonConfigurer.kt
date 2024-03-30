package formatter.rule.configurer

import formatter.rule.LineBreakAfterSemicolonRule
import formatter.rule.Rule
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

class LineBreakAfterSemicolonConfigurer : RuleConfigurer {
    private val ruleConfigName = "lineBreakAfterSemicolon"

    override fun getRule(json: JsonObject): Rule {
        if (json.containsKey(ruleConfigName)) {
            val hasLineBreakAfterSemicolon = json.getValue(ruleConfigName).jsonPrimitive.content.toBoolean()
            return LineBreakAfterSemicolonRule(hasLineBreakAfterSemicolon)
        } else {
            throw IllegalArgumentException("Rule config not found")
        }
    }
}
