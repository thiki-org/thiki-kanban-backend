package org.thiki.kanban;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by xubt on 9/14/16.
 */
@Service
public class DBPreparation {
    private JdbcTemplate jdbcTemplate;
    private String tableName;

    private String fieldsNames;

    private Object[] fieldsValues;

    public void setJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DBPreparation table(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public DBPreparation names(String fieldsNames) {
        this.fieldsNames = fieldsNames;
        return this;
    }

    public DBPreparation values(Object... fieldsValues) {
        this.fieldsValues = fieldsValues;
        return this;
    }

    public void exec() {
        String[] values = new String[this.fieldsValues.length];
        for (int i = 0; i < this.fieldsValues.length; i++) {
            values[i] = "'" + this.fieldsValues[i] + "'";
        }
        String sql = String.format("INSERT INTO %s (%s) values(%s)", tableName, fieldsNames, StringUtils.join(values, ",").replace("''", "'"));
        jdbcTemplate.execute(sql);
    }
}
