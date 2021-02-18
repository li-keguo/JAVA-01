package cn.leaf.exercise.jdbc.common;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
@Component
public class JdbcSession implements Closeable {
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();

    private final DataSource dataSource;

    public synchronized Connection getConnection() throws SQLException {

        if (CONNECTION_THREAD_LOCAL.get() == null || CONNECTION_THREAD_LOCAL.get().isClosed()) {
            CONNECTION_THREAD_LOCAL.set(dataSource.getConnection());
            return CONNECTION_THREAD_LOCAL.get();
        }
        return CONNECTION_THREAD_LOCAL.get();
    }

    @SneakyThrows
    @Override
    public synchronized void close() {
        if (CONNECTION_THREAD_LOCAL.get() != null) {
            CONNECTION_THREAD_LOCAL.get().close();
            CONNECTION_THREAD_LOCAL.remove();
        }
    }

}