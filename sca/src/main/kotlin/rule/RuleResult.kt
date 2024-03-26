package org.example.rule

import position.Position

sealed interface RuleResult

object RuleSuccess : RuleResult

class RuleFailures(val failures: List<FailurePayload>) : RuleResult

class FailurePayload(val message: String, val position: Position)