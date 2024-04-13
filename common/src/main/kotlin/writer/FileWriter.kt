package writer

import java.io.File

class FileWriter : Writer {
    override fun write(
        content: String,
        path: String,
    ) {
        File(path).writeText(content)
    }
}
