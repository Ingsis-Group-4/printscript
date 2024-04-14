package cli.function

import ast.AssignationNode
import ast.DeclarationNode
import ast.FunctionStatementNode
import cli.util.getFilePath
import interpreter.Environment
import interpreter.FunctionStatementInterpreter
import interpreter.Interpreter
import interpreter.StatementInterpreter
import interpreter.VariableStatementInterpreter
import logger.ConsoleLogger
import logger.Logger
import parser.Parser
import parser.factory.StatementParserFactory
import parser.result.FailureResult
import parser.result.SuccessResult
import reader.StatementFileReader

class BufferedInterpret(
    private val parser: Parser = StatementParserFactory.create(),
    private val interpreter: Interpreter =
        StatementInterpreter(
            mapOf(
                FunctionStatementNode::class to FunctionStatementInterpreter(),
                DeclarationNode::class to VariableStatementInterpreter(),
                AssignationNode::class to VariableStatementInterpreter(),
            ),
        ),
    private val logger: Logger = ConsoleLogger(),
) : CLIFunction {
    override fun run(args: Map<String, String>) {
        val filePath = getFilePath(args)
        val reader = StatementFileReader(filePath)

        var env = Environment()

        while (reader.hasNextLine()) {
            val statements = reader.nextLine()

            for (statement in statements) {
                val result = parser.parse(statement, 0)

                when (result) {
                    is SuccessResult -> {
                        val ast = result.value
                        val output = interpreter.interpret(ast, env)
                        env = output.environment
                        for (log in output.logs) {
                            logger.log(log)
                        }
                    }
                    is FailureResult -> {
                        throw IllegalArgumentException("Parsing Error: ${result.message}")
                    }
                }
            }
        }
    }
}
