package formatter.stringifier

import ast.AST

interface Stringifier {
    fun stringify(node: AST): String
}
