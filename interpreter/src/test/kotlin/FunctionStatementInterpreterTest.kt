package interpreter

import ast.LiteralNode
import ast.PrintLnNode
import position.Position
import kotlin.test.Test

class FunctionStatementInterpreterTest {
    @Test
    fun testPrintStatement() {
        val input = PrintLnNode(LiteralNode("hello", Position(1, 1), Position(1, 1)), Position(1, 1), Position(1, 2))

        val interpreter = FunctionStatementInterpreter(input, Environment())

        val result = interpreter.interpret()

        assert(result is VoidValue)
    }
}
