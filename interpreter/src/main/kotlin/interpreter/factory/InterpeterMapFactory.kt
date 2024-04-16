package interpreter.factory

import ast.AST
import ast.AssignationNode
import ast.DeclarationNode
import ast.FunctionStatementNode
import interpreter.FunctionStatementInterpreter
import interpreter.Interpreter
import interpreter.VariableStatementInterpreter
import kotlin.reflect.KClass

fun getInterpreterMap(): Map<KClass<out AST>, Interpreter> {
    return mapOf(
        FunctionStatementNode::class to FunctionStatementInterpreter(),
        DeclarationNode::class to VariableStatementInterpreter(),
        AssignationNode::class to VariableStatementInterpreter(),
    )
}
