package sca.rule

import ast.AST
import ast.AssignationNode
import ast.DeclarationNode
import ast.FunctionStatementNode
import ast.IdentifierNode
import ast.LiteralNode
import ast.PrintLnNode
import ast.ProgramNode
import ast.StatementNode
import sca.checkProgramNode

/**
 * Rule that checks if a println call has been made with an argument that is not a literal or a variable
 * */
class PrintLnArgumentRule : Rule {
    override fun check(ast: AST): RuleResult {
        return when (ast) {
            is ProgramNode -> checkProgramNode(ast, checkStatementNode = ::checkStatementNode)
            is StatementNode -> checkStatementNode(ast)
            else -> RuleSuccess
        }
    }

    /**
     * Check if the statement follows the println rule
     *
     * - If the statement is an AssignationNode or VariableDeclarationNode, RuleSuccess is returned
     * - If the statement is a PrintLnNode, check if argument is valid
     * */
    private fun checkStatementNode(ast: StatementNode): RuleResult {
        return when (ast) {
            is AssignationNode -> RuleSuccess
            is DeclarationNode -> RuleSuccess
            is FunctionStatementNode -> checkFunctionStatementNode(ast)
            else -> RuleFailures(emptyList())
        }
    }

    private fun checkFunctionStatementNode(node: FunctionStatementNode): RuleResult {
        return when (val functionNode = node.getFunctionNode()) {
            is PrintLnNode -> checkPrintlnExpression(functionNode)
            else -> RuleFailures(emptyList())
        }
    }

    /**
     * Check if println expression
     *
     * - If it is a LiteralNode or IdentifierNode, RuleSuccess is returned
     * - If it is any other Node, return failure.
     * */
    private fun checkPrintlnExpression(ast: PrintLnNode): RuleResult {
        return when (val expression = ast.getExpression()) {
            is LiteralNode<*> -> RuleSuccess
            is IdentifierNode -> RuleSuccess
            else ->
                RuleFailures(
                    listOf(
                        FailurePayload(
                            "PrintLn argument does not follow argument rule",
                            expression.getStart(),
                        ),
                    ),
                )
        }
    }
}
