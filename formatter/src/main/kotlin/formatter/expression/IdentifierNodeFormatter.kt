package formatter.expression

import ast.AST
import formatter.Formatter
import formatter.rule.FormattingRule
import kotlin.reflect.KClass

class IdentifierNodeFormatter : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String {
        val identifierNode = node as ast.IdentifierNode
        return identifierNode.variableName
    }
}
