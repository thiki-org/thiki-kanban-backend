package org.thiki.kanban;

import com.jayway.restassured.RestAssured;
import org.junit.After;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by xubt on 5/14/16.
 */
@SpringApplicationConfiguration({Application.class, TestContextConfiguration.class})
@WebIntegrationTest("server.port:8007")
public class TestBase {
    protected static int port = 8007;

    @Autowired
    protected DataSource db;
    protected JdbcTemplate jdbcTemplate;

    @BeforeClass
    public static void globalInit() {
        RestAssured.port = port;
    }

    @After
    public void resetDB() {
        jdbcTemplate.execute("TRUNCATE TABLE kb_entry");
        jdbcTemplate.execute("TRUNCATE TABLE kb_task");
    }
}
