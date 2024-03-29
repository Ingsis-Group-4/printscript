package formatter.rule.configurer

import formatter.rule.Rule
import kotlinx.serialization.json.JsonObject

interface RuleConfigurer {
    fun getRule(json: JsonObject): Rule
}
