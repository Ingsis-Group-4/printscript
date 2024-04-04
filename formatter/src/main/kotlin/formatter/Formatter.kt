package formatter

import ast.AST
import ast.ProgramNode
import formatter.stringifier.Stringifier


// Not sure if the best way to pass the rule processor and stringifier is through the constructor, maybe using the init block would be better
class Formatter(private val ruleProcessor: AstRuleProcessor, private val stringifier: Stringifier) {
    fun format(node: AST): String {
        val formattedAst = ruleProcessor.format(node as ProgramNode)
        return stringifier.stringify(formattedAst)
    }
}
