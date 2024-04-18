package cli.function

import cli.util.generateAST
import cli.util.getConfigFilePath
import cli.util.getFilePath
import cli.util.getVersion
import formatter.ProgramNodeFormatter
import formatter.factory.FormatterMapFactory
import formatter.rule.FormattingRule
import lexer.Lexer
import lexer.getTokenMap
import parser.factory.ProgramParserFactoryV2
import writer.FileWriter
import writer.Writer

/**
 * Formats the given source code.
 */
class Format(
    private val writer: Writer = FileWriter(),
) : CLIFunction {
    override fun run(args: Map<String, String>) {
        val version = getVersion(args)
        val lexer = Lexer(getTokenMap(version))
        val parser = ProgramParserFactoryV2.create(version)

        // Generate the AST from the source code
        val ast = generateAST(lexer, parser, args)
        val filePath = getFilePath(args)
        val configPath = getConfigFilePath(args)
        // Format the AST
        val result = ProgramNodeFormatter().format(ast, FormattingRule(configPath), FormatterMapFactory().createFormatterMap())
        writer.write(result, filePath)
    }
}
