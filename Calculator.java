package com.ragga.calculator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    /**
     * Метод counter(String text) используется для вычисления арифметичекого выражения,
     * поступившего в text. Предполагается, что приведённое выражение содержит два числа
     * и знак действия: +,-,*,/
     * @param  text - строка с арифметическим выражением, тип "число знак число"
     * @return String - результат в текстовом представлении
     */
    private String counter(String text) { //3+4   5-6   -5-6
        Pattern digitPattern = Pattern.compile("(-?\\d+\\.\\d+|-?\\d+)([\\+\\-\\*\\/])(-?\\d+\\.\\d+|-?\\d+)");
        Matcher matcher = digitPattern.matcher(text);
        double value_1 = 0, value_2 = 0;
        char action = ' ';
        if (matcher.find()) {
            value_1 = Double.valueOf(formatter(matcher.group(1)));
            value_2 = Double.valueOf(formatter(matcher.group(3)));
            action = matcher.group(2).charAt(0);
        }
        if (action!=' ') {
            switch (action) {
                case '+': return formatter((value_1+value_2)+"");
                case '-': return formatter((value_1-value_2)+"");
                case '*': return formatter((value_1*value_2)+"");
                case '/': {
                    if (value_2 != 0) return formatter((value_1/value_2)+"");
                    else throw new RuntimeException("Unexpected action: dividing by zero");
                }
            }
        }
        return text;
    }

    /**
     * Ищет все умножения и деления в полученной строке
     * Вызывает метод counter() для участка число умножить/делить число
     * @param text - строка с арифметическим выражением, тип "число знак число"
     * @return String - double число в строковом представлении
     */
    private String findAllMuldiv(String text) {

        Pattern digitPattern = Pattern.compile("(-?\\d+\\.\\d+|-?\\d+)([\\*\\/])(-?\\d+\\.\\d+|-?\\d+)");
        Matcher matcher = digitPattern.matcher(text);

        if (matcher.find()) {
            text = matcher.replaceFirst(counter(matcher.group(0)));
            return findAllMuldiv(text);
        } return text;

    }

    /**
     * Ищет все сложения и вычитания в полученной строке
     * Вызывает метод counter() для участка число сложить/вычесть число
     * @param text - строка с арифметическим выражением, тип "число знак число"
     * @return String - double число в строковом представлении
     */
    private String findAllPlusminus(String text) {
        Pattern digitPattern = Pattern.compile("(-?\\d+\\.\\d+|-?\\d+)([\\+\\-])(-?\\d+\\.\\d+|-?\\d+)");
        Matcher matcher = digitPattern.matcher(text);

        if (matcher.find()) {
            text = matcher.replaceFirst(counter(matcher.group(0)));
            return findAllPlusminus(text);
        }
        return text;
    }
    private String solve(String text) {
        Pattern digitPattern = Pattern.compile("(\\()([^\\(\\)]*)(\\))");
        Matcher matcher = digitPattern.matcher(text);

        while (matcher.find()) {
            String brackets = solve(matcher.group(2));
            text = matcher.replaceFirst(brackets);
            matcher = digitPattern.matcher(text);
        }

        text = findAllMuldiv(text);
        text = findAllPlusminus(text);

        return text;
    }

    public String evaluate(String text,boolean toDo) {
        String result = solve(text);
        if (toDo) {
            try {
                String formattedDouble = new DecimalFormat("#0.0000").format(Double.valueOf(result));
                if (formattedDouble != null){

                    return formattedDouble;
                }
            } catch (NumberFormatException e) {
                return null;
            }

            return null;
        } else return result;
    }
    /**
     * Костыльное форматирование для отсутствия конфликтов регулярного выражения с представлениями
     * числа double в виде x.xE-y до 12го знака после запятой
     * Точность - вежливость снайперов, а не моя.
     * @param  text - строка с арифметическим выражением, тип "число знак число"
     * @return String - результат в текстовом представлении
     */
    public String formatter(String text) {
        String formattedDouble = new DecimalFormat("#0.000000000000").format(Double.valueOf(text)).replace(',','.');
        return formattedDouble;
    }


}
