create or replace function listTagsByNewsID(newsID in integer, seperator in varchar)
    return varchar
    is
    result varchar(1000);
    temp   varchar(1000);
    cursor ds is (select name
                   from tags
                            inner join NEWS_TO_TAG on TAGS.ID = NEWS_TO_TAG.TAG_ID
                   where NEWS_TO_TAG.NEWS_ID = newsID);
begin
    for record in ds
        loop
            temp := '';
            temp := concat(temp, record.name);
            temp := concat(temp, seperator);
            result := concat(result, temp);
        end loop;
    return result;
end;

SELECT listTagsByNewsID(1, ',') FROM dual;