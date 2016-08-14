package org.thiki.kanban.foundation.common;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by xubt on 8/14/16.
 */
public class VerificationCodeTest {

    @Test
    public void generateVerificationCode() {
        String verificationCode = VerificationCode.generate();
        assertTrue(verificationCode.length() == 6);
    }
}
