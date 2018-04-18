/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Camilo
 */
public class Conexion {
    
    /**
     * Genera la conexión con la base de datos
     * @return Retorna los datos relacionados con la conexión
     */
    public DriverManagerDataSource conectar(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/Proyecto2");
        dataSource.setUsername("postgres");
        dataSource.setPassword("MunicipalWaste");
        return dataSource;
    }
}
