package org.thiki.kanban.foundation.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;

import javax.annotation.Resource;

import static org.junit.Assert.assertTrue;

/**
 * Created by xubt on 8/14/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class VerificationCodeServiceTest extends TestBase {
    @Resource
    private VerificationCodeService verificationCodeService;

    @Test
    public void generateVerificationCode() {
        String newVerificationCode = verificationCodeService.generate();
        assertTrue(newVerificationCode.length() == 6);
    }
}
