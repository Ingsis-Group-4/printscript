package formatter.rule.configurer

import formatter.rule.LineBreakBeforePrintLnRule
import formatter.rule.Rule
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

class LineBreakBeforePrintLnConfigurer : RuleConfigurer {
    private val ruleConfigName = "lineBreakBeforePrintLn"

    override fun getRule(json: JsonObject): Rule {
        if (json.containsKey(ruleConfigName)) {
            val lineBreaksAfterPrintLn = json.getValue(ruleConfigName).jsonPrimitive.content.toInt()
            return LineBreakBeforePrintLnRule(lineBreaksAfterPrintLn)
        } else {
            throw IllegalArgumentException("Rule config not found")
        }
    }
}
