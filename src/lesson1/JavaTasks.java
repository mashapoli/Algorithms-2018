package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
     * каждый на отдельной строке. Пример:
     *
     * 13:15:19
     * 07:26:57
     * 10:00:03
     * 19:56:14
     * 13:15:19
     * 00:40:31
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 00:40:31
     * 07:26:57
     * 10:00:03
     * 13:15:19
     * 13:15:19
     * 19:56:14
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) throws IOException {
        List<Integer> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputName))) {
            for (String line; (line = br.readLine()) != null; ) {
                if (!line.matches("[0-2]\\d:[0-6]\\d:[0-6]\\d")) {
                    throw new IllegalArgumentException("Invalid format");
                }
                int res = 0;
                String[] parts = line.split(":");
                for (int i = 0; i < parts.length; i++) {
                    res = res * 60 + Integer.parseInt(String.valueOf(parts[i]));
                    if (i == parts.length - 1) {
                        list.add(res);
                        res = 0;
                    }
                }
            }
        }
        list.sort(Comparator.naturalOrder());
        StringBuilder stringBuilder = new StringBuilder();
        int listSize = list.size();
        int hour;
        int minute;
        int second;
        for (int i = 0; i < listSize; i++) {
            int seconds = list.get(i);
            hour = seconds / 3600;
            minute = (seconds % 3600) / 60;
            second = seconds % 60;
            stringBuilder.append(String.format("%02d:%02d:%02d", hour, minute, second));
            stringBuilder.append("\n");
        }
        Files.write(Paths.get(outputName), Collections.singleton(stringBuilder), Charset.defaultCharset());
    }

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        int min = 5000;
        int max = -2730;
        try (BufferedReader br = new BufferedReader(new FileReader(inputName))) {
            for (String line; (line = br.readLine()) != null; ) {
                if (!line.matches("[\\+-]?0*\\d+\\.\\d")) {
                    throw new IllegalArgumentException("Invalid format");
                }
                double temp = Double.valueOf(line);
                if ((temp < -273.0 || temp > 500.0)) {
                    throw new IllegalArgumentException("Wrong range");
                }
                String strTemp = String.valueOf(temp);
                int intTemp = (int) (temp * 10);
                if (intTemp < min) {
                    min = intTemp;
                }
                if (intTemp > max) {
                    max = intTemp;
                }
            }
        }
        int[] buckets = new int[max - min + 1];
        try (BufferedReader br = new BufferedReader(new FileReader(inputName))) {
            for (String line; (line = br.readLine()) != null; ) {
                double temp = Double.valueOf(line);
                int intTemp = (int) (temp * 10);
                buckets[intTemp - min]++;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputName))) {
            for (int bucketIdx = 0; bucketIdx < buckets.length; bucketIdx++) {
                int bucketValue = buckets[bucketIdx];
                if (bucketValue != 0) {
                    double doubTemp = (bucketIdx + min) / 10.0;
                    for (int i = 0; i < bucketValue; i++) {
                        writer.write(String.valueOf(doubTemp));
                        writer.write("\n");
                    }
                }
            }
        }

    }




    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
