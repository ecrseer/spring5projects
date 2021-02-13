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
        public void getCountries_ByName(){
            Map<String,Object> parametros = new HashMap<>();
            parametros.put("search","Aruba");
            List<Country> contries =  countryDao.getCountries(parametros);
            assertThat(contries).hasSize(1);

        }


        @Test
        public void testGetCountry_ByContinent(){
            Map<String,Object> parametrs = new HashMap<>();
            parametrs.put("continent","Asia");
            List<Country> contries =countryDao.getCountries(parametrs);
            assertThat(contries).hasSize(20);
        }

        @Test
        public void testGetCountryDetails(){
            Country c= countryDao.getCountryDetail("IND");
            assertThat(c).isNotNull();
            String queryDosDetalhes="Country(code=IND, name=India, "
                    + "continent=Asia, region=Southern and Central Asia, "
                    + "surfaceArea=3287263.0, indepYear=1947, population=1013662000, "
                    + "lifeExpectancy=62.5, gnp=447114.0, localName=Bharat/India, "
                    + "governmentForm=Federal Republic, headOfState=Kocheril Raman Narayanan, "
                    + "capital=City(id=1109, name=New Delhi, country=null, "
                    + "countryCode=null, district=null, population=null), code2=IN)";
            assertThat(c.toString()).isEqualTo(queryDosDetalhes);
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

    @Test
    public void mytestStrinSql(){
        String hughQuery = "SELECT "
                + " c.Code, "
                + " c.Name, "
                + " c.Continent, "
                + " c.region, "
                + " c.SurfaceArea surface_area, "
                + " c.IndepYear indep_year, "
                + " c.Population, "
                + " c.LifeExpectancy life_expectancy, "
                + " c.GNP, "
                + " c.LocalName local_name, "
                + " c.GovernmentForm government_form, "
                + " c.HeadOfState head_of_state, "
                + " c.code2 ,"
                + " c.capital ,"
                + " cy.name capital_name "
                + "FROM country c"
                + " LEFT OUTER JOIN city cy ON cy.id=c.capital"
                +" WHERE 1 = 1";


        Map<String,Object> parametrs = new HashMap<>();
        parametrs.put("continent","Asia");
        assertThat(
        countryDao._queryCompletadaPb(parametrs)
        ).isEqualTo(hughQuery)
        ;
    }


}
