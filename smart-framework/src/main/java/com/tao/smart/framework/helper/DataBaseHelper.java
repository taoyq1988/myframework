package com.tao.smart.framework.helper;

import com.tao.smart.framework.utils.PropsUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库操作助手
 *
 * @author tyq
 * @version 1.0, 2017/11/30
 */
public final class DataBaseHelper {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DataBaseHelper.class);

    /**
     * 用于存放当前线程的 sql 链接对象
     */
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();

    /**
     * 查询更新 助手
     */
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    /**
     * 用于dbcp链接池的数据源
     */
    private static final BasicDataSource BASIC_DATA_SOURCE;

    static {
        Properties props = PropsUtils.loadProps("db.properties");
        String driverClassName = PropsUtils.getString(props,"jdbc.driverClassName");
        String url = PropsUtils.getString(props,"jdbc.url");
        String username = PropsUtils.getString(props,"jdbc.username");
        String password = PropsUtils.getString(props,"jdbc.password");

        BASIC_DATA_SOURCE = new BasicDataSource();
        BASIC_DATA_SOURCE.setDriverClassName(driverClassName);
        BASIC_DATA_SOURCE.setUrl(url);
        BASIC_DATA_SOURCE.setUsername(username);
        BASIC_DATA_SOURCE.setPassword(password);
    }

    public static DataSource getDataSource(){
        return BASIC_DATA_SOURCE;
    }

    public static Connection getConnection() {
        Connection connection = CONNECTION_HOLDER.get();
        if (connection == null) {
            try {
                connection = BASIC_DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("get connection fail ", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(connection);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        Connection connection = CONNECTION_HOLDER.get();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("close connection fail ", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 开启事务
     */
    public static void beginTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("begin transaction fail ", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(connection);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.commit();
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("commit transaction fail ", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    public static void rollbackTransaction() {
        Connection conn = getConnection();
        if(conn != null){
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("rollback transaction fail ",e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }
}
