package formatter

import ast.AST
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
                formatterMap[assignationNode.expression::class]?.format(assignationNode.expression, rule, formatterMap)
                    ?: throw IllegalArgumentException("No formatter found for ${assignationNode.expression::class}"),
            )
        }
    }
}
