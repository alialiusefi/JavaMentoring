/*this can be runned on laptop idea*/
select segment_name,segment_type,bytes/1024/1024 MB
from user_segments inner join user_Tables on TABLE_NAME = SEGMENT_NAME
where segment_type='TABLE' and segment_name = TABLE_NAME /*and owner='C##IDEA'*/;