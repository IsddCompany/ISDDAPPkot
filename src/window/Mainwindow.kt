package window

import java.awt.*
import java.awt.event.*
import java.net.URI
import javax.swing.*
import kotlin.system.exitProcess
import window.util.*

class Mainwindow(fileadd: String? = null) : JFrame() {
    private val editarea = JTextPane().apply {
        font = Font("Consolas", Font.PLAIN, 14)
        background = Color(30, 30, 30)
        foreground = Color(200, 200, 200)
        isEditable = true
        border = BorderFactory.createEmptyBorder()
    }

    private var posX = 0
    private var posY = 0

    init {
        iconImage = ImageIcon(Mainwindow::class.java.getResource("img/icon.png")).image
        title = "ISDD Text Editor"
        setSize(1280, 600)
        isUndecorated = true
        defaultCloseOperation = EXIT_ON_CLOSE

        val titleBar = JPanel().apply {
            background = Color(60, 60, 60)
            layout = BorderLayout()
            preferredSize = Dimension(width, 40)
        }

        titleBar.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                posX = e.x
                posY = e.y
            }
        })
        titleBar.addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(e: MouseEvent) {
                setLocation(e.xOnScreen - posX, e.yOnScreen - posY)
            }
        })

        val menuBar = createMenuBar().apply {
            isOpaque = false
            background = Color(60, 60, 60)
        }

        val minimizeButton = createTitleButton("─") { extendedState = ICONIFIED }
        val closeButton = createTitleButton("X", Color.RED) { dispose() }

        val buttonPanel = JPanel().apply {
            background = Color(37, 37, 38)
            layout = FlowLayout(FlowLayout.RIGHT)
            add(minimizeButton)
            add(closeButton)
        }

        // 아이콘 크기 조정
        val icon = ImageIcon(Mainwindow::class.java.getResource("img/icon.png"))
        val scaledIcon = ImageIcon(icon.image.getScaledInstance(20, 20, Image.SCALE_SMOOTH))
        val iconLabel = JLabel(scaledIcon)

        // 아이콘을 왼쪽에 배치하고, 메뉴바는 그 옆에 배치
        val titlePanel = JPanel().apply {
            layout = BorderLayout()
            add(iconLabel, BorderLayout.WEST)  // 아이콘을 왼쪽에 배치
            add(menuBar, BorderLayout.CENTER)
            background = Color(60, 60, 60)
            border = BorderFactory.createEmptyBorder(0, 10, 0, 10)
        }

        titleBar.add(titlePanel, BorderLayout.CENTER) // 아이콘과 메뉴바를 하나의 패널에 넣음
        titleBar.add(buttonPanel, BorderLayout.EAST)  // 닫기, 최소화 버튼은 오른쪽

        contentPane.layout = BorderLayout()
        contentPane.add(titleBar, BorderLayout.NORTH)
        contentPane.add(JScrollPane(editarea), BorderLayout.CENTER)

        setupShortcuts()

        fileadd?.let {
            try {
                editarea.text = openisdd(it)
            } catch (e: Exception) {
                JOptionPane.showMessageDialog(null, ".ISDD 파일이 아니거나 손상되었습니다.")
            }
        }

        isVisible = true
    }

    private fun createMenuBar(): JMenuBar {
        val menuBar = JMenuBar().apply {
            background = Color(60, 60, 60)
            border = BorderFactory.createEmptyBorder(0, 10, 0, 10)
        }

        val fileMenu = JMenu("파일").apply { foreground = Color.WHITE }
        val helpMenu = JMenu("도움말").apply { foreground = Color.WHITE }

        val newt = JMenuItem("다 지우기 (Ctrl+N)").apply { addActionListener { editarea.text = "" } }
        val opent = JMenuItem("열기 (Ctrl+O)").apply { addActionListener { openFile(editarea) } }
        val savet = JMenuItem("저장 (Ctrl+S)").apply { addActionListener { saveFile(editarea) } }
        val exitt = JMenuItem("나가기").apply { addActionListener { exitProcess(0) } }

        val craditt = JMenuItem("크레딧").apply { addActionListener { Creditwindow() } }
        val githubt = JMenuItem("저장소로 가기").apply {
            addActionListener {
                val url = "https://github.com/IsddCompany/ISDDAPPkot"
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(URI(url))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }

        fileMenu.apply {
            add(newt)
            add(opent)
            add(savet)
            addSeparator()
            add(exitt)
        }
        helpMenu.apply {
            add(craditt)
            add(githubt)
        }

        menuBar.add(fileMenu)
        menuBar.add(helpMenu)
        return menuBar
    }

    private fun createTitleButton(text: String, bgColor: Color = Color(60, 60, 60), action: () -> Unit): JButton {
        return JButton(text).apply {
            foreground = Color.WHITE
            background = bgColor
            border = BorderFactory.createEmptyBorder(5, 10, 5, 10)
            addActionListener { action() }
        }
    }

    private fun setupShortcuts() {
        val inputMap = editarea.getInputMap(JComponent.WHEN_FOCUSED)
        val actionMap = editarea.actionMap

        inputMap.put(KeyStroke.getKeyStroke("control S"), "save")
        inputMap.put(KeyStroke.getKeyStroke("control O"), "open")
        inputMap.put(KeyStroke.getKeyStroke("control N"), "newfile")

        actionMap.put("save", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) { saveFile(editarea) }
        })
        actionMap.put("open", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) { openFile(editarea) }
        })
        actionMap.put("newfile", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) { editarea.text = "" }
        })
    }
}
