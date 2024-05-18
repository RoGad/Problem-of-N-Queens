import kotlin.math.exp
import kotlin.random.Random

fun main() {
    println("Введите количество ферзей (n): ")
    val n = readLine()?.toIntOrNull() ?: return println("Некорректный ввод. Попробуйте снова.")

    if (n <= 0) {
        println("Количество ферзей должно быть положительным числом.")
        return
    }

    val initialTemp = 1000.0
    val coolingRate = 0.003

    val solution = simulatedAnnealing(n, initialTemp, coolingRate)

    println("Финальная конфигурация доски:")
    printBoard(solution)
    println("Конфликты: ${solution.evaluate()}")
    println("Всего решений задачи о $n ферзях: ${countNQueensSolutions(n)}")
}

fun countNQueensSolutions(n: Int): Int {
    val positions = IntArray(n) { -1 }
    return placeQueens(positions, 0, n)
}

fun placeQueens(positions: IntArray, targetRow: Int, size: Int): Int {
    if (targetRow == size) {
        return 1
    }

    var count = 0
    for (column in 0 until size) {
        if (isValid(positions, targetRow, column)) {
            positions[targetRow] = column
            count += placeQueens(positions, targetRow + 1, size)
            positions[targetRow] = -1
        }
    }
    return count
}

fun isValid(positions: IntArray, occupiedRows: Int, column: Int): Boolean {
    for (i in 0 until occupiedRows) {
        if (positions[i] == column ||
            positions[i] - i == column - occupiedRows ||
            positions[i] + i == column + occupiedRows) {
            return false
        }
    }
    return true
}

data class Board(val positions: IntArray) {
    fun copy() = Board(positions.copyOf())

    fun evaluate(): Int {
        var conflicts = 0
        for (i in positions.indices) {
            for (j in i + 1 until positions.size) {
                if (positions[i] == positions[j] ||
                    positions[i] - i == positions[j] - j ||
                    positions[i] + i == positions[j] + j) {
                    conflicts++
                }
            }
        }
        return conflicts
    }
}

fun simulatedAnnealing(n: Int, initialTemp: Double, coolingRate: Double): Board {
    var temp = initialTemp
    var currentBoard = Board(IntArray(n) { it })
    currentBoard.positions.shuffle()
    var currentEnergy = currentBoard.evaluate()

    var bestBoard = currentBoard.copy()
    var bestEnergy = currentEnergy

    while (temp > 1) {
        val newBoard = currentBoard.copy()
        val i = Random.nextInt(n)
        val j = Random.nextInt(n)
        newBoard.positions[i] = newBoard.positions[j].also { newBoard.positions[j] = newBoard.positions[i] }

        val newEnergy = newBoard.evaluate()
        if (newEnergy < bestEnergy) {
            bestBoard = newBoard.copy()
            bestEnergy = newEnergy
        }

        if (newEnergy < currentEnergy || exp((currentEnergy - newEnergy) / temp) > Random.nextDouble()) {
            currentBoard = newBoard
            currentEnergy = newEnergy
        }

        temp *= 1 - coolingRate
    }

    return bestBoard
}

fun printBoard(board: Board) {
    val n = board.positions.size
    for (i in 0 until n) {
        for (j in 0 until n) {
            if (board.positions[i] == j) print("Q ") else print(". ")
        }
        println()
    }
}
