CREATE FUNCTION public.consists(IN a text, IN b text)
    RETURNS boolean
    LANGUAGE 'plpgsql'
AS
$BODY$
begin
    return b like '%' || a || '%';
end;
$BODY$;

ALTER FUNCTION public.consists(text, text)
    OWNER TO postgres;