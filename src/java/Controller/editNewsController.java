/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Conexion;
import Models.Novedad;
import Models.ValidateUser;
import java.sql.ResultSet;
import java.sql.SQLException;
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

/**
 *
 * @author Camilo
 */
@Controller
public class editNewsController {

    private ValidateUser validar;
    private JdbcTemplate jdbcTemplate;

    public editNewsController() {
        this.validar = new ValidateUser();
        Conexion conn = new Conexion();
        this.jdbcTemplate = new JdbcTemplate(conn.conectar());
    }

    @RequestMapping(value = "editNovedades.htm", method = RequestMethod.GET)
    public ModelAndView edit(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("BD/editNovedades");
        int id = Integer.parseInt(request.getParameter("id"));
        Novedad u = this.selectUsuario(id);
        mav.addObject("Usuario", new Novedad(id, u.getNombre(),u.getInformacion(),u.getImagen()));
        return mav;
    }
    
    @RequestMapping(value = "editNovedades.htm", method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute("Usuario") Novedad u, BindingResult result, SessionStatus status, HttpServletRequest request){
        this.validar.validate(u, result);
        if(result.hasErrors()){
            ModelAndView mav=new ModelAndView();
            int id= Integer.parseInt(request.getParameter("id"));
            Novedad datos = this.selectUsuario(id);
            mav.setViewName("BD/editNovedades");
            mav.addObject("Usuario",u);
            return mav;
        }else{
            int id = Integer.parseInt(request.getParameter("id"));
            this.jdbcTemplate.update("update novedades set nombre=? ,informacion=? ,fecha=NOW() where id=?;",u.getNombre(),u.getInformacion(),id);
            return new ModelAndView("redirect:/admNovedades.htm");
        }
    }

    public Novedad selectUsuario(int id) {
        final Novedad u = new Novedad();
        String query = "select * from novedades where id='" + id + "';";
        return (Novedad) jdbcTemplate.query(query, new ResultSetExtractor<Novedad>() {
            @Override
            public Novedad extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    u.setNombre(rs.getString("nombre"));
                    u.setInformacion(rs.getString("informacion"));
                }
                return u;
            }
        });
    }
}
