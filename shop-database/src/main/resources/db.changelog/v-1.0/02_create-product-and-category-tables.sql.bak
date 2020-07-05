create table categories (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
GO
create table products (id bigint not null auto_increment, title varchar(255), cost decimal(10,2), category_id bigint, primary key (id)) engine=InnoDB
GO
alter table products add constraint productToCategoryReference foreign key (category_id) references categories (id)
GO
