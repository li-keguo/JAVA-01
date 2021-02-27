## 作业总览

##### 作业

第一节
1、（选做）使 Java 里的动态代理，实现一个简单的 AOP。
2、（必做）写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）,
提交到 Github。
3、（选做）实现一个 Spring XML 自定义配置，配置一组 Bean，例如：
Student/Klass/School。
4、（选做，会添加到高手附加题）
4.1 （挑战）讲网关的 frontend/backend/filter/router 线程池都改造成 Spring 配置方式；
4.2 （挑战）基于 AOP 改造 Netty 网关，filter 和 router 使用 AOP 方式实现；
4.3 （中级挑战）基于前述改造，将网关请求前后端分离，中级使用 JMS 传递消息；
4.4 （中级挑战）尝试使用 ByteBuddy 实现一个简单的基于类的 AOP；
4.5 （超级挑战）尝试使用 ByteBuddy 与 Instrument 实现一个简单 JavaAgent 实现无侵入
下的 AOP。
第二节

1. （选做）总结一下，单例的各种写法，比较它们的优劣。

2. （选做）maven/spring 的 profile 机制，都有什么用法？
3. （必做）给前面课程提供的 Student/Klass/School 实现自动配置和 Starter。
4. （选做）总结 Hibernate 与 MyBatis 的各方面异同点。
5. （选做）学习 MyBatis-generator 的用法和原理，学会自定义 TypeHandler 处理复杂类型。
6. （必做）研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：
1）使用 JDBC 原生接口，实现数据库的增删改查操作。
2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
3）配置 Hikari 连接池，改进上述操作。提交代码到 Github。
附加题（可以后面上完数据库的课再考虑做）：
1. (挑战)基于 AOP 和自定义注解，实现 @MyCache(60) 对于指定方法返回值缓存60秒。
2. (挑战)自定义实现一个数据库连接池，并整合 Hibernate/Mybatis/Spring/SpringBoot。
3. (挑战)基于 MyBatis 实现一个简单的分库分表+读写分离+分布式 ID 生成方案。

#### 完成情况

##### Spring Bean的装配

使用多种方式对spring bean进行初始化，有注解方式、XML配置方式，properties配置方式。其中注解方式最多，例如@Component,@Service @Controller等等，利用spring注解标注传递的特性自定义注解装配， 使用xml配置了一个简单的bean，使用properties方式配合@Value和@Configuration等注解实现属性的配置对bean装配，

##### 写一个Spring boot starter 

实现了一个简单的springboot-starter ，使用@Configuration的方式装配bean到spring容器，

##### 使用jdbc操作数据库

实现一个简单的ORM框架，封装一个简单的SuperBaseDAO抽象接口，接口提供单表的CRUD操作，需对相关po映射打上persistence相关注解，具体使用代码如下： 

```java
// DAO
public interface UserDAO extends BaseDAO<User> {
}
// PO
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
// update 
     User user = User.builder()
              .id(7)
              .name("test1-update")
              .loginName("test1-update")
              .password("123-update")
              .build();
     userDAO.update(user)
// select
   List<User> users = userDAO.selectAll();
   List<User> users = userDAO.selectByLimit(3);
   List<User> users = userDAO.selectByLimit(1, 2);
// 批处理插入
   List<User> users = new ArrayList<>();
   for (int i = 1; i <= 100; i++) {
       users.add(User.builder()
               .id(7 + i)
               .name("test1" + i)
               .loginName("test1" + i)
               .password("123" + i)
               .build());
   }
   userDAO.inserts(users);
```

##### 单例

总结单例的使用实现方法，总共实现了7钟，有的很类似，取巧了，详情见代码（AOP、单例、及其他简单作业文件夹下项目）

##### sop

使用jdk动态代理和cglib动态代理实现对一个简单方法的切入，详情见代码（AOP、单例、及其他简单作业文件夹下项目）

##### 

