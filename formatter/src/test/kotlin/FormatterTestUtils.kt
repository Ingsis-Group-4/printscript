import ast.AST
import com.google.gson.GsonBuilder
import java.io.File

fun convertToJson(ast: AST): String {
    return GsonBuilder().setPrettyPrinting().create().toJson(ast)
}

fun getStringFromFile(path: String): String {
    return File(path).readText()
}
