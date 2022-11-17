package gov.example.appDemo.applicationRunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Order(1)
@Component
public class TableCreateRunner implements ApplicationRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        String sql = "DROP TABLE IF EXISTS public.students;\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS students\n" +
                "(\n" +
                "    name character varying(100) COLLATE pg_catalog.\"default\",\n" +
                "    email character varying(100) COLLATE pg_catalog.\"default\",\n" +
                "    graduation integer,\n" +
                "    degree character varying(100) COLLATE pg_catalog.\"default\",\n" +
                "    CONSTRAINT students_pkey PRIMARY KEY (email)\n" +
                ")\n" +
                "\n" +
                "TABLESPACE pg_default;\n" +
                "\n" +
                "ALTER TABLE IF EXISTS public.students\n" +
                "    OWNER to postgres;";

        jdbcTemplate.execute(sql);
    }
}
