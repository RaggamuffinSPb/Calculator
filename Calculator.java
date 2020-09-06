package com.ragga.calculator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    /**
     * Метод count(String text) используется для вычисления арифметичекого выражения,
     * поступившего в text. Предполагается, что приведённое выражение содержит два числа
     * и знак действия: +,-,*,/
     * @param  text - строка с арифметическим выражением, тип "число знак число"
     * @return String
     */
    private String counter(String text) { //3+4   5-6   -5-6
        Pattern digitPattern = Pattern.compile("(-?\\d+\\.\\d+|-?\\d+)([\\+\\-\\*\\/])(-?\\d+\\.\\d+|-?\\d+)");
        Matcher matcher = digitPattern.matcher(text);
        double value_1 = 0, value_2 = 0;
        char action = ' ';
        while (matcher.find()) {
            value_1 = Double.valueOf(matcher.group(1));
            value_2 = Double.valueOf(matcher.group(3));
            action = matcher.group(2).charAt(0);
        }
        if (action!=' ') {
            switch (action) {
                case '+': return (value_1+value_2)+"";
                case '-': return (value_1-value_2)+"";
                case '*': return (value_1*value_2)+"";
                case '/': {
                    if (value_2 != 0) return (value_1/value_2)+"";
                    else throw new RuntimeException("Unexpected action: dividing by zero");
                }
            }
        }
        return text;
    }

    /**
     * Передаёт строку text методу solve(), получает численное значение в строковом представлении
     * Форматирует и возвращает пользователю
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

    public String extractFromBrackets(String text, int start, int end) {
        StringBuilder result = new StringBuilder();
        boolean trigger = false;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '(') {
                trigger = true;
                continue;
            }
            if (text.charAt(i) == ')') {
                trigger = false;
                return solve(result.toString());
            }

            if (trigger) result.append(text.charAt(i));
        }
        return solve(result.toString());
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
            String formattedDouble = new DecimalFormat("#0.0000").format(Double.valueOf(result));


            return null;
        } else return result;
    }


}
// 1 + (2 + (3+4) + 5) + (6+7)
//-5*2  -6/-2   -2-1    если есть * /, то умножаем-делим, иначе суммируем в т.ч. отрицательные