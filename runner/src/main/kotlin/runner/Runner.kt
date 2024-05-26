package runner

import interpreter.Environment
import interpreter.InterpretOutput
import interpreter.Interpreter
import interpreter.readEnvFunction.ReadEnvFunction
import interpreter.readInputFunction.ReadInputFunction
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
        errorLogger: Logger,
        readInputFunction: ReadInputFunction,
        logger: Logger,
        readEnvFunction: ReadEnvFunction,
    ) {
        try {
            var environment = Environment()

            while (reader.hasNextLine()) {
                val statements: List<List<Token>> = reader.nextLine()

                for (statement in statements) {
                    var parserResult: ParserResult
                    try {
                        parserResult = parser.parse(statement, 0)
                    } catch (e: Exception) {
                        e.message?.let { errorLogger.log(it) }
                        return
                    }

                    if (parserResult is FailureResult) {
                        errorLogger.log(parserResult.message)
                        return
                    }

                    val ast = (parserResult as SuccessResult).value

                    var output: InterpretOutput

                    try {
                        output = interpreter.interpret(ast, environment, readInputFunction, readEnvFunction)
                    } catch (e: Exception) {
                        e.message?.let { errorLogger.log(it) }
                        return
                    }

                    output.logs.forEach(logger::log)
                    environment = output.environment
                }
            }
        } catch (e: Exception) {
            e.message?.let { errorLogger.log(it) }
        }

        if (reader.hasRemainingTokens()) errorLogger.log("Unexpected end of file")
    }
}
