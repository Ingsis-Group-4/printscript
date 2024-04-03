package cli.function

import cli.util.generateAST
import lexer.Lexer
import lexer.getTokenMap
import logger.ConsoleLogger
import logger.Logger
import parser.Parser
import parser.factory.ProgramParserFactory
import sca.StaticCodeAnalyzer
import sca.factory.DefaultSCARuleFactory
import sca.factory.SCARuleFactory

/**
 * Analyzes the given source code, with a static code analyzer
 * provided by the static code analyzer configurer with a set of rules.
 *
 * @param lexer The lexer to use.
 * @param parser The parser to use.
 * @param analyzerConfigurer The static code analyzer configurer to use.
 * @param logger The logger to use.
 */

class Analyze(
    private val lexer: Lexer = Lexer(getTokenMap()),
    private val parser: Parser = ProgramParserFactory.create(),
    private val ruleFactory: SCARuleFactory = DefaultSCARuleFactory(),
    private val logger: Logger = ConsoleLogger(),
) : CLIFunction {
    override fun run(args: Map<String, String>) {
        // Generate the AST from the source code
        val ast = generateAST(lexer, parser, args)

        // Get the static code analyzer
        val sca = getSca(args)

        // Analyze the AST
        val report = sca.analyze(ast)

        // Log the report messages
        report.getReportMessages().forEach { logger.log(it) }
    }

    /**
     * Configures and returns the static code analyzer with the set of rules from the config file.
     */
    private fun getSca(args: Map<String, String>): StaticCodeAnalyzer {
        val configFile = getConfigFile(args)

        return StaticCodeAnalyzer(
            ruleFactory.getRules(configFile),
        )
    }

    /**
     * Gets the config file from the command line arguments.
     */
    private fun getConfigFile(args: Map<String, String>): String {
        return if (args.containsKey("-c")) {
            args["-c"]!!
        } else {
            throw IllegalArgumentException("No config file provided")
        }
    }
}
