package org.eldar;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MainEjercicio5 {

    public static void main(String[] args) {
        String[] myArray = {"FirstWord", "SecondWord", "THIRDWORD"};
        String myArrayJoined = joinStringValues(myArray);
        System.out.println(myArrayJoined);

    }

    private static String joinStringValues(String[] stringArray){
        return Arrays.stream(stringArray)
                .map(String::toLowerCase)
                .collect(Collectors.joining(" "));
    }
}
