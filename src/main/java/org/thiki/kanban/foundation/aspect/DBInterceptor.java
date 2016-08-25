package org.thiki.kanban.foundation.aspect;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Service;
import org.springframework.test.util.ReflectionTestUtils;
import org.thiki.kanban.foundation.common.SequenceNumber;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Properties;

/**
 * Created by xubt on 5/18/16.
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class})})
@Service
public class DBInterceptor implements Interceptor {

    @Resource
    private SequenceNumber sequenceNumber;

    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement stmt = (MappedStatement) invocation.getArgs()[0];
        Object entityToSave = invocation.getArgs()[1];
        if (stmt == null) return invocation.proceed();
        if (stmt.getSqlCommandType().equals(SqlCommandType.INSERT)) {
            ((Map) entityToSave).values().stream().filter(param -> !isJavaClass(param.getClass())).forEach(param -> {
                ReflectionTestUtils.setField(param, "id", sequenceNumber.generate());
            });
        }
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
    }

    public static boolean isJavaClass(Class<?> clz) {
        return clz != null && clz.getClassLoader() == null;
    }
}
