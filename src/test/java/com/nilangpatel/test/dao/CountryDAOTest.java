package com.nilangpatel.test.dao;

import com.nilangpatel.test.config.TestDBConfiguration;
import com.nilangpatel.worldgdp.Country;
import com.nilangpatel.worldgdp.dao.CountryDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringJUnitConfig( classes = {
        TestDBConfiguration.class, CountryDAO.class})
public class CountryDAOTest {



        @Autowired
        CountryDAO countryDao;

        @Autowired @Qualifier("testTemplate")
        NamedParameterJdbcTemplate namedParamJdbcTemplate;

        @Before
        public void setup() {
            countryDao.setNamedParameterJdbcTemplate((namedParamJdbcTemplate));
        }

        @Test
        public void amizade(){
            countryDao = new CountryDAO();
            boolean en = countryDao.amizou(true);
            assertThat(en).isEqualTo(false);
        }


    @Test public void testEditCountryDetail() {
        Country c = countryDao.getCountryDetail("IND");
        c.setHeadOfState("Ram Nath Kovind");
        c.setPopulation(1324171354l);
        countryDao.editCountryDetail("IND", c);

        c = countryDao.getCountryDetail("IND");
        assertThat(c.getHeadOfState()).isEqualTo("Ram Nath Kovind");
        assertThat(c.getPopulation()).isEqualTo(1324171354l);
    }

    @Test
    public void testGetCountrCount(){
            int cntador = countryDao.getCountriesNumber(Collections.EMPTY_MAP);
            assertThat(cntador).isEqualTo(239);
    }


}
