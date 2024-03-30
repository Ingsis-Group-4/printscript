package formatter.rule.configurer

import formatter.rule.Rule
import formatter.rule.SpaceBeforeColonRule
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

class SpaceBeforeColonConfigurer : RuleConfigurer {
    private val ruleConfigName = "spaceBeforeColon"

    override fun getRule(json: JsonObject): Rule {
        if (json.containsKey(ruleConfigName)) {
            val hasSpaceBeforeColon = json.getValue(ruleConfigName).jsonPrimitive.content.toBoolean()
            return SpaceBeforeColonRule(hasSpaceBeforeColon)
        } else {
            throw IllegalArgumentException("Rule config not found")
        }
    }
}
