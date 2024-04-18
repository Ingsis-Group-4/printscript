package formatter.statement

import ast.AST
import formatter.Formatter
import formatter.rule.FormattingRule
import formatter.utils.formatNextNode
import kotlin.reflect.KClass

class AssignationNodeFormatter : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String {
        val assignationNode = node as ast.AssignationNode
        return buildString {
            append(assignationNode.identifier.variableName)
            append(if (rule.hasSpaceBetweenEqualSign) " = " else "=")
            append(
                formatNextNode(
                    formatterMap,
                    assignationNode.expression,
                    rule,
                ),
            )
            append(";")
        }
    }
}
