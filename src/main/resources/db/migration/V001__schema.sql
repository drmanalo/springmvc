create table users (id number(10,0) not null, active number(1,0), password varchar2(255 char) not null, role varchar2(50 char) not null, username varchar2(255 char) not null, primary key (id));
create sequence users_seq;