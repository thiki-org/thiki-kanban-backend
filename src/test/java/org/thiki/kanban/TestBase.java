package org.thiki.kanban;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.thiki.kanban.foundation.aspect.DBInterceptor;
import org.thiki.kanban.foundation.common.SequenceNumber;
import org.thiki.kanban.foundation.security.Constants;

import javax.sql.DataSource;

import static org.mockito.Mockito.when;

/**
 * Created by xubt on 5/14/16.
 */
@SpringApplicationConfiguration({Application.class, TestContextConfiguration.class})
@WebIntegrationTest("server.port:8007")
public class TestBase {
    protected static int port = 8007;
    protected String publicKeyFilePath = "src/main/resources/rsakey.pub";

    protected String userName = "someone";

    @Autowired
    protected DataSource dataSource;

    @Autowired
    protected DBPreparation dbPreparation;
    protected JdbcTemplate jdbcTemplate;
    protected SequenceNumber sequenceNumber = Mockito.mock(SequenceNumber.class);
    @Autowired
    private DBInterceptor dbInterceptor;

    @BeforeClass
    public static void globalInit() {
        RestAssured.port = port;
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addHeader(Constants.HEADER_PARAMS_IDENTIFICATION, "no")
                .addHeader(Constants.HEADER_PARAMS_AUTHENTICATION, "no")
                .build();
        RestAssured.requestSpecification = requestSpecification;
    }

    @Before
    public void setUp() throws Exception {
        when(sequenceNumber.generate()).thenReturn("fooId");
        ReflectionTestUtils.setField(dbInterceptor, "sequenceNumber", sequenceNumber);
        jdbcTemplate = new JdbcTemplate(dataSource);
        dbPreparation.setJDBCTemplate(jdbcTemplate);
    }

    @After
    public void resetDB() {
        jdbcTemplate.execute("TRUNCATE TABLE kb_board");
        jdbcTemplate.execute("TRUNCATE TABLE kb_procedure");
        jdbcTemplate.execute("TRUNCATE TABLE kb_card");
        jdbcTemplate.execute("TRUNCATE TABLE kb_team");
        jdbcTemplate.execute("TRUNCATE TABLE kb_user_profile");
        jdbcTemplate.execute("TRUNCATE TABLE kb_user_registration");
        jdbcTemplate.execute("TRUNCATE TABLE kb_card_assignment");
        jdbcTemplate.execute("TRUNCATE TABLE kb_team_members");
        jdbcTemplate.execute("TRUNCATE TABLE kb_password_retrieval");
        jdbcTemplate.execute("TRUNCATE TABLE kb_password_reset");
        jdbcTemplate.execute("TRUNCATE TABLE kb_team_member_invitation");
        jdbcTemplate.execute("TRUNCATE TABLE kb_notification");
        jdbcTemplate.execute("TRUNCATE TABLE kb_acceptance_criterias");
    }
}
