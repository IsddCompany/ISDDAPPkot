package window

import java.awt.BorderLayout
import java.awt.Font
import javax.swing.*

class creditwindow : JFrame() {

    init {
        val icon = ImageIcon(Mainwindow::class.java.getResource("img/icon.png"))
        this.iconImage = icon.image
        title = "ISDD Text Editor - Credit"
        setSize(300, 200)
        layout = BorderLayout()

        val creditText = """
            개발자: Jjoon
            버전: 1.0.0
            """.trimIndent()

        val textArea = JTextArea(creditText)
        textArea.isEditable = false
        textArea.font = Font("Monospaced", Font.PLAIN, 14)

        val scrollPane = JScrollPane(textArea)
        add(scrollPane, BorderLayout.CENTER)

        isVisible = true
    }

}