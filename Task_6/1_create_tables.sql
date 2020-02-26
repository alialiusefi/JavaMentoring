/*system manager in oracle developer app*/
/*https://localhost:5500 -> add user C##IDEA admin*/
/*credentials: C##IDEA 	admin */

create table authors (
    id NUMBER(19,0) primary key,
    name varchar(48) not null unique
);

create sequence authors_id_seq start with 1;

create table news (
    id NUMBER(19,0) primary key,
    content varchar2(4000) not null,
    creation_date date default current_date not null,
    author_id NUMBER(19,0) not null,
    foreign key(author_id) references authors(id) on delete cascade
);
create sequence news_id_seq start with 1;

create table comments (
    id NUMBER(19,0) primary key ,
    content varchar(256) not null,
    author_id NUMBER(19,0) not null,
    news_id NUMBER(19,0) not null,
    creation_date date default current_date not null,
    foreign key(author_id) references authors(id) on delete cascade,
    foreign key(news_id) references news(id) on delete cascade
);
create sequence comments_id_seq start with 1;


create table tags (
  id            NUMBER(19,0) primary key,
  name          varchar(64) unique not null
);

create sequence tags_id_seq start with 1;

CREATE TABLE news_to_tag (
  news_id NUMBER(19,0) not null,
  tag_id  NUMBER(19,0) not null,
  foreign key (tag_id)  references tags (id) on delete cascade,
  foreign key (news_id) references news (id) on delete cascade,
  primary key (news_id, tag_id)
  );

CREATE TABLE logs (
  id NUMBER(19,0) primary key,
  table_name varchar(64) not null,
  insert_date timestamp not null,
  description varchar2(4000) not null
);

create sequence logs_id_seq start with 1;

