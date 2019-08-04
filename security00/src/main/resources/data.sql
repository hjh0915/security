insert into users (username, password) values ('lonestarr', 'vespa');

insert into user_roles (username, role_name) values ('lonestarr', 'goodguy');
insert into user_roles (username, role_name) values ('lonestarr', 'schwartz');

insert into roles_permissions (role_name, permission) values ('goodguy', 'winnebago:drive:eagle5');
insert into roles_permissions (role_name, permission) values ('schwartz', 'lightsaber:*');

alter table users alter column password_salt type varchar(20);
alter table users alter column password type varchar(60);