package utils

import java.io.File

fun getStringFromFile(path: String): String {
    return File(path).readText().trimEnd()
}
