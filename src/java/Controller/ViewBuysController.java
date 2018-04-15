/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Conexion;
import Models.Producto;
import Models.ValidateUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;

/**
 *
 * @author CASA
 */
@Controller
public class ViewBuysController {
     private JdbcTemplate jdbcTemplate;
     public ViewBuysController() {
        Conexion conn = new Conexion();
        this.jdbcTemplate = new JdbcTemplate(conn.conectar());;
    }
    @RequestMapping(value = "viewBuys.htm")
    public ModelAndView previewView(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("BD/viewBuys");
        String sql = "select * from productos";
        List response;
        response = this.jdbcTemplate.queryForList(sql);
        mav.addObject("Datos", response);
        return mav;
    } 



}


  