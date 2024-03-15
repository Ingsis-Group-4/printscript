package org.example.common.visitor

import org.example.ast.*

interface Visitor<T> {
    fun visit(node: ProgramNode): T
    fun visit(node: AssignationNode): T
    fun visit(node: DivisionNode): T
    fun visit(node: IdentifierNode): T
    fun visit(node: StringNode): T
    fun visit(node: NumberNode): T
    fun visit(node: PrintLnNode): T
    fun visit(node: ProductNode): T
    fun visit(node: SubtractionNode): T
    fun visit(node: SumNode): T
    fun visit(node: VariableDeclarationNode): T
}
