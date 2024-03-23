package org.example.provider

import org.example.rule.Rule
import kotlinx.serialization.json.JsonObject

/**
 * Each rule provider is responsible for creating a rule from a JSON configuration object
 * */
interface RuleProvider{

    /**
     * Get the rule from the JSON configuration object
     *
     * @throws IllegalArgumentException if the rule config is not found
     * */
    fun getRule(json: JsonObject): Rule
}