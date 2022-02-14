package hse.se;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final Integer[][] arr = {{5, 8, 0, 2, 0, 0, 4, 7, 0},
            {0, 2, 0, 0, 0, 0, 0, 3, 0},
            {0, 3, 0, 0, 5, 4, 0, 0, 0},
            {0, 0, 0, 5, 6, 0, 0, 0, 0},
            {0, 0, 7, 0, 3, 0, 9, 0, 0},
            {0, 0, 0, 0, 9, 1, 0, 0, 0},
            {0, 0, 0, 8, 2, 0, 0, 6, 0},
            {0, 7, 0, 0, 0, 0, 0, 8, 0},
            {0, 9, 4, 0, 0, 6, 0, 1, 5}};

    public static void main(String[] args) {
        var sudokuMatrix = Arrays.stream(arr).map(Arrays::asList).collect(Collectors.toList());
        if (solve(sudokuMatrix)) {
            System.out.println("корректно");
            return;
        }
        System.out.println("некорректно");
    }

    private static boolean solve(List<List<Integer>> sudokuMatrix) {
        var coords = findFirstZeroCell(sudokuMatrix);
        if (coords.contains(-1)) {
            return true;
        }
        for (int num = 1; num < 10; num++) {
            if (isEmpty(sudokuMatrix, coords.get(0), coords.get(1), num)) {
                sudokuMatrix.get(coords.get(0)).set(coords.get(1), num);
                boolean check = solve(sudokuMatrix);
                if (check) {
                    return true;
                }
                sudokuMatrix.get(coords.get(0)).set(coords.get(1), 0);
            }
        }
        return false;
    }

    /**
     * @param sudokuMatrix - Матрица, отвечающая за поле судоку.
     * @param row          - номер строки на поле.
     * @param num          - цифра, которую ищем в строке.
     * @return - есть цифра или нет.
     */
    private static boolean isTypedInRow(List<List<Integer>> sudokuMatrix, int row, int num) {
        return sudokuMatrix.get(row).contains(num);
    }

    /**
     * @param sudokuMatrix - Матрица, отвечающая за поле судоку.
     * @param col          - номер колонки на поле.
     * @param num          - цифра, которую ищем в колонке.
     * @return - есть цифра или нет.
     */
    private static boolean isTypedInCol(List<List<Integer>> sudokuMatrix, int col, int num) {
        var columnValues = sudokuMatrix.stream().mapToInt(integers -> integers.get(col)).boxed().collect(Collectors.toList());
        return columnValues.contains(num);
    }

    /**
     * @param sudokuMatrix - Матрица, отвечающая за поле судоку.
     * @param startInRow   - Начальная строка одного из девяти внутренних квадратов.
     * @param startInCol   - Начальная колонка одного из девяти внутренних квадратов.
     * @param num          - цифра, которую ищем в квадрате.
     * @return - есть цифра или нет.
     */
    private static boolean isTypedInSquare(List<List<Integer>> sudokuMatrix, int startInRow, int startInCol, int num) {
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                if (sudokuMatrix.get(row + startInRow).get(col + startInCol) == num) {
                    return true;
                }
        return false;
    }


    private static boolean isEmpty(List<List<Integer>> sudokuMatrix, int row, int col, int num) {
        return (!isTypedInCol(sudokuMatrix, col, num)
                && !isTypedInRow(sudokuMatrix, row, num)
                && !isTypedInSquare(sudokuMatrix, row - row % 3, col - col % 3, num));

    }

    private static List<Integer> findFirstZeroCell(List<List<Integer>> sudokuMatrix) {
        var zeroCellCoords = new ArrayList<>(List.of(-1, -1));
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudokuMatrix.get(row).get(col) == 0) {
                    zeroCellCoords.set(0, row);
                    zeroCellCoords.set(1, col);
                    return zeroCellCoords;
                }
            }
        }
        return zeroCellCoords;
    }
}
