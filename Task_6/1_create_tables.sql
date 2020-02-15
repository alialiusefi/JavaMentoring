create table authors (
    id bigserial primary key ,
    name varchar(48) not null unique
);

create table news (
    id bigserial primary key ,
    title varchar(100) not null,
    creation_date date default now() not null,
    author_id bigint not null,
    foreign key(author_id) references authors(id) on delete cascade
);

create table comments (
    id bigserial primary key ,
    content varchar(256) not null,
    author_id bigint not null,
    news_id bigint not null,
    creation_date date default now() not null,
    foreign key(author_id) references authors(id) on delete cascade,
    foreign key(news_id) references news(id) on delete cascade
);

create table tags (
  id            bigserial primary key unique,
  name          varchar(64) unique not null
);

CREATE TABLE news_to_tag (
  news_id bigint not null,
  tag_id  bigint not null,
  foreign key (tag_id)  references tags (id) on delete cascade,
  foreign key (news_id) references news (id) on delete cascade,
  primary key (news_id, tag_id)
  );

CREATE TABLE logs (
  id BIGSERIAL primary key,
  table_name varchar(64) not null,
  insert_date timestamp not null,
  description TEXT not null
);


