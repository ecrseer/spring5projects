package com.nilangpatel.worldgdp.dao;

import com.nilangpatel.worldgdp.Country;

import java.util.List;
import java.util.Map;

public class CountryDAO {
    private String SELECT_FROM;
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
