package interpreter

import ast.LiteralNode
import ast.PrintLnNode
import org.example.interpreter.Environment
import org.example.interpreter.FunctionStatementInterpreter
import org.example.interpreter.VoidValue
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
