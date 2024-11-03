package window.sub

import java.util.Base64

//사랑해요 chat-GPT
class ISDD {
    companion object {
        fun encoding(text: String): String {
            val encodedText = Base64.getEncoder().encodeToString(text.toByteArray(Charsets.UTF_8))
            val hexArray = encodedText.map { String.format("%02x", it.code) }
            return hexArray.joinToString(" ")
        }

        fun decoding(encodedText: String): String {
            return try {
                val hexArray = encodedText.split(" ")
                val text = hexArray.joinToString("") { it.toInt(16).toChar().toString() }
                String(Base64.getDecoder().decode(text))
            } catch (e: IllegalArgumentException) {
                throw IsNotISDDEncoded()
            }
        }
    }
}

class IsNotISDDEncoded : Exception("The provided text is not ISDD-encoded")
