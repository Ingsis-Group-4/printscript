package formatter.utils

import ast.OperatorType

interface OperatorFormatter {
    fun format(operatorType: OperatorType): String
}

object DefaultOperatorFormatter : OperatorFormatter {
    override fun format(operatorType: OperatorType): String {
        return when (operatorType) {
            OperatorType.SUM -> "+"
            OperatorType.SUBTRACT -> "-"
            OperatorType.MULTIPLICATION -> "*"
            OperatorType.DIVISION -> "/"
            OperatorType.NEGATION -> "!"
        }
    }
}
