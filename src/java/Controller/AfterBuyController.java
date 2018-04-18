/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Conexion;
import Models.Producto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static java.util.Optional.empty;
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
public class AfterBuyController {
     private JdbcTemplate jdbcTemplate;
     public AfterBuyController() {
        Conexion conn = new Conexion();
        this.jdbcTemplate = new JdbcTemplate(conn.conectar());;
    }
    
    @RequestMapping(value = "afterBuy.htm",method = RequestMethod.GET)
    public ModelAndView viewOptions(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("BD/afterBuy");
        String sql = "with recursive h(id) as\n" +
"(select id from trampa order by id desc limit 1),\n" +
"t(aux) as\n" +
"(select aux from h, trampa j where j.id=h.id),\n" +
"a(id) as\n" +
"(select distinct(m.id) from t, h, trampa m \n" +
"where (m.aux = t.aux) and m.id<>h.id)\n" +
"select * from a, productos p where (a.id = p.id);";
        List response;
        response = this.jdbcTemplate.queryForList(sql);
        mav.addObject("Datos", response);
        if (response.isEmpty()) {
            mav.addObject("mensaje", "Señor(a) Usuario(a) gracias por realizar su compra con nosotros.");
            
        } else
        {
             mav.addObject("mensaje", "Señor(a) Usuario(a) gracias por realizar su compra con nosotros, otros usuarios tambien han escogido los siguientes productos, creemos que puedan ser de su interes.");    
        }
        return mav;
    } 



}


  