package interpreter

import ast.LiteralNode
import ast.PrintLnNode
import kotlin.test.Test

class FunctionStatementInterpreterTest {
    @Test
    fun testPrintStatement() {
        val input = PrintLnNode(LiteralNode("hello"))

        val interpreter = FunctionStatementInterpreter(input, Environment())

        val result = interpreter.interpret()

        assert(result is VoidValue)
    }
}
