package formatter.rule.configurer

import formatter.rule.Rule
import formatter.rule.SpaceBeforeEqualSignRule
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

class SpaceBeforeEqualSignConfigurer : RuleConfigurer {
    private val ruleConfigName = "spaceBeforeEquals"

    override fun getRule(json: JsonObject): Rule {
        if (json.containsKey(ruleConfigName)) {
            val hasSpaceBeforeEquals = json.getValue(ruleConfigName).jsonPrimitive.content.toBoolean()
            return SpaceBeforeEqualSignRule(hasSpaceBeforeEquals)
        } else {
            throw IllegalArgumentException("Rule config not found")
        }
    }
}
