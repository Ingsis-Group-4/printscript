package sca.configurer

import kotlinx.serialization.json.JsonObject
import sca.rule.Rule

/**
 * Each rule provider is responsible for creating a rule from a JSON configuration object
 * */
interface RuleConfigurer {
    /**
     * Get the rule from the JSON configuration object
     *
     * @throws IllegalArgumentException if the rule config is not found
     * */
    fun getRule(json: JsonObject): Rule
}
