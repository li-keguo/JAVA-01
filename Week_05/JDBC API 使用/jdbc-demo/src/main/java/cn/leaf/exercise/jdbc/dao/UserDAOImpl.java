package cn.leaf.exercise.jdbc.dao;

import cn.leaf.exercise.jdbc.common.JdbcSession;
import cn.leaf.exercise.jdbc.po.User;
import cn.leaf.exercise.jdbc.support.SuperDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * user dao
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/2/16 15:13
 */
@Component
@RequiredArgsConstructor
public class UserDAOImpl extends SuperDAO<User> implements UserDAO {

    private final JdbcSession jdbcSession;

    @Override
    public JdbcSession getJdbcSession() {
        return jdbcSession;
    }
}
