package formatter

import ast.AST

class AssignationNodeFormatter : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
    ): String {
        val assignationNode = node as ast.AssignationNode
        val tokens =
            mutableListOf<String>(
                assignationNode.identifier.variableName,
                "=",
                getExpression(assignationNode.expression, rule),
            )
        if (rule.spaceBetweenEqualSign) {
            tokens[0] = addWhiteSpace(tokens[0])
            tokens[1] = addWhiteSpace(tokens[1])
        }

        val resultString = tokens.joinToString(separator = "")
        return resultString
    }
}
