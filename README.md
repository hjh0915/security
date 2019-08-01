## 学习内容

    hsecurity01 shiro官网示例 安全机制包含user(password), role
        http://shiro.apache.org/tutorial.html
        http://shiro.apache.org/architecture.html
        https://shiro.apache.org/spring-boot.html
    
    hsecurity02 Introduction to Apache Shiro
        不用ini配置文件，直接构建realm
        https://github.com/eugenp/tutorials/tree/master/apache-shiro

    hsecurity03 继承JdbcRealm，自定义Realm
    hsecurity04 采用SimpleAccountRealm 只有user(password), role两部分，不包含permission

    hsecurity05 JdbcRealm
        直接看shiro源码，有3个表users, user_roles, roles_permissions
        根据hsecurity01的shiro.ini来写入表
        增加spring-boot-starter-jdbc自动拿到DataSource的Bean
        https://github.com/apache/shiro/blob/master/core/src/main/java/org/apache/shiro/realm/jdbc/JdbcRealm.java

        setPermissionsLookupEnabled(true or false)
        此方法存在，应该是为了极度简化user, role两部分即可解决安全权限

    hsecurity06 隔离Bean配置
        单独ShiroConfig设置shiro, 主程序保持简单
        ShiroAutoConfiguration已经自动装配了securityManager
        对于默认的JdbcReam中的指定表，可以采用不同的表名及sql来处理

    hsecurity07 初步完成shiro login
        controller采用手动验证
        https://github.com/eugenp/tutorials/tree/master/apache-shiro

    hsecurity08 采用salt加密密码
        更改users表字段长度和类型，schema.sql
        使用junit test方式插入一个用户信息
        仍然使用JdbcRealm
            
        realm.setSaltStyle(SaltStyle.COLUMN);
        realm.setCredentialsMatcher(hashedCredentialsMatcher());

## 数据库

    sudo su
    su - postgres
    createdb -E utf8 -O jxyz hsctest
    psql -h localhost -U jxyz -d hsctest < schema.sql