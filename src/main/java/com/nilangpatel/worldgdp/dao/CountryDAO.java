package com.nilangpatel.worldgdp.dao;

import com.nilangpatel.worldgdp.Country;
import com.nilangpatel.worldgdp.dao.mapper.CountryRowMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
public class CountryDAO {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
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
            + " LEFT OUTER JOIN city cy ON cy.id=c.capital";

    private static final String SELECT_WHERE=" AND (LOWER(c.name))" +
            "LIKE CONCAT('%', LOWER(:search),'%')";
    private static final String REGION_WHERE =" AND c.region = :region";
    private static final String CONTINENT_WHERE =" AND c.continent = :continent";
    private static final String PAGINATION = " ORDER BY c.code" +
            " LIMIT :size OFFSET :offset";
    private int PAGE_SIZE=20;

    private boolean _parametroTem(String atributo,Map<String,Object> parametro){
        return !StringUtils.isEmpty((String)parametro.get(atributo));
    }


    private String _queryCompletada(String initialQuery,
                                    Map <String,Object> parametros){
        String _queryCompleta=initialQuery+"";
        if(_parametroTem("search",parametros)){
            _queryCompleta+=SELECT_WHERE;
        }
        if(_parametroTem("region",parametros)){
            _queryCompleta+=REGION_WHERE;
        }
        if(_parametroTem("continent",parametros)){
            _queryCompleta+=CONTINENT_WHERE;
        }
        return _queryCompleta;
    }
    public String _queryCompletadaPb(Map<String,Object> pm){
        return _queryCompletada(SELECT_CLAUSE+" WHERE 1 = 1 ",
                pm)+" "+PAGINATION;
    }
    private Map<String, Object> getCountryAsMap(String code, Country country){
        Map<String, Object> countryMap = new HashMap<String, Object>();
        countryMap.put("name", country.getName());
        countryMap.put("localName", country.getLocalName());
        countryMap.put("capital", country.getCapital().getId());
        countryMap.put("continent", country.getContinent());
        countryMap.put("region", country.getRegion());
        countryMap.put("headOfState", country.getHeadOfState());
        countryMap.put("governmentForm", country.getGovernmentForm());
        countryMap.put("indepYear", country.getIndepYear());
        countryMap.put("surfaceArea", country.getSurfaceArea());
        countryMap.put("population", country.getPopulation());
        countryMap.put("lifeExpectancy", country.getLifeExpectancy());
        countryMap.put("code", code);
        return countryMap;
    }
    public List<Country> getCountries(Map<String,Object> parametros){
        int nPaginas=1;


        if(parametros.containsKey("PageNo"))
            nPaginas = Integer.parseInt(parametros.get("PageNo")
                    .toString());

        Integer offset = (nPaginas -1)*PAGE_SIZE;
        parametros.put("offset",offset);
        parametros.put("size",PAGE_SIZE);

        String nossaQr= _queryCompletada(SELECT_CLAUSE+" WHERE 1 = 1 ",
                parametros)+""+PAGINATION;

        return
        namedParameterJdbcTemplate.query(nossaQr,parametros,
                new CountryRowMapper());

    }
    public int getCountriesNumber(Map<String,Object> parametros
                                  ){
        return namedParameterJdbcTemplate.queryForObject(
                _queryCompletada("SELECT COUNT(*) FROM " +
                        "country c WHERE 1 = 1 ",parametros)
                ,parametros,Integer.class);

    }
    public Country getCountryDetail(String code) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("code", code);
        String sFiny = SELECT_CLAUSE
                +" WHERE c.code = :code";
        return namedParameterJdbcTemplate.queryForObject(sFiny, params,
                new CountryRowMapper());
    }
    public void editCountryDetail(String code, Country country) {
        namedParameterJdbcTemplate.update(" UPDATE country SET "
                        + " name = :name, "
                        + " localname = :localName, "
                        + " capital = :capital, "
                        + " continent = :continent, "
                        + " region = :region, "
                        + " HeadOfState = :headOfState, "
                        + " GovernmentForm = :governmentForm, "
                        + " IndepYear = :indepYear, "
                        + " SurfaceArea = :surfaceArea, "
                        + " population = :population, "
                        + " LifeExpectancy = :lifeExpectancy "
                        + "WHERE Code = :code ",
                getCountryAsMap(code, country));
    }


}
