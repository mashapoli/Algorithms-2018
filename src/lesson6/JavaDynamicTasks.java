package lesson6;

import kotlin.NotImplementedError;

import java.util.List;

import static java.lang.Math.addExact;
import static java.lang.Math.max;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        int lenFirst = first.length();
        int lenSecond = second.length();
        int maxLen[][] = new int[lenFirst + 1][lenSecond + 1];
        for (int i = 0; i < lenFirst + 1; i++) {
            for (int j = 0; j < lenSecond + 1; j++) {
                if (i == 0 || j == 0) {
                    maxLen[i][j] = 0; //если какая-то строка пустая
                } else if (first.charAt(i - 1) == second.charAt(j - 1)) {
                    maxLen[i][j] = maxLen[i - 1][j - 1] + 1; //последние символы строк совпадают,отбрасываем последние символы рассматриваемых строк и +1
                } else {
                    maxLen[i][j] = Math.max(maxLen[i - 1][j], maxLen[i][j - 1]); //отбрасываем по одному символу от конца строк
                }
            }
        }

        StringBuffer res = new StringBuffer(Math.max(lenFirst, lenSecond));
        int i = lenFirst;
        int j = lenSecond;
        while (i > 0 && j > 0) {//обратный проход
            if (first.charAt(i - 1) == second.charAt(j - 1)) {
                res.append(first.charAt(i - 1));
                i--;
                j--;
            } else if (maxLen[i - 1][j] == maxLen[i][j]) {
                i--;
            } else {
                j--;
            }
        }
        return res.reverse().toString();

    }



    /**
     * Наибольшая возрастающая подпоследовательность
     * Средняя
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        throw new NotImplementedError();
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Сложная
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
