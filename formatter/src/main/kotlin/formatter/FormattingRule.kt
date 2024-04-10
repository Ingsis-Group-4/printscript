package formatter

import formatter.configurer.LineBreakBeforePrintLnConfigurer
import formatter.configurer.SpaceBetweenColonConfigurer
import formatter.configurer.SpaceBetweenEqualSignConfigurer

private const val CONFIG_PATH = "path"

data class FormattingRule(
    val whiteSpaceBeforeAndAfterOperation: Boolean = true,
    val lineBreakAfterSemicolon: Boolean = true,
    val whiteSpaceBetweenTokens: Boolean = true,
    val spaceBetweenColon: Boolean = SpaceBetweenColonConfigurer(CONFIG_PATH).getRule(),
    val spaceBetweenEqualSign: Boolean = SpaceBetweenEqualSignConfigurer(CONFIG_PATH).getRule(),
    val lineBreakBeforePrintLn: Int = LineBreakBeforePrintLnConfigurer(CONFIG_PATH).getRule(),
)
