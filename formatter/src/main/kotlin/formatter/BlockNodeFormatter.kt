package formatter

import ast.AST
import ast.StatementNode
import formatter.utils.formatNextNode
import kotlin.reflect.KClass

class BlockNodeFormatter : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String {
        val blockNode = node as ast.BlockNode
        val statements = blockNode.getStatements()
        return formatStatements(statements, rule, formatterMap)
    }

    private fun formatStatements(
        statements: List<StatementNode>,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ) = statements.joinToString("\n") { statement ->
        val indent = " ".repeat(rule.ifBlockIndent)
        val formattedStatement = formatNextNode(formatterMap, statement, rule)
        identEachLine(formattedStatement, indent)
    }

    private fun identEachLine(
        formattedStatement: String,
        indent: String,
    ) = formattedStatement.split("\n").joinToString("\n") { line ->
        if (line.isBlank()) line else "$indent$line"
    }
}
