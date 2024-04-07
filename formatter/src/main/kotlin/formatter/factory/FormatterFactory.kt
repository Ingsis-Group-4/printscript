package formatter.factory

import formatter.AstRuleProcessor
import formatter.Formatter
import formatter.rule.factory.FormatterRuleFactory
import formatter.stringifier.ProgramNodeStringifier

class FormatterFactory(configPath: String) {
    private val astRuleProcessor = AstRuleProcessor(FormatterRuleFactory(configPath).getRules())

    fun create(): Formatter = Formatter(astRuleProcessor, ProgramNodeStringifier())
}
