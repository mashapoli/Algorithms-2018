package lesson6;

import kotlin.NotImplementedError;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
                    maxLen[i][j] = 0;
                } else if (first.charAt(i - 1) == second.charAt(j - 1)) {
                    maxLen[i][j] = maxLen[i - 1][j - 1] + 1;
                } else {
                    maxLen[i][j] = Math.max(maxLen[i - 1][j], maxLen[i][j - 1]);
                }
            }
        }

        StringBuilder res = new StringBuilder(Math.max(lenFirst, lenSecond));
        int i = lenFirst;
        int j = lenSecond;
        while (i > 0 && j > 0) {
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
        if (list.isEmpty()) {
            return list;
        }
        int len = list.size();
        int[] dp = new int[len];
        int[] prev = new int[len];
        for (int i = 0; i < len; i++) {
            dp[i] = 1;
            prev[i] = -1;
            for (int j = 0; j < i; j++) {
                if (list.get(j) < list.get(i) && (dp[j] + 1) > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
        }
        int pos = 0;
        int lisLen = dp[0];
        for (int i = 0; i < len; i++) {
            if (dp[i] > lisLen) {
                pos = i;
                lisLen = dp[i];
            }
        }

        List<Integer> answer = new ArrayList<>();
        while (pos != -1) {
            answer.add(list.get(pos));
            pos = prev[pos];
        }
        Collections.reverse(answer);
        return answer;
    }

    public static List<Integer> longestIncreasingSubSequenceONLogN(List<Integer> list) {
        if (list.isEmpty()) {
            return list;
        }
        int len = list.size();
        int[] dp = new int[len + 1];
        int[] pos = new int[len + 1];
        int[] prev = new int[len];
        int lisLen = 0;
        pos[0] = -1;
        dp[0] = Integer.MIN_VALUE;
        for (int i = 1; i < len + 1; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < len; i++) {
            int j = Arrays.binarySearch(dp, list.get(i));
            if (j < 0) {
                j = -(j + 1);
            }
            if (dp[j - 1] < list.get(i) && list.get(i) < dp[j]) {
                dp[j] = list.get(i);
                pos[j] = i;
                prev[i] = pos[j - 1];
                lisLen = Math.max(lisLen, j);
            }
        }

        List<Integer> answer = new ArrayList<>();
        int p = pos[lisLen];
        while (p != -1) {
            answer.add(list.get(p));
            p = prev[p];
        }
        Collections.reverse(answer);
        return answer;
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
