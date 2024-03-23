package org.example.rule

import position.Position

sealed interface RuleResult

object RuleSuccess : RuleResult

class RuleFailures(val failures: List<RuleFailure>) : RuleResult

class RuleFailure(val message: String, val position: Position) : RuleResult