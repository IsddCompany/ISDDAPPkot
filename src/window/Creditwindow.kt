package window

import java.awt.BorderLayout
import java.awt.Font
import javax.swing.*


class Creditwindow : JFrame() {

    init {
        val icon = ImageIcon(Mainwindow::class.java.getResource("img/icon.png"))
        iconImage = icon.image
        title = "ISDD Text Editor - Credit"
        setSize(300, 200)
        layout = BorderLayout()

        val creditText = "제작: Jjoon \n버전: 1.1.1 \n사용해주셔서 감사합니다!".toList()

        val textArea = JTextArea()

        var i = 0
        val timer = Timer(100){}
        timer.start()
        timer.addActionListener {
            if (i < creditText.size){
                textArea.text += creditText[i]
                i++
            } else {
                timer.stop()
            }
        }



        textArea.isEditable = false
        textArea.font = Font("Monospaced", Font.PLAIN, 14)

        val scrollPane = JScrollPane(textArea)
        add(scrollPane, BorderLayout.CENTER)

        isVisible = true
    }


}