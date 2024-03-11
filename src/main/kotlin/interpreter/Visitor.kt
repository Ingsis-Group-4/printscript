package org.example.interpreter

import org.example.ast.*

interface Visitor<T> {
    fun visit(node: ProgramNode): T
    fun visit(node: AssignationNode): T
    fun visit(node: DivisionNode): T
    fun visit(node: IdentifierNode): T
    fun <K> visit(node: LiteralNode<K>): T
    fun visit(node: PrintLnNode): T
    fun visit(node: ProductNode): T
    fun visit(node: SubtractionNode): T
    fun visit(node: SumNode): T
    fun visit(node: VariableDeclarationNode): T
}
