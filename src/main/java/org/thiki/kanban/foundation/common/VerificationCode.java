package org.thiki.kanban.foundation.common;

import java.util.Random;

/**
 * Created by xubt on 8/14/16.
 */
public class VerificationCode {
    private static int codeLength = 6;

    public static String generate() {
        String charValue = "";
        for (int i = 0; i < codeLength; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }

    public static int randomInt(int from, int to) {
        Random random = new Random();
        return from + random.nextInt(to - from);
    }
}
