import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class MainKtTest {
    @Test
    fun testSimulatedAnnealing() {
        val initialTemp = 1000.0
        val coolingRate = 0.003

        println("Тест 1: Метод отжига для 8 ферзей")
        val n1 = 8
        val solution1 = simulatedAnnealing(n1, initialTemp, coolingRate)
        assertEquals(0, solution1.evaluate(), "Решение для 8 ферзей не содержит конфликты")
        println("Решение не содержит конфликтов")

        println("Тест 1: Метод отжига для 3 ферзей")
        val n2 = 3
        val solution2 = simulatedAnnealing(n2, initialTemp, coolingRate)
        assertEquals(1, solution2.evaluate(), "Решение для 3 ферзей содержит конфликты")
        println("Решение содержит конфликты")
    }

    @Test
    fun testCountNQueensSolutions() {
        // Тест для 1 ферзя
        assertEquals(1, countNQueensSolutions(1), "Количество решений для 1 ферзя должно быть 1")

        // Тест для 4 ферзей
        assertEquals(2, countNQueensSolutions(4), "Количество решений для 4 ферзей должно быть 2")

        // Тест для 8 ферзей
        assertEquals(92, countNQueensSolutions(8), "Количество решений для 8 ферзей должно быть 92")
    }

    @Test
    fun testIsValid() {
        val positions = IntArray(8) { -1 }

        // Установка ферзей на допустимые позиции
        positions[0] = 0
        positions[1] = 4
        positions[2] = 7
        positions[3] = 5
        positions[4] = 2
        positions[5] = 6
        positions[6] = 1
        positions[7] = 3

        for (i in positions.indices) {
            assertTrue(isValid(positions, i, positions[i]), "Позиция должна быть допустимой для ферзя $i")
        }

        // Установка ферзей на конфликтные позиции
        positions[0] = 0
        positions[1] = 0

        assertFalse(isValid(positions, 1, positions[1]), "Позиция не должна быть допустимой из-за конфликта")
    }

    @Test
    fun testEvaluate() {
        val board = Board(intArrayOf(0, 4, 7, 5, 2, 6, 1, 3))
        assertEquals(0, board.evaluate(), "Решение должно быть без конфликтов")

        val conflictBoard = Board(intArrayOf(0, 0, 0, 0, 0, 0, 0, 0))
        assertTrue(conflictBoard.evaluate() > 0, "Решение должно содержать конфликты")
    }

    @Test
    fun testPrintBoard() {
        val board = Board(intArrayOf(0, 4, 7, 5, 2, 6, 1, 3))
        printBoard(board)
        // Необходимо вручную проверить правильность вывода
    }
}