package com.nilangpatel.worldgdp.dao;

import com.nilangpatel.worldgdp.Country;
import com.nilangpatel.worldgdp.dao.mapper.CountryRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class CountryDAO {
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
            + "LEFT OUTER JOIN city cy ON cy.id=c.capital";

    private static final String SELECT_WHERE=" AND (LOWER(c.name))" +
            "LIKE CONCAT('%', LOWER(:search),'%')";
    private static final String REGION_WHERE =" AND c.region = :region";
    private static final String CONTINENT_WHERE =" AND c.continent = :continent";
    private static final String PAGINATION = "ORDER BY c.code" +
            "LIMIT :size OFFSET :offset";
    private int PAGE_SIZE=0;

    private boolean _parametroTem(String atributo,Map<String,Object> parametro){
        return StringUtils.isEmpty((String)parametro.get(atributo));
    }

    private String _verificaParamPesquisa(String paramPesquisado,
            Map<String,Object> parametro){

        switch ((String)parametro.get(paramPesquisado)){
            case "search":
                return SELECT_WHERE;
            case "region":
                return REGION_WHERE;
            case "continent":
                return CONTINENT_WHERE;
            default:
                return "";

        }

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

    public List<Country> getCountries(Map<String,Object> parametros){
        int nPaginas=0;

        String _queryComOpcoes = SELECT_CLAUSE+
                "WHERE 1 = 1"+_verificaParamPesquisa("search",parametros)
                +_verificaParamPesquisa("continent",parametros)
                +_verificaParamPesquisa("region",parametros);

        if(parametros.containsKey("PageNo"))
            nPaginas = Integer.parseInt(parametros.get("PageNo")
                    .toString());

        Integer offset = (nPaginas -1)*PAGE_SIZE;
        parametros.put("offset",offset);
        parametros.put("size",PAGE_SIZE);

        return
        namedParameterJdbcTemplate.query(
             _queryCompletada(SELECT_CLAUSE+"WHERE 1 = 1 ",
                     parametros)+""+PAGINATION,parametros,
                new CountryRowMapper());

    }
}
