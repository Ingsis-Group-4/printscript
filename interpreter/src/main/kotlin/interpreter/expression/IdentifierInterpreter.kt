package interpreter.expression

import ast.IdentifierNode
import interpreter.Environment
import interpreter.Value

class IdentifierInterpreter {
    fun interpret(
        node: IdentifierNode,
        environment: Environment,
    ): Value {
        return environment.getVariable(node.variableName)
    }
}
