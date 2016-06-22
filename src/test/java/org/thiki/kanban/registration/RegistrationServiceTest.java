package org.thiki.kanban.registration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.Application;
import org.thiki.kanban.TestContextConfiguration;

/**
 * Created by mac on 6/22/16.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration({Application.class, TestContextConfiguration.class})
public class RegistrationServiceTest {

    @Test
    public void testRegisterNewUser(){
        RegistrationService cut = new RegistrationService();
//        UserRegistration a = cut.registerNewUser(null, null, null, null, null);

    }

}