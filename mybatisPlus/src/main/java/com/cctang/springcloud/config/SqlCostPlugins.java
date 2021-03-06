package com.cctang.springcloud.config;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
/**
 * @author cctang
 * @version 1.0
 * @date 2021/9/6 23:12
 * @description
 */
//@Intercepts({@Signature(type = StatementHandler.class, method = "update", args = {Statement.class,}),
        //@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class})})
//public final class SqlCostPlugins implements Interceptor {

//    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(SqlCostPlugins.class);
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        long startTime = System.currentTimeMillis();
//        try {
//            return invocation.proceed();
//        } finally {
//            long endTime = System.currentTimeMillis();
//            long elapsedTime = endTime - startTime;
//            String sql = formatSql(invocation);
//            printColorString(String.format("cost %s ms,??????SQL:\n %s ", elapsedTime, sql), elapsedTime);
//        }
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//
//    }
//
//    /**
//     * ?????????SQL????????????
//     *
//     * @param invocation invocation
//     * @return java.lang.String
//     * @author liekkas 2020/12/08 10:43
//     */
//    private String formatSql(Invocation invocation) throws NoSuchFieldException, IllegalAccessException {
//        //??????StatementHandler
//        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
//        //??????ParameterHandler
//        ParameterHandler parameterHandler = statementHandler.getParameterHandler();
//        //??????boundSql
//        BoundSql boundSql = statementHandler.getBoundSql();
//
//        Class<? extends ParameterHandler> parameterHandlerClass = parameterHandler.getClass();
//        Field mappedStatementField = parameterHandlerClass.getDeclaredField("mappedStatement");
//        mappedStatementField.setAccessible(true);
//        MappedStatement mappedStatement = (MappedStatement) mappedStatementField.get(parameterHandler);
//
//        String sql = boundSql.getSql();
//
//        // ??????sql??????????????????
//        if (Objects.isNull(sql)) {
//            return "";
//        }
//
//        // ??????sql
//        sql = beautifySql(sql).toLowerCase();
//
//        // ?????????????????????????????????Sql????????????????????????
//        Object parameterObject = parameterHandler.getParameterObject();
//        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();
//        if (Objects.isNull(parameterObject) || parameterMappingList.isEmpty()) {
//            return sql;
//        }
//
//        // ???????????????????????????????????????sql???????????????????????????
//        String sqlWithoutReplacePlaceholder = sql;
//
//        try {
//            sql = handleCommonParameter(boundSql, mappedStatement);
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//            // ????????????????????????????????????????????????????????????????????????????????????????????????sql
//            return sqlWithoutReplacePlaceholder;
//        }
//
//        return sql;
//    }
//
//    /**
//     * ??????SQL
//     *
//     * @param sql sql
//     * @return java.lang.String
//     * @author liekkas 2020/12/08 10:45
//     */
//    private String beautifySql(String sql) {
//        sql = sql.replaceAll("[\\s\n ]+", " ");
//        return sql;
//    }
//
//    /**
//     * ??????SQL???????,??????sql??????
//     *
//     * @param boundSql boundSql
//     * @param mappedStatement mappedStatement
//     * @return java.lang.String
//     * @author liekkas 2020/12/08 10:46
//     */
//    private String handleCommonParameter(BoundSql boundSql, MappedStatement mappedStatement) {
//
//        String sql = boundSql.getSql();
//        Object parameterObject = boundSql.getParameterObject();
//        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//        Configuration configuration = mappedStatement.getConfiguration();
//        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
//
//        for (ParameterMapping parameterMapping : parameterMappings) {
//            if (parameterMapping.getMode() != ParameterMode.OUT) {
//                Object value;
//                String propertyName = parameterMapping.getProperty();
//                if (boundSql.hasAdditionalParameter(propertyName)) {
//                    value = boundSql.getAdditionalParameter(propertyName);
//                } else if (parameterObject == null) {
//                    value = null;
//                } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
//                    value = parameterObject;
//                } else {
//                    MetaObject metaObject = configuration.newMetaObject(parameterObject);
//                    value = metaObject.getValue(propertyName);
//                }
//                sql = replacePlaceholder(sql, value);
//            }
//        }
//        return sql;
//    }
//
//    /**
//     * ???????????????propertyValue??????,??????SQL??????????????????????
//     *
//     * @param sql sql
//     * @param propertyValue propertyValue
//     * @return java.lang.String
//     * @author liekkas 2020/12/08 10:48
//     */
//    private String replacePlaceholder(String sql, Object propertyValue) {
//        String value;
//        if (Objects.nonNull(propertyValue)) {
//            if (propertyValue instanceof String) {
//                value = "'" + propertyValue + "'";
//            } else if (propertyValue instanceof Date) {
//                value = "'" + DATE_TIME_FORMATTER
//                        .format(((Date) propertyValue).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
//                        + "'";
//            } else if (propertyValue instanceof LocalDate) {
//                value = "'" + DATE_FORMATTER.format((LocalDate) propertyValue) + "'";
//            } else if (propertyValue instanceof LocalDateTime) {
//                value = "'" + DATE_TIME_FORMATTER.format((LocalDateTime) propertyValue) + "'";
//            } else {
//                value = propertyValue.toString();
//            }
//        } else {
//            value = "null";
//        }
//        return sql.replaceFirst("\\?", value);
//    }
//
//    /**
//     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
//     *
//     * @param str Str
//     * @param timeOut ????????????
//     * @author liekkas 2020/12/08 10:50
//     */
//    private void printColorString(String str, Long timeOut) {
//
//        if (timeOut < Constant.DEFAULT_TIME_OUT) {
//            LOGGER.info("-----------------------------------------------------------------------");
//
//            LOGGER.info("\033[33;4m" + str + "\033[0m");
//            LOGGER.info("-----------------------------------------------------------------------");
//        } else {
//            LOGGER.error("-----------------------------------------------------------------------");
//            LOGGER.error("\033[31;4m" + str + "\033[0m");
//            LOGGER.error("-----------------------------------------------------------------------");
//        }
//    }
//
//    /**
//     * <p>
//     * ???????????????????????????????????????
//     * </p>
//     *
//     * @author liekkas 2020/12/08 10:52
//     */
//    private static class Constant {
//        public static final Long DEFAULT_TIME_OUT = 5000L;
//    }
//}

