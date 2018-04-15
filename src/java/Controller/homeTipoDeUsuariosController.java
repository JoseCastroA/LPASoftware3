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
 * @author Camilo
 */
@Controller
public class homeTipoDeUsuariosController {

    //PARA BD
    private JdbcTemplate jdbcTemplate;
    //--------------
    
    //Constructor de clase con la conexion
    public homeTipoDeUsuariosController() {
        Conexion conn = new Conexion();
        this.jdbcTemplate = new JdbcTemplate(conn.conectar());;
    }
    
    //Metodo para enlazar el home.htm con la clase home.jsp
    @RequestMapping("homeTipoDeUsuarios.htm")
    public ModelAndView homeBD(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("TiposUsuarios/homeTipoDeUsuarios");
        String sql = "select * from tipos_usuarios;";
        List response;
        response = this.jdbcTemplate.queryForList(sql);
        mav.addObject("Datos", response);
        return mav;
    } 


    
}
