package cli.function

import cli.util.getFilePath
import cli.util.getVersion
import interpreter.StatementInterpreter
import interpreter.factory.getInterpreterMap
import interpreter.readEnvFunction.SystemEnvFunction
import interpreter.readInputFunction.StandardInputFunction
import lexer.LineLexer
import lexer.getTokenMap
import logger.ConsoleLogger
import logger.Logger
import parser.factory.StatementParserFactory
import reader.StatementFileReader
import runner.Runner
import java.io.File

/**
 * Interprets the given source code by statement.
 *
 * @param lexer The lexer to use.
 * @param parser The parser to use.
 * @param logger The logger to use.
 */

class BufferedInterpret(
    private val lexer: LineLexer = LineLexer(getTokenMap()),
    private val logger: Logger = ConsoleLogger(),
) : CLIFunction {
    override fun run(args: Map<String, String>) {
        val version = getVersion(args)

        val parser = StatementParserFactory.create(version)
        val runner = Runner()
        runner.run(
            reader = StatementFileReader(File(getFilePath(args)).inputStream(), version),
            readInputFunction = StandardInputFunction(),
            parser = parser,
            interpreter = StatementInterpreter(getInterpreterMap(version)),
            errorLogger = logger,
            logger = logger,
            readEnvFunction = SystemEnvFunction(),
        )
    }
}
