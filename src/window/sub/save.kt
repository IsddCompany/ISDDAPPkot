package window.sub

import java.io.File
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JTextArea
import javax.swing.filechooser.FileNameExtensionFilter

fun openFile(editarea: JTextArea) {
    val fileChooser = JFileChooser()

    fileChooser.fileFilter = FileNameExtensionFilter("ISDD파일", "isdd")


    val result = fileChooser.showOpenDialog(JFrame())

    if (result == JFileChooser.APPROVE_OPTION) {
        val selectedFile = fileChooser.selectedFile
        if (selectedFile.extension == "isdd") {
            editarea.text = ISDD.decoding(selectedFile.readLines()[0])
        } else{
            val content = selectedFile.readText()
            editarea.text = content
        }

    }
}

fun saveFile(editarea: JTextArea) {
    val fileChooser = JFileChooser()
    val isf = FileNameExtensionFilter("ISDD파일", "isdd")
    fileChooser.fileFilter = isf
    val result = fileChooser.showSaveDialog(null)

    if (result == JFileChooser.APPROVE_OPTION) {

        val selectedFilter = fileChooser.fileFilter
        val selected = if (selectedFilter == isf) {
            ".isdd"
        } else {
            ".txt"
        }
        val content = if (selectedFilter == isf) {
            ISDD.encoding(editarea.text)
        } else {
            editarea.text
        }

        val selectedFile = fileChooser.selectedFile

        try {
            val file = File(selectedFile.path + selected)
            file.writeText(content)
            JOptionPane.showMessageDialog(null, "파일이 정상적으로 저장되었습니다.")


        } catch (e: Exception) {
            JOptionPane.showMessageDialog(null, "저장하는중 문제가 발생했습니다.: ${e.message}")
        }
    }
}