package org.thiki.kanban.foundation.common;

import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by xubt on 8/14/16.
 */

@Service
public class VerificationCodeService {
    private static int codeLength = 6;

    public String generate() {
        String charValue = "";
        for (int i = 0; i < codeLength; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }

    public int randomInt(int from, int to) {
        Random random = new Random();
        return from + random.nextInt(to - from);
    }
}
