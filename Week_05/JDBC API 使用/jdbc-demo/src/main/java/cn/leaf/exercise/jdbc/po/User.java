package cn.leaf.exercise.jdbc.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * user
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/2/16 16:02
 */
@Data
@Builder
@AllArgsConstructor
@Table(name = "c_user")
public class User {
    @Id
    @Column(name = "c_id")
    private Integer id;
    @Column(name = "c_name")
    private String name;
    @Column(name = "c_login_name")
    private String loginName;
    @Column(name = "c_password")
    private String password;

    public User() {
    }
}
