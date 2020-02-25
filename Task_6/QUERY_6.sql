create or replace trigger authors_trigger
    after insert
    on authors referencing new as n
    for each row
declare
    id integer := :n.ID;
    name varchar2(48) := :n.NAME;
    key_value varchar(1000);
begin
    if(id is not null ) then
        key_value := concat(key_value,'id:');
        key_value := concat(key_value,ID);
        key_value := concat(key_value,';');
    end if;
    if(name is not null ) then
        key_value := concat(key_value,'name:');
        key_value := concat(key_value,name);
    end if;
    insert into logs(id, table_name, insert_date, description)
    values (logs_id_seq.nextval, 'authors', current_date, key_value);
end;

create or replace trigger comments_trigger
    after insert
    on COMMENTS referencing new as n
    for each row
declare
    id integer := :n.ID;
    content varchar2(256) := :n.CONTENT;
    AUTHOR_ID number(19) := :n.AUTHOR_ID;
    NEWS_ID number(19) := :n.NEWS_ID;
    CREATION_DATE date := :n.CREATION_DATE;
    key_value varchar(1000);
begin
    if(id is not null ) then
        key_value := concat(key_value,'id:');
        key_value := concat(key_value,id);
        key_value := concat(key_value,';');
    end if;
    if(content is not null ) then
        key_value := concat(key_value,'content:');
        key_value := concat(key_value,content);
    end if;
    if(AUTHOR_ID is not null ) then
        key_value := concat(key_value,'author_id:');
        key_value := concat(key_value,AUTHOR_ID);
    end if;
    if(NEWS_ID is not null ) then
        key_value := concat(key_value,'news_id:');
        key_value := concat(key_value,NEWS_ID);
    end if;
    if(CREATION_DATE is not null ) then
        key_value := concat(key_value,'creation_date:');
        key_value := concat(key_value,CREATION_DATE);
    end if;
    insert into logs(id, table_name, insert_date, description)
    values (logs_id_seq.nextval, 'comments', current_date, key_value);
end;


create or replace trigger news_trigger
    after insert
    on NEWS referencing new as n
    for each row
declare
    id integer := :n.ID;
    content varchar2(3000) := :n.CONTENT;
    AUTHOR_ID number(19) := :n.AUTHOR_ID;
    NEWS_ID number(19) := :n.ID;
    CREATION_DATE date := :n.CREATION_DATE;
    key_value varchar(4000);
begin
    if(id is not null ) then
        key_value := concat(key_value,'id:');
        key_value := concat(key_value,id);
        key_value := concat(key_value,';');
    end if;
    if(content is not null ) then
        key_value := concat(key_value,'content:');
        key_value := concat(key_value,content);
    end if;
    if(AUTHOR_ID is not null ) then
        key_value := concat(key_value,'author_id:');
        key_value := concat(key_value,AUTHOR_ID);
    end if;
    if(NEWS_ID is not null ) then
        key_value := concat(key_value,'news_id:');
        key_value := concat(key_value,NEWS_ID);
    end if;
    if(CREATION_DATE is not null ) then
        key_value := concat(key_value,'creation_date:');
        key_value := concat(key_value,CREATION_DATE);
    end if;
    insert into logs(id, table_name, insert_date, description)
    values (logs_id_seq.nextval, 'news', current_date, key_value);
end;

create or replace trigger news_to_tag_trigger
    after insert
    on NEWS_TO_TAG referencing new as n
    for each row
declare
    news_id integer := :n.NEWS_ID;
    tag_id integer := :n.TAG_ID;
    key_value varchar(1000);
begin
    if(news_id is not null ) then
        key_value := concat(key_value,'news_id:');
        key_value := concat(key_value,news_id);
        key_value := concat(key_value,';');
    end if;
    if(tag_id is not null ) then
        key_value := concat(key_value,'tag_id:');
        key_value := concat(key_value,tag_id);
    end if;
    insert into logs(id, table_name, insert_date, description)
    values (logs_id_seq.nextval, 'news_to_tag', current_date, key_value);
end;

create or replace trigger tags_trigger
    after insert
    on tags referencing new as n
    for each row
declare
    id integer := :n.ID;
    name integer := :n.NAME;
    key_value varchar(1000);
begin
    if(id is not null ) then
        key_value := concat(key_value,'id:');
        key_value := concat(key_value,id);
        key_value := concat(key_value,';');
    end if;
    if(name is not null ) then
        key_value := concat(key_value,'name:');
        key_value := concat(key_value,name);
    end if;
    insert into logs(id, table_name, insert_date, description)
    values (logs_id_seq.nextval, 'tags', current_date, key_value);
end;

create sequence logs_id_seq start with 1;
/*
select 'drop trigger ' || owner || '.' || trigger_name || ';' from all_triggers
*/
/*

drop trigger C##IDEA.AUTHORS_TRIGGER;
drop trigger C##IDEA.COMMENTS_TRIGGER;
drop trigger C##IDEA.NEWS_TRIGGER;
drop trigger C##IDEA.NEWS_TO_TAG_TRIGGER;
drop trigger C##IDEA.TAGS_TRIGGER;
*/
select 'drop trigger ' || owner || '.' || trigger_name || ';' from all_triggers
