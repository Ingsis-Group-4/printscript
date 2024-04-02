package formatter.stringifier

import ast.AST
import ast.FunctionStatementNode
import ast.ProgramNode
import ast.VariableStatementNode

class ProgramNodeStringifier : Stringifier {
    override fun stringify(node: AST): String {
        val programNode = node as ProgramNode
        val stringifyStatements = mutableListOf<String>()
        for (statement in node.statements) {
            when (statement) {
                is VariableStatementNode -> stringifyStatements.add(VariableStatementNodeStringifier().stringify(statement))
                is FunctionStatementNode -> stringifyStatements.add(FunctionStatementNodeStringifier().stringify(statement))
            }
        }
        return stringifyStatements.joinToString(";\n")
    }
}
