package cli.function

import cli.util.generateAST
import cli.util.getConfigFilePath
import cli.util.getFilePath
import formatter.ProgramNodeFormatter
import formatter.factory.FormatterMapFactory
import formatter.rule.FormattingRule
import lexer.Lexer
import lexer.getTokenMap
import parser.Parser
import parser.factory.ProgramParserFactoryV2
import version.Version
import writer.FileWriter
import writer.Writer

/**
 * Formats the given source code.
 */
class Format(
    private val lexer: Lexer = Lexer(getTokenMap(Version.V2)),
    private val parser: Parser = ProgramParserFactoryV2.create(Version.V2),
    private val writer: Writer = FileWriter(),
) : CLIFunction {
    override fun run(args: Map<String, String>) {
        // Generate the AST from the source code
        val ast = generateAST(lexer, parser, args)
        val filePath = getFilePath(args)
        val configPath = getConfigFilePath(args)
        // Format the AST
        val result = ProgramNodeFormatter().format(ast, FormattingRule(configPath), FormatterMapFactory().createFormatterMap())
        writer.write(result, filePath)
    }
}
