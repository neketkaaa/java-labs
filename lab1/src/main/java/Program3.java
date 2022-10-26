public class Program3 {

    /**
     * Метод flattenMatrix преобразует матрицу размера nxm в одномерный массив, записывая сперва элементы первого столбца,
     * затем элементы второго столбца и т.д.
     *
     * @param matrix матрица размера nxm
     * @return одномерный массив
     * ПРИМЕР:
     * Вход: matrix = [[1, 2, 3],
     *                 [4, 5, 6],
     *                 [7, 8, 9]]
     * Выход: [1, 4, 7, 2, 5, 8, 3, 6, 9]
     */

    public static int[] flattenMatrix(int[][] matrix) {
        int[] new_array = new int[matrix.length * matrix[0].length];
        int num = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                new_array[num] = matrix[j][i];
                num++;
            }
        }

        return new_array;
    }

}