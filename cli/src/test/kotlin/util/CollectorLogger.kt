package util

import logger.Logger

class CollectorLogger : Logger {
    private val messages = mutableListOf<String>()

    override fun log(message: String) {
        messages.add(message)
    }

    fun getLogs(): List<String> {
        return messages
    }
}
