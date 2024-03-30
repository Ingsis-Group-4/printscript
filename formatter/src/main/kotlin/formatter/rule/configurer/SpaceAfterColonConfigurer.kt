package formatter.rule.configurer

import formatter.rule.Rule
import formatter.rule.SpaceAfterColonRule
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

class SpaceAfterColonConfigurer : RuleConfigurer {
    private val ruleConfigName = "spaceAfterColon"

    override fun getRule(json: JsonObject): Rule {
        if (json.containsKey(ruleConfigName)) {
            val hasSpaceAfterColon = json.getValue(ruleConfigName).jsonPrimitive.content.toBoolean()
            return SpaceAfterColonRule(hasSpaceAfterColon)
        } else {
            throw IllegalArgumentException("Rule config not found")
        }
    }
}
