package sca.configurer

import kotlinx.serialization.json.JsonObject
import sca.rule.PrintLnArgumentRule
import sca.rule.Rule

/**
 * Rule provider for PrintLnArgumentNonExpressionRule
 * */
class PrintLnArgumentNonExpressionRuleConfigurer : RuleConfigurer {
    private val ruleConfigName = "printLnArgumentNonExpressionRule"

    /**
     * Checks if config contains the rule config name and returns the rule
     * */
    override fun getRule(json: JsonObject): Rule {
        if (json.containsKey(ruleConfigName)) {
            return PrintLnArgumentRule()
        } else {
            throw IllegalArgumentException("Rule config not found")
        }
    }
}
