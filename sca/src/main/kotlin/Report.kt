package org.example

import org.example.rule.RuleFailure
import org.example.rule.RuleFailures
import org.example.rule.RuleResult
import org.example.rule.RuleSuccess
import position.Position

class Report(
    val ruleResults: List<RuleResult>
){
    fun print(){
        if (ruleResults.isEmpty()){
            println("Static code analysis successful!")
            return
        }
        for (ruleResult in ruleResults) {
            when(ruleResult){
                is RuleSuccess -> {}
                is RuleFailure -> printRuleFailure(ruleResult)
                is RuleFailures -> printFailures(ruleResult)
            }
        }
    }

    private fun positionToString(position: Position): String{
        return "(${position.line}, column ${position.column})"
    }

    private fun printFailures(failuresResult: RuleFailures){
        failuresResult.failures.forEach(::printRuleFailure)
    }

    private fun printRuleFailure(failure: RuleFailure){
        println("Rule failed at ${positionToString(failure.position)}: ${failure.message}")
    }
}