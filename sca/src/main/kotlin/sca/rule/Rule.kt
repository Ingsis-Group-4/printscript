package sca.rule

import ast.AST

interface Rule {
    /**
     * Checks if the rule is satisfied by the AST
     *
     * @param ast AST to be checked
     * @return RuleResult with the result of the check
     * */
    fun check(ast: AST): RuleResult
}
