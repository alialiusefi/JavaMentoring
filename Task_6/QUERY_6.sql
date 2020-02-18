create or replace trigger authors_trigger
    after insert
    on authors
    for each row
declare
    key_value varchar(1000);
begin
    if(new.ID is empty ) then
        key_value := concat(key_value);
    end if;
    if(new.NAME is empty ) then

    end if;
    insert into logs(id, table_name, insert_date, description)
    values (AUTHORS_ID_SEQ.nextval, 'authors', current_date, 'ADDED!');
end;


/*create or replace procedure createAuditTriggersForTables is

    cursor cursor1 is select * from user_tables;

    begin
        for item in cursor1
        loop
            execute immediate 'create or replace trigger ' || item.TABLE_NAME || '_trigger' ||
                              ' after insert on ' || item.TABLE_NAME || ' for each row ' ||
                              ' declare ' ||
                              ' begin ' || ' insert into logs(id,table_name,insert_date,description) values ' ||
                              ' ( ' || item.TABLE_NAME || '_ID_SEQ.nextval,' || item.TABLE_NAME || ',' || current_date || ',' || 'ADDED!' || ' ); '
                                || ' end;';
        end loop;
    end;

begin createAuditTriggersForTables; end;*/
/*
begin
    DBMS_OUTPUT.PUT_LINE('create or replace trigger ' || 'AUTHORS' || '_trigger' ||
                    ' after insert on ' || 'AUTHORS' || ' for each row ' ||
                    ' declare ' ||
                    ' begin ' || ' insert into logs(id,table_name,insert_date,description) values ' ||
                    ' ( ' || 'AUTHORS' || '_ID_SEQ.nextval,' || 'AUTHORS' || ',' || current_date || ',' || 'ADDED!' || ' ); '
        || ' end;');
    *//*execute immediate 'create or replace trigger ' || 'AUTHORS' || '_trigger' ||
                      ' after insert on ' || 'AUTHORS' || ' for each row ' ||
                      ' declare ' ||
                      ' begin ' || ' insert into logs(id,table_name,insert_date,description) values ' ||
                      ' ( ' || 'AUTHORS' || '_ID_SEQ.nextval,' || 'AUTHORS' || ',' || current_date || ',' || 'ADDED!' || ' ); '
        || ' end;';*//*
end;*/
