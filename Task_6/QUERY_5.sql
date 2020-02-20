with query1 as (select id as id1,name as author_1 from AUTHORS where AUTHORS.ID <= (select count(*) from AUTHORS) / 2),
     query2 as (select id as id2 ,name as author_2 from AUTHORS where AUTHORS.ID > (select count(*) from AUTHORS) / 2)
/*order by DBMS_RANDOM.RANDOM()*/
select distinct author_1,author_2 from query1  inner join query2 on (query1.id1 < query2.id2) order by DBMS_RANDOM.RANDOM() ;
