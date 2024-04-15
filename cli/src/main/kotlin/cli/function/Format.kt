package cli.function

import cli.util.generateAST
import cli.util.getFilePath
import formatter.FormattingRule
import formatter.ProgramNodeFormatter
import formatter.factory.FormatterMapFactory
import lexer.Lexer
import lexer.getTokenMap
import parser.Parser
import parser.factory.ProgramParserFactory
import writer.FileWriter
import writer.Writer

/**
 * Formats the given source code.
 */
class Format(
    private val lexer: Lexer = Lexer(getTokenMap()),
    private val parser: Parser = ProgramParserFactory.create(),
    private val writer: Writer = FileWriter(),
) : CLIFunction {
    private val configFile = "src/test/resources/format/config/formatter.test.config.json"

    override fun run(args: Map<String, String>) {
        // Generate the AST from the source code
        val ast = generateAST(lexer, parser, args)
        val filePath = getFilePath(args)
        // Format the AST
        // Not sure if we should have an FormatterResult here
        val result = ProgramNodeFormatter().format(ast, FormattingRule(configPath = configFile), FormatterMapFactory().createFormatterMap())
        writer.write(result, filePath)
    }
}
