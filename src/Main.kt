import window.Mainwindow

fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        val filePath = args[0]
        Mainwindow(filePath)
    } else {
        Mainwindow()
    }

}
