package a1.pool;

import java.sql.Connection;

public interface ConnectionPool {

    /**
     * 释放链接
     * @param connection
     */
    public void releseConnection(Connection connection);

    /**
     * 获取链接
     * @param mills
     * @return
     */
    public Connection fetchConnection(long mills) throws Exception;
}
