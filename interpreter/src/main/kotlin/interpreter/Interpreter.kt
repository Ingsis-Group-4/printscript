package interpreter

import ast.AST
import interpreter.readInputFunction.ReadInputFunction
import interpreter.readInputFunction.StandardInputFunction

interface Interpreter {
    /**
     * Interprets given ast. If ast is not of supported type for that interpreter, throws exception
     *
     * @param ast AST to interpret.
     * @param environment Initial environment to be used. If not provided, a new empty environment is used.
     * @return InterpretOutput with the modified environment and output logs from the interpretation.
     * @throws Exception if ast of unsupported type. TODO this does not meet Liskov's principle
     */
    fun interpret(
        ast: AST,
        environment: Environment = Environment(),
        inputHandler: ReadInputFunction = StandardInputFunction(),
    ): InterpretOutput
}
