package com.ragga.calculator;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Calculator c = new Calculator();
        ArrayList<String> expressions = new ArrayList<String>();
        expressions.add("1+1/2");
        expressions.add("1+1/2+1/4");
        expressions.add("1+1/2+1/4+1/8");
        expressions.add("1+1/2+1/4+1/8+1/16");
        expressions.add("1+1/2+1/4+1/8+1/16+1/32");
        expressions.add("1+1/2+1/4+1/8+1/16+1/32+1/64");
        expressions.add("1+1/2+1/4+1/8+1/16+1/32+1/64+1/128");
        expressions.add("1+1/2+1/4+1/8+1/16+1/32+1/64+1/128+1/256");
        expressions.add("1+1/2+1/4+1/8+1/16+1/32+1/64+1/128+1/256+1/512");
        expressions.add("1+1/2+1/4+1/8+1/16+1/32+1/64+1/128+1/256+1/512+1/1024");
        expressions.add("1+1/2+1/4+1/8+1/16+1/32+1/64+1/128+1/256+1/512+1/1024+1/2048");
        expressions.add("1+1/2+1/4+1/8+1/16+1/32+1/64+1/128+1/256+1/512+1/1024+1/2048+1/4096");
        expressions.add("1+1/2+1/4+1/8+1/16+1/32+1/64+1/128+1/256+1/512+1/1024+1/2048+1/4096+1/8192");
        expressions.add("1+1/2+1/4+1/8+1/16+1/32+1/64+1/128+1/256+1/512+1/1024+1/2048+1/4096+1/8192+1/16384");
        expressions.add("1+1/2+1/4+1/8+1/16+1/32+1/64+1/128+1/256+1/512+1/1024+1/2048+1/4096+1/8192+1/16384+1/32768");
        expressions.add("1+1/2+1/4+1/8+1/16+1/32+1/64+1/128+1/256+1/512+1/1024+1/2048+1/4096+1/8192+1/16384+1/32768+1/65536");

        for (String s : expressions
             ) {
            System.out.println(c.evaluate(s,false));
        }



    }

}
