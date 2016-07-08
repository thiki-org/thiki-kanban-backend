package org.thiki.kanban;

import com.jayway.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.thiki.kanban.foundation.common.SequenceNumber;
import org.thiki.kanban.foundation.config.DBInterceptor;

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

    @Autowired
    protected DataSource dataSource;
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    private DBInterceptor dbInterceptor;
    protected SequenceNumber sequenceNumber = Mockito.mock(SequenceNumber.class);

    @BeforeClass
    public static void globalInit() {
        RestAssured.port = port;
    }

    @Before
    public void setUp() {
        when(sequenceNumber.generate()).thenReturn("fooId");
        ReflectionTestUtils.setField(dbInterceptor, "sequenceNumber", sequenceNumber);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @After
    public void resetDB() {
        jdbcTemplate.execute("TRUNCATE TABLE kb_board");
        jdbcTemplate.execute("TRUNCATE TABLE kb_entry");
        jdbcTemplate.execute("TRUNCATE TABLE kb_task");
        jdbcTemplate.execute("TRUNCATE TABLE kb_user_profile");
        jdbcTemplate.execute("TRUNCATE TABLE kb_user_registration");
        jdbcTemplate.execute("TRUNCATE TABLE kb_task_assignment");
    }
}
