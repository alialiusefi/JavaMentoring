package com.epam.esm.repository;

public abstract class AbstractRepoTest {

    /*@ClassRule
    public static SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();*//*
    public EntityManager entityManager;
    @Autowired
    public TagRepositoryImpl tagRepositoryImpl;
    @Autowired
    public GiftCertificateRepositoryImpl giftCertificateRepositoryImpl;

    public JdbcTemplate template;
    public DataSource dataSource;


    @Before
    public void setUp() {

       *//* template = new JdbcTemplate(pg.getEmbeddedPostgres().getPostgresDatabase());
        template.execute("" +
                "create table giftcertificates\n" +
                "(\n" +
                "    id                   serial primary key,\n" +
                "    name                 varchar(50),\n" +
                "    description          text,\n" +
                "    price                float,\n" +
                "    date_created         date,\n" +
                "    date_modified        date,\n" +
                "    duration_till_expiry integer\n" +
                ");" +
                "");
       *//**//* entityManager.createNativeQuery("create table tag\n" +
                "(\n" +
                "    id       serial primary key,\n" +
                "    tag_name varchar(16)\n" +
                ");").executeUpdate();
        entityManager.createNativeQuery("create table tagged_giftcertificates\n" +
                "(\n" +
                "    tag_id              integer references tag (id) on delete cascade,\n" +
                "    gift_certificate_id integer references giftcertificates (id) on delete cascade\n" +
                ");").executeUpdate();
        entityManager.createNativeQuery("CREATE FUNCTION public.consists(IN a text, IN b text)\n" +
                "    RETURNS boolean\n" +
                "    LANGUAGE 'plpgsql'\n" +
                "AS\n" +
                "$BODY$\n" +
                "begin\n" +
                "    return b like '%' || a || '%';\n" +
                "end;\n" +
                "$BODY$;\n" +
                "\n" +
                "ALTER FUNCTION public.consists(text, text)\n" +
                "    OWNER TO postgres;").executeUpdate();

        entityManager.createNativeQuery("insert into tag(tag_name)\n" +
                "values ('Accesories'),\n" +
                "       ('Food'),\n" +
                "       ('Hotel'),\n" +
                "       ('Travel');").executeUpdate();
        entityManager.createNativeQuery("insert into giftcertificates(name, description, price, date_created, date_modified, duration_till_expiry)\n" +
                "VALUES ('ACME Discount Voucher', 'Discount while shopping', 20.00, '2012-09-09', '2014-12-01', 5);\n")
                .executeUpdate();
        entityManager.createNativeQuery("insert into giftcertificates(name, description, price, date_created, date_modified, duration_till_expiry)\n" +
                "VALUES ('Discount Voucher', 'Ze Discript', 20.00, '2000-09-09', '2000-12-01', 5);\n").executeUpdate();
        entityManager.createNativeQuery("insert into tagged_giftcertificates(tag_id, gift_certificate_id)\n" +
                "VALUES (1, 1),\n" +
                "       (2, 1)," +
                "(1,2);").executeUpdate();*//*
    }*/
}
