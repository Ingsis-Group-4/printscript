package interpreter

import ast.AST
import interpreter.readInputFunction.ReadInputFunction
import kotlin.reflect.KClass

class StatementInterpreter(
    private val handlers: Map<KClass<out AST>, Interpreter>,
) : Interpreter {
    override fun interpret(
        ast: AST,
        environment: Environment,
        inputHandler: ReadInputFunction,
    ): InterpretOutput {
        val handler =
            handlers.getOrElse(ast::class) {
                throw Exception(
                    "Unknown statement at (line: ${ast.getStart().line} column: ${ast.getStart().column})",
                )
            }

        return handler.interpret(ast, environment, inputHandler)
    }
}
