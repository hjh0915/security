CREATE TABLE users (
  username varchar(20) NOT NULL,
  password varchar(20) NOT NULL,
  password_salt int DEFAULT NULL,
  PRIMARY KEY (username)
);

CREATE TABLE user_roles (
  username varchar(20) NOT NULL,
  role_name varchar(20) NOT NULL,
  PRIMARY KEY (username, role_name)
);

CREATE TABLE roles_permissions (
  role_name varchar(20) NOT NULL,
  permission varchar(60) NOT NULL,
  PRIMARY KEY (role_name, permission)
);