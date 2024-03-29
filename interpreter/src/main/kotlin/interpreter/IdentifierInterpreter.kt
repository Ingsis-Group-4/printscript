package interpreter

import ast.IdentifierNode

class IdentifierInterpreter(private val node: IdentifierNode, private val environment: Environment) : Interpreter {
    override fun interpret(): Value {
        return environment.getVariable(node.variableName)
    }
}
