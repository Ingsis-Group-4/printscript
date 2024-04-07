package interpreter

import ast.FunctionStatementNode
import ast.LiteralNode
import ast.OperatorNode
import ast.OperatorType
import ast.PrintLnNode
import ast.SumNode
import position.Position
import util.CollectorLogger
import kotlin.test.Test

class FunctionStatementInterpreterTest {
    @Test
    fun testPrintStatementWithStringLiteral() {
        val input =
            FunctionStatementNode(
                Position(1, 1),
                Position(1, 14),
                PrintLnNode(
                    LiteralNode(
                        "hello",
                        Position(1, 9),
                        Position(1, 13),
                    ),
                    Position(1, 1),
                    Position(1, 14),
                ),
            )

        val collectorLogger = CollectorLogger()

        val interpreter = FunctionStatementInterpreter(input, Environment(), collectorLogger)

        interpreter.interpret()

        val logs = collectorLogger.getLogs()

        assert(logs.size == 1)
        assert(logs[0] == "hello")
    }

    @Test
    fun testPrintStatementWithNumberLiteral() {
        val input =
            FunctionStatementNode(
                Position(1, 1),
                Position(1, 2),
                PrintLnNode(
                    LiteralNode(
                        5.0,
                        Position(1, 1),
                        Position(1, 1),
                    ),
                    Position(1, 1),
                    Position(1, 2),
                ),
            )

        val collectorLogger = CollectorLogger()

        val interpreter = FunctionStatementInterpreter(input, Environment(), collectorLogger)

        interpreter.interpret()

        val logs = collectorLogger.getLogs()

        assert(logs.size == 1)
        assert(logs[0] == "5.0")
    }

    @Test
    fun testPrintStatementWithSum() {
        val input =
            FunctionStatementNode(
                Position(1, 1),
                Position(1, 2),
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
                ),
            )

        val collectorLogger = CollectorLogger()

        val interpreter = FunctionStatementInterpreter(input, Environment(), collectorLogger)

        interpreter.interpret()

        val logs = collectorLogger.getLogs()

        assert(logs.size == 1)
        assert(logs[0] == "5.0")
    }
}
