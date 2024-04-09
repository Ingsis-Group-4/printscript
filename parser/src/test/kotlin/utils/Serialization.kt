package parser.utils

import ast.AST
import com.google.gson.GsonBuilder
import java.io.File

fun convertToJson(ast: AST): String {
    return GsonBuilder().setPrettyPrinting().create().toJson(ast)
}

fun getJsonFromFile(path: String): String {
    return File(path).readText()
}
