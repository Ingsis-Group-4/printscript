package org.example.provider

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.example.rule.Rule
import org.example.rule.VariableNamingRule

/**
 * Rule provider for VariableNamingRule
 * */
class VariableNamingRuleConfigurer : RuleConfigurer{
    private val ruleConfigName = "variableNamingRule"

    /**
     * Checks if config contains the rule config name and returns the rule with the regex pattern
     * */
    override fun getRule(json: JsonObject): Rule {
        if(json.containsKey(ruleConfigName)){
            val ruleConfig = json.getValue(ruleConfigName).jsonPrimitive.content
            return VariableNamingRule(ruleConfig)
        }else
            throw IllegalArgumentException("Rule config not found")
    }
}