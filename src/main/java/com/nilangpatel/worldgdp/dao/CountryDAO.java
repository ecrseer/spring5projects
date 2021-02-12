package com.nilangpatel.worldgdp.dao;

import com.nilangpatel.worldgdp.Country;

import java.util.List;
import java.util.Map;

public class CountryDAO {
    private static final String SELECT_CLAUSE="SELECT "
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
            + "LEFT OUTER JOIN city cy ON cy.id=c.capital";

    private static final String SELECT_WHERE="AND (LOWER(c.name))" +
            "LIKE CONCAT('%', LOWER(:search),'%')";
    private static final String REGION_WHERE ="AND c.region = :region";
    private static final String PAGINATION = "ORDER BY c.code" +
            "LIMIT :size OFFSET :offset";
    private int PAGE_SIZE=0;




    public List<Country> getCountries(Map<String,Object> parametros){
        int nPaginas=0;
        if(parametros.containsKey("PageNo"))
            nPaginas = Integer.parseInt(parametros.get("PageNo")
                    .toString());

        Integer offset = (nPaginas -1)*PAGE_SIZE;
        parametros.put("offset",offset);
        parametros.put("size",PAGE_SIZE);



        return null;
    }
}
