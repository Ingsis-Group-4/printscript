package cli.function

import cli.util.generateAST
import cli.util.getConfigFilePath
import cli.util.getVersion
import lexer.Lexer
import lexer.getTokenMap
import logger.ConsoleLogger
import logger.Logger
import parser.factory.ProgramParserFactoryV2
import sca.StaticCodeAnalyzer
import sca.factory.DefaultSCARuleFactory
import sca.factory.SCARuleFactory
import java.io.File

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
    private val ruleFactory: SCARuleFactory = DefaultSCARuleFactory(),
    private val logger: Logger = ConsoleLogger(),
) : CLIFunction {
    override fun run(args: Map<String, String>) {
        val version = getVersion(args)

        val lexer = Lexer(getTokenMap(version))
        val parser = ProgramParserFactoryV2.create(version)

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
        val configFilePath = getConfigFilePath(args)
        val configFileContent = File(configFilePath).readText()

        return StaticCodeAnalyzer(
            ruleFactory.getRules(configFileContent),
        )
    }
}
