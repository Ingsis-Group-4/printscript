package runner

import interpreter.Environment
import interpreter.InterpretOutput
import interpreter.Interpreter
import interpreter.readInputFunction.ReadInputFunction
import logger.ErrorLogger
import logger.Logger
import parser.Parser
import parser.result.FailureResult
import parser.result.ParserResult
import parser.result.SuccessResult
import reader.StatementFileReader
import token.Token

class Runner {
    fun run(
        reader: StatementFileReader,
        parser: Parser,
        interpreter: Interpreter,
        handler: ErrorLogger,
        readInputFunction: ReadInputFunction,
        logger: Logger,
    ) {
        try {
            var environment: Environment = Environment()

            while (reader.hasNextLine()) {
                val statements: List<List<Token>> = reader.nextLine()

                for (statement in statements) {
                    var parserResult: ParserResult
                    try {
                        parserResult = parser.parse(statement, 0)
                    } catch (e: Exception) {
                        e.message?.let { handler.log(it) }
                        return
                    }

                    if (parserResult is FailureResult) {
                        handler.log(parserResult.message)
                        return
                    }

                    val ast = (parserResult as SuccessResult).value

                    var output: InterpretOutput

                    try {
                        output = interpreter.interpret(ast, environment, readInputFunction)
                    } catch (e: Exception) {
                        e.message?.let { handler.log(it) }
                        return
                    }

                    output.logs.forEach(logger::log)
                    environment = output.environment
                }
            }
        } catch (e: Exception) {
            e.message?.let { handler.log(it) }
        }
    }
}
