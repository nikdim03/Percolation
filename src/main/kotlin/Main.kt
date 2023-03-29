import kotlin.random.Random

data class Cell(val row: Int, val col: Int)

fun main() {
    val gridSize = 100
    val fillPercentage = listOf(50, 75)

    fillPercentage.forEach { percentage ->
        val grid = generateGrid(gridSize, percentage)
        val path = findPathWithLeastWhiteSquares(grid)
        println("Filling percentage: $percentage%")
        println("Path length: ${path.size}")
        println("Number of white squares: ${path.count { grid[it.row][it.col] }}")
//        printGridWithPath(grid, path)
//        println()
        GridVisualizer(grid, path, gridSize, percentage).display()
        Thread.sleep(2000)
    }
}

fun generateGrid(gridSize: Int, fillPercentage: Int): Array<Array<Boolean>> {
    val grid = Array(gridSize) { Array(gridSize) { false } }
    for (row in grid.indices) {
        for (col in grid[row].indices) {
            grid[row][col] = Random.nextInt(100) < fillPercentage
        }
    }
    return grid
}

fun findPathWithLeastWhiteSquares(grid: Array<Array<Boolean>>): List<Cell> {
    val gridSize = grid.size
    val weights = Array(gridSize) { Array(gridSize) { Int.MAX_VALUE } }
    val visited = Array(gridSize) { Array(gridSize) { false } }
    val previous = mutableMapOf<Cell, Cell?>()

    // Init the top row
    for (col in 0 until gridSize) {
        weights[0][col] = if (grid[0][col]) 1 else 0
        previous[Cell(0, col)] = null
    }

    while (true) {
        val current = findMinimumWeightCell(weights, visited)

        if (current == null || current.row == gridSize - 1) {
            return buildPath(previous, current)
        }

        visited[current.row][current.col] = true

        val neighbors = listOf(
//            Cell(current.row - 1, current.col),
            Cell(current.row + 1, current.col),
            Cell(current.row, current.col - 1),
            Cell(current.row, current.col + 1)
        )

        for (neighbor in neighbors) {
            if (isValid(grid, visited, neighbor)) {
                val weight = weights[current.row][current.col] + (if (grid[neighbor.row][neighbor.col]) 1 else 0)
                if (weight < weights[neighbor.row][neighbor.col]) {
                    weights[neighbor.row][neighbor.col] = weight
                    previous[neighbor] = current
                }
            }
        }
    }
}

fun findMinimumWeightCell(weights: Array<Array<Int>>, visited: Array<Array<Boolean>>): Cell? {
    var minWeight = Int.MAX_VALUE
    var minWeightCell: Cell? = null

    for (row in weights.indices) {
        for (col in weights[row].indices) {
            if (!visited[row][col] && weights[row][col] < minWeight) {
                minWeight = weights[row][col]
                minWeightCell = Cell(row, col)
            }
        }
    }

    return minWeightCell
}

fun buildPath(previous: Map<Cell, Cell?>, lastCell: Cell?): List<Cell> {
    val path = mutableListOf<Cell>()
    var current = lastCell
    while (current != null) {
        path.add(current)
        current = previous[current]
    }

    return path.reversed()
}

fun isValid(grid: Array<Array<Boolean>>, visited: Array<Array<Boolean>>, cell: Cell): Boolean {
    val (row, col) = cell
    if (row < 0 || row >= grid.size || col < 0 || col >= grid.size) return false
    return !visited[row][col]
}

fun printGridWithPath(grid: Array<Array<Boolean>>, path: List<Cell>) {
    for (row in grid.indices) {
        for (col in grid[row].indices) {
            val cell = Cell(row, col)
            when {
                path.contains(cell) && grid[row][col] -> print("🟥")
                path.contains(cell) && !grid[row][col] -> print("🟪")
                grid[row][col] -> print("⬛")
                else -> print("⬜")
            }
        }
        println()
    }
}