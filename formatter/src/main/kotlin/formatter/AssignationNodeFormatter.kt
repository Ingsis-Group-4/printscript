package formatter

import ast.AST

class AssignationNodeFormatter : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
    ): String {
        val assignationNode = node as ast.AssignationNode
        return buildString {
            append(assignationNode.identifier.variableName)
            append(if (rule.spaceBetweenEqualSign) " = " else "=")
            append(getExpression(assignationNode.expression, rule))
        }
    }
}
