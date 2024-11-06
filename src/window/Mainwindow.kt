package window

import java.net.URI
import window.sub.saveFile
import window.sub.openFile
import window.sub.openisdd
import java.awt.Desktop
import javax.swing.*
import java.awt.Font
import java.awt.event.ActionEvent
import kotlin.system.exitProcess

//코틀린이 좋긴하네 자바보단;
class Mainwindow(fileadd : String? = null) : JFrame() {
    init {

        val icon = ImageIcon(Mainwindow::class.java.getResource("img/icon.png"))
        this.iconImage = icon.image


        title = "ISDD Text Editor"

        val menuBar = JMenuBar()

        val fileMenu = JMenu("파일")
        val newt = JMenuItem("다 지우기(Ctrl+N)")
        val opent = JMenuItem("열기(Ctrl+O)")
        val savet = JMenuItem("저장(Ctrl+S)")
        val exitt = JMenuItem("나가기")

        val helpMenu = JMenu("도움알")
        val craditt = JMenuItem("크래딧")
        val githubt = JMenuItem("저장소로 가기")

        helpMenu.isEnabled = true
        fileMenu.isEnabled = true

        fileMenu.add(newt)
        fileMenu.add(opent)
        fileMenu.add(savet)
        fileMenu.addSeparator()
        fileMenu.add(exitt)

        helpMenu.add(craditt)
        helpMenu.add(githubt)

        menuBar.add(fileMenu)
        menuBar.add(helpMenu)

        jMenuBar = menuBar

        setSize(1280, 600)


        defaultCloseOperation = EXIT_ON_CLOSE

        val editarea = JTextArea().apply {
            lineWrap = true
            wrapStyleWord = true
            font = Font("SansSerif", Font.PLAIN, 15)
        }


        val scrollPane = JScrollPane(editarea)
        add(scrollPane)

        githubt.addActionListener{
            val url = "https://github.com/IsddCompany/ISDDAPPkot"

            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(URI(url))
                } catch (e: Exception) {
                    e.printStackTrace()
                    println("웹사이트를 열 수 없습니다: ${e.message}")
                }
            } else {
                println("데스크탑 기능이 지원되지 않습니다.")
            }
        }


        newt.addActionListener{
            editarea.text = ""
        }
        craditt.addActionListener{
            Creditwindow()
        }
        opent.addActionListener {
            openFile(editarea)
        }
        savet.addActionListener {
            saveFile(editarea)
        }
        exitt.addActionListener {
            exitProcess(0)
        }

        val saveAction = object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) {
                saveFile(editarea)
            }
        }
        val openAction = object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) {
                openFile(editarea)
            }
        }
        val newfileAction = object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) {
                editarea.text = ""
            }
        }

        editarea.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("control S"), "save")
        editarea.actionMap.put("save", saveAction)

        editarea.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("control O"), "open")
        editarea.actionMap.put("open", openAction)

        editarea.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("control N"), "newfile")
        editarea.actionMap.put("newfile", newfileAction)

        if (fileadd != null){
            try {
                editarea.text = openisdd(fileadd)
            } catch (e: Exception) {
                JOptionPane.showMessageDialog(null, ".ISDD파일이 아닌것 같습니다. 파일이 손상되었거나 잘못 저장되었습니다.")
            }

        }

        isVisible = true
    }
}