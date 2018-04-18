package Controller;

import Models.Conexion;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author trisb
 */
@Controller
public class TipoProductoDeleteController {

    private JdbcTemplate conexion;

    /**
     * Constructor
     */
    public TipoProductoDeleteController() {
        Conexion conn = new Conexion();
        this.conexion = new JdbcTemplate(conn.conectar());
    }

    /**
     * Elimina un tipo de producto en la base de datos a partir de su id
     *
     * @param request Request de su petición desde el jsp
     * @return Retorna la vista a la que se redirige después de la petición de
     * eliminar
     */
    @RequestMapping("TipoProductoDelete.htm")
    public ModelAndView delete(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        this.conexion.update("delete from tipo_producto where id=?", id);
        return new ModelAndView("redirect:/TipoProductoHome.htm");
    }
}
