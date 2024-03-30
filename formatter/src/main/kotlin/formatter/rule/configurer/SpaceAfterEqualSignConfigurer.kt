package formatter.rule.configurer

import formatter.rule.Rule
import formatter.rule.SpaceAfterEqualSignRule
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

class SpaceAfterEqualSignConfigurer : RuleConfigurer {
    private val ruleConfigName = "spaceAfterEquals"

    override fun getRule(json: JsonObject): Rule {
        if (json.containsKey(ruleConfigName)) {
            val hasSpaceAfterEquals = json.getValue(ruleConfigName).jsonPrimitive.content.toBoolean()
            return SpaceAfterEqualSignRule(hasSpaceAfterEquals)
        } else {
            throw IllegalArgumentException("Rule config not found")
        }
    }
}
