/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.a
 */
package com.nilangpatel.worldgdp;

/**
 *
 * @author nublado
 never*/

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CountryLanguage {
    private Country country;
    private String language;
    private String isOfficial;
    private Double percentage;
}
