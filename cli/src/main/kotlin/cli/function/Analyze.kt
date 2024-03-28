package cli.function

import cli.util.generateAST
import org.example.StaticCodeAnalyzer
import org.example.cli.functions.CLIFunction
import org.example.factory.StaticCodeAnalyzerConfigurerFactory
import org.example.lexer.Lexer
import org.example.lexer.getTokenMap
import org.example.parser.Parser
import org.example.parser.factory.ProgramParserFactory
import org.example.provider.StaticCodeAnalyzerConfigurer

class Analyze(
    private val lexer: Lexer = Lexer(getTokenMap()),
    private val parser: Parser = ProgramParserFactory.create(),
    private val analyezrConfigurer: StaticCodeAnalyzerConfigurer = StaticCodeAnalyzerConfigurerFactory().create(),
) : CLIFunction {
    override fun run(args: Map<String, String>) {
        val ast = generateAST(lexer, parser, args)

        val sca = getSca(args)

        val report = sca.analyze(ast)
        report.print()
    }

    private fun getSca(args: Map<String, String>): StaticCodeAnalyzer {
        val configFile = getConfigFile(args)

        return analyezrConfigurer.createStaticCodeAnalyzer(configFile)
    }

    private fun getConfigFile(args: Map<String, String>): String {
        return if (args.containsKey("-c")) {
            args["-c"]!!
        } else {
            throw IllegalArgumentException("No config file provided")
        }
    }
}
