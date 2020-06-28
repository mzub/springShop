create table roles (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
GO
create table users (id bigint not null auto_increment, password varchar(255), user_name varchar(255), first_name varchar(255), last_name varchar(255), email varchar(255), age int, primary key (id)) engine=InnoDB
GO
create table users_roles (user_id bigint not null, role_id bigint not null) engine=InnoDB
GO
alter table users_roles add constraint FKj6m8fwv7oqv74fcehir1a9ffy foreign key (role_id) references roles (id)
GO
alter table users_roles add constraint FK2o0jvgh89lemvvo17cbqvdxaa foreign key (user_id) references users (id)
GO