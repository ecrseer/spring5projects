/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nilangpatel.worldgdp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author nublado
 */
@Configuration
@PropertySource("classpath:application.properties")
public class PropertySourcesPlaceholderConfigurer {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }
}
