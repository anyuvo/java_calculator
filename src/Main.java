import java.util.*;

class Calculator {
    private static final TreeMap<Integer, String> ROMAN_NUMERALS = new TreeMap<>();

    static {
        ROMAN_NUMERALS.put(1000, "M");
        ROMAN_NUMERALS.put(900, "CM");
        ROMAN_NUMERALS.put(500, "D");
        ROMAN_NUMERALS.put(400, "CD");
        ROMAN_NUMERALS.put(100, "C");
        ROMAN_NUMERALS.put(90, "XC");
        ROMAN_NUMERALS.put(50, "L");
        ROMAN_NUMERALS.put(40, "XL");
        ROMAN_NUMERALS.put(10, "X");
        ROMAN_NUMERALS.put(9, "IX");
        ROMAN_NUMERALS.put(5, "V");
        ROMAN_NUMERALS.put(4, "IV");
        ROMAN_NUMERALS.put(1, "I");
    }

    public static String arabicToRoman(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("В римской системе нет отрицательных чисел и нуля.");
        }

        int l = ROMAN_NUMERALS.floorKey(number);
        if (number == l) {
            return ROMAN_NUMERALS.get(number);
        }
        return ROMAN_NUMERALS.get(l) + arabicToRoman(number - l);
    }

    public static int romanToArabic(String number) {
        String romanNumeral = number.toUpperCase();
        int result = 0;
        int i = 0;

        for (Integer key : ROMAN_NUMERALS.descendingKeySet()) {
            String numeral = ROMAN_NUMERALS.get(key);
            while (romanNumeral.startsWith(numeral, i)) {
                result += key;
                i += numeral.length();
            }
        }

        if (arabicToRoman(result).equals(romanNumeral)) {
            return result;
        } else {
            throw new IllegalArgumentException("Некорректное римское число.");
        }
    }

    public static String calc(String input) {
        try {
            String[] tokens = input.split(" ");
            if (tokens.length != 3) {
                throw new IllegalArgumentException("Строка не соответствует формату 'a операция b'.");
            }

            String operation = tokens[1];
            boolean isRoman = tokens[0].matches("^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");

            int a, b;
            if (isRoman == tokens[2].matches("^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$")) {
                if (isRoman) {
                    a = romanToArabic(tokens[0]);
                    b = romanToArabic(tokens[2]);
                } else {
                    a = Integer.parseInt(tokens[0]);
                    b = Integer.parseInt(tokens[2]);
                }
                if (a < 1 || a > 10 || b < 1 || b > 10) {
                    throw new IllegalArgumentException("Числа должны быть в диапазоне от 1 до 10 включительно.");
                }
            } else {
                throw new IllegalArgumentException("Числа должны быть в одной и той же системе счисления.");
            }

            int result;
            switch (operation) {
                case "+":
                    result = a + b;
                    break;
                case "-":
                    result = a - b;
                    break;
                case "*":
                    result = a * b;
                    break;
                case "/":
                    result = a / b;
                    break;
                default:
                    throw new IllegalArgumentException("Операция не поддерживается.");
            }

            return isRoman ? arabicToRoman(result) : String.valueOf(result);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка при обработке запроса: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        try {
            String result = Calculator.calc(input);
            System.out.println(result);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}