package com.nilangpatel.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

public class TestDBConfiguration {

    int nom =0;

    @Bean
    public DataSource embeddedDatabaseBuilder(){
        return new EmbeddedDatabaseBuilder().generateUniqueName(true)
                .setScriptEncoding("UTF-8")
                .setType(EmbeddedDatabaseType.H2)
                .ignoreFailedDrops(true)
                .addScript("h2_world.sql")
                .build();

    }


    @Bean("testTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(){
     return new NamedParameterJdbcTemplate(embeddedDatabaseBuilder());
    }
}
