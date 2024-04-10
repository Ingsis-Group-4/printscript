package formatter.factory

import formatter.AssignationNodeFormatter
import formatter.DeclarationNodeFormatter
import formatter.Formatter
import formatter.PrintLnNodeFormatter
import formatter.ProgramNodeFormatter

class FormatterFactory {
    fun createFormatter(): Formatter {
        val keywordMap =
            mapOf(
                ast.LetNode::class to "let",
            )
        val astToFormatterMap =
            mapOf(
                ast.AssignationNode::class to AssignationNodeFormatter(),
                ast.DeclarationNode::class to DeclarationNodeFormatter(keywordMap),
                ast.PrintLnNode::class to PrintLnNodeFormatter(),
            )
        return ProgramNodeFormatter(astToFormatterMap)
    }
}
