package interpreter

import ast.LiteralNode
import ast.OperatorNode
import ast.OperatorType
import ast.PrintLnNode
import ast.SumNode
import position.Position
import kotlin.test.Test

class FunctionStatementInterpreterTest {
    @Test
    fun testPrintStatementWithStringLiteral() {
        val input =
            PrintLnNode(
                LiteralNode(
                    "hello",
                    Position(1, 9),
                    Position(1, 13),
                ),
                Position(1, 1),
                Position(1, 14),
            )

        val interpreter = FunctionStatementInterpreter()

        val result = interpreter.interpret(input, Environment())

        val logs = result.logs

        assert(logs.size == 1)
        assert(logs[0] == "hello")
    }

    @Test
    fun testPrintStatementWithNumberLiteral() {
        val input =
            PrintLnNode(
                LiteralNode(
                    5.0,
                    Position(1, 1),
                    Position(1, 1),
                ),
                Position(1, 1),
                Position(1, 2),
            )

        val interpreter = FunctionStatementInterpreter()

        val result = interpreter.interpret(input, Environment())

        val logs = result.logs

        assert(logs.size == 1)
        assert(logs[0] == "5.0")
    }

    @Test
    fun testPrintStatementWithSum() {
        val input =
            PrintLnNode(
                SumNode(
                    LiteralNode(2.0, Position(1, 1), Position(1, 1)),
                    LiteralNode(3.0, Position(1, 1), Position(1, 1)),
                    OperatorNode(Position(1, 1), Position(1, 1), OperatorType.SUM),
                    Position(1, 1),
                    Position(1, 1),
                ),
                Position(1, 1),
                Position(1, 2),
            )

        val interpreter = FunctionStatementInterpreter()

        val result = interpreter.interpret(input, Environment())

        val logs = result.logs

        assert(logs.size == 1)
        assert(logs[0] == "5.0")
    }
}
