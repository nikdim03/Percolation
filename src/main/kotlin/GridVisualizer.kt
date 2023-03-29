import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

// Generate the grid and find the path with the least white squares
class GridVisualizer(
    private val grid: Array<Array<Boolean>>,
    private val path: List<Cell>,
    gridSize: Int,
    private val fillPercentage: Int
) : JPanel() {
    private val cellSize = 8
    private val padding = 20
    private var pathLength = 0
    private val infoLabel = JLabel().apply {
        text = "Filling percentage: $fillPercentage%\nPath length: 0\nNumber of white squares: 0"
        foreground = Color.BLACK
    }

    init {
        preferredSize = Dimension(gridSize * cellSize + 2 * padding, gridSize * cellSize + 2 * padding)
        add(infoLabel)
    }

    fun display() {
        val frame = JFrame("Grid Visualizer")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.add(this)
        frame.pack()
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
        drawPath()
    }

    private fun drawPath() {
        for (i in 1..path.size) {
            Thread.sleep(50)
            pathLength = i
            updateInfoLabel()
            repaint()
        }
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        paintGrid(g)
        paintPath(g)
    }

    private fun paintGrid(g: Graphics) {
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                g.color = if (grid[row][col]) Color.WHITE else Color.BLACK
                g.fillRect(col * cellSize + padding, row * cellSize + padding, cellSize, cellSize)
            }
        }
    }

    private fun paintPath(g: Graphics) {
        for (i in 0 until pathLength) {
            val cell = path[i]
            g.color = if (grid[cell.row][cell.col]) Color.BLUE else Color.MAGENTA
            g.fillRect(cell.col * cellSize + padding, cell.row * cellSize + padding, cellSize, cellSize)
        }
    }

    private fun updateInfoLabel() {
        val whiteSquares = path.subList(0, pathLength).count { grid[it.row][it.col] }
        infoLabel.text =
            "Filling percentage: $fillPercentage%\nPath length: $pathLength\nNumber of white squares: $whiteSquares"
    }
}