/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nilangpatel.worldgdp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author nublado
 */

@Data
@Setter
@Getter

public class City {
    private Long id;
    private String name;
    private Country country;
    private String countryCode;
    private String district;
    private Long population;
}
