/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Conexion;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
/**
 *
 * @author trisb
 */
@Controller
public class TipoProductoHomeController {
     //PARA BD
    private JdbcTemplate jdbcTemplate;
    
    public TipoProductoHomeController() {
        
        Conexion conn = new Conexion();
        this.jdbcTemplate = new JdbcTemplate(conn.conectar());
    }

    //-------------Base de Datos------------------- 
    
    @RequestMapping("TipoProductoHome.htm")
    public ModelAndView homeBD(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("BD/TipoProductoHome");
        String sql = "select pro.id, pro.tipo_producto, lin.tipo_linea from tipo_producto pro, tipo_linea lin where lin.id=pro.id_tipo_linea;";
        List response;
        response = this.jdbcTemplate.queryForList(sql);
        System.out.println(response);
        mav.addObject("Datos", response);
        return mav;
    } 
    
    //------------------CRUD-----------------------
    
    @RequestMapping("TipoProductoAdd.htm")
    public ModelAndView add(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("BD/TipoProductoAdd");
        return mav;
    }
}
