package ru.hh.price.utils;

import java.util.Random;

/**
 *
 * @author Hanna_Aliakseichykava
 */
public class MathUtils {

    //[min, max)
    public static int getRandomNumber(final int min, final int max) {
        return new  Random().nextInt(max - min) + min;
    }
}
