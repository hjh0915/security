package com.work.sign;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class MyCustomRealm extends JdbcRealm {

    private Map<String, String> credentials = new HashMap<>();
    private Map<String, Set<String>> roles = new HashMap<>();
    private Map<String, Set<String>> perm = new HashMap<>();

    {
        credentials.put("lonestarr", "vespa");
        credentials.put("darkhelmet", "ludicrousspeed");
        credentials.put("root", "secret");

        roles.put("lonestarr", new HashSet<>(Arrays.asList("goodguy", "schwartz")));
        roles.put("root", new HashSet<>(Arrays.asList("admin")));

        perm.put("admin", new HashSet<>(Arrays.asList("*")));
        perm.put("goodguy", new HashSet<>(Arrays.asList("winnebago:drive:eagle5")));
        perm.put("schwartz",
            new HashSet<>(Arrays.asList("lightsaber:*")));

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {

        UsernamePasswordToken uToken = (UsernamePasswordToken) token;

        if(uToken.getUsername() == null
          || uToken.getUsername().isEmpty()
          || !credentials.containsKey(uToken.getUsername())
          ) {
            throw new UnknownAccountException("username not found!");
        }


        return new SimpleAuthenticationInfo(
          uToken.getUsername(), credentials.get(uToken.getUsername()),
          getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Set<String> roleNames = new HashSet<>();
        Set<String> permissions = new HashSet<>();

        principals.forEach(p -> {
            try {
                Set<String> roles = getRoleNamesForUser(null, (String) p);
                roleNames.addAll(roles);
                permissions.addAll(getPermissions(null, null,roles));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

    @Override
    protected Set<String> getRoleNamesForUser(Connection conn, String username) throws SQLException {
        if(!roles.containsKey(username)) {
            throw new SQLException("username not found!");
        }

       return roles.get(username);
    }

    @Override
    protected Set<String> getPermissions(Connection conn, String username, Collection<String> roleNames) throws SQLException {
        for (String role : roleNames) {
            if (!perm.containsKey(role)) {
                throw new SQLException("role not found!");
            }
        }

        Set<String> finalSet = new HashSet<>();
        for (String role : roleNames) {
            finalSet.addAll(perm.get(role));
        }

        return finalSet;
    }
}


