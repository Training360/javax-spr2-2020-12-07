create table employees (id bigint auto_increment,
  emp_name varchar(255), version bigint, primary key (id));

insert into employees (emp_name, version) values ('John Doe', 0);
insert into employees (emp_name, version) values ('Jack Doe', 0);