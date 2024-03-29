package cli.function

import cli.util.generateAST
import lexer.Lexer
import lexer.getTokenMap
import logger.ConsoleLogger
import logger.Logger
import parser.Parser
import parser.factory.ProgramParserFactory
import sca.StaticCodeAnalyzer
import sca.factory.StaticCodeAnalyzerConfigurerFactory
import sca.provider.StaticCodeAnalyzerConfigurer

class Analyze(
    private val lexer: Lexer = Lexer(getTokenMap()),
    private val parser: Parser = ProgramParserFactory.create(),
    private val analyzerConfigurer: StaticCodeAnalyzerConfigurer = StaticCodeAnalyzerConfigurerFactory().create(),
    private val logger: Logger = ConsoleLogger(),
) : CLIFunction {
    override fun run(args: Map<String, String>) {
        val ast = generateAST(lexer, parser, args)

        val sca = getSca(args)

        val report = sca.analyze(ast)

        report.getReportMessages().forEach { logger.log(it) }
    }

    private fun getSca(args: Map<String, String>): StaticCodeAnalyzer {
        val configFile = getConfigFile(args)

        return analyzerConfigurer.createStaticCodeAnalyzer(configFile)
    }

    private fun getConfigFile(args: Map<String, String>): String {
        return if (args.containsKey("-c")) {
            args["-c"]!!
        } else {
            throw IllegalArgumentException("No config file provided")
        }
    }
}
