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
    private JdbcTemplate conexion;
    
    /**
     * Constructor
     */
    public TipoProductoHomeController() {
        Conexion conn = new Conexion();
        this.conexion = new JdbcTemplate(conn.conectar());
    }

    /**
     * Obtiene los datos que están al momento de ingresar la base de datos
     * @return Retorna la vista con los datos al tipoLineaHomeController
     */
    @RequestMapping("TipoProductoHome.htm")
    public ModelAndView homeBD(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("BD/TipoProductoHome");
        String sql = "select pro.id, pro.tipo_producto, lin.tipo_linea from tipo_producto pro, tipo_linea lin where lin.id=pro.id_tipo_linea;";
        List response;
        response = this.conexion.queryForList(sql);
        System.out.println(response);
        mav.addObject("Datos", response);
        return mav;
    } 

}
