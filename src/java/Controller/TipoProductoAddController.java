package Controller;

import Models.Conexion;
import Models.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
/**
 *
 * @author trisb
 */
@Controller
public class TipoProductoAddController {

    private ValidarTipoProducto validarTipoProducto;
    private JdbcTemplate conexion;

    /**
     * Constructor
     */
    public TipoProductoAddController() {
        Conexion conn = new Conexion();
        this.validarTipoProducto = new ValidarTipoProducto();
        this.conexion = new JdbcTemplate(conn.conectar());
    }

    
    /**
     * Obtiene los id y nombres de los tipos de línea que están en la base de datos
     * @return Retorna la lista de los tipos de línea
     */
    @ModelAttribute("id_tipo_linea")
    public Map<String, String> cmbTipoLinea() {
        Map<String, String> ListCond = new LinkedHashMap<>();
        String sql = "SELECT id,tipo_linea FROM tipo_linea;";
        List<Map<String, Object>> listaTipoLinea;
        listaTipoLinea = this.conexion.queryForList(sql);

        if ((listaTipoLinea != null) && (listaTipoLinea.size() > 0)) {
            for (Map<String, Object> tempRow : listaTipoLinea) {
                ListCond.put( ""+tempRow.get("id"), ""+tempRow.get("tipo_linea"));
            }
        }
        return ListCond;
    }

    /**
     * Adiciona un nuevo tipo de producto a la base de datos generando un id consecutivo
     * @param tipoProducto Tipo de producto a adicionar
     * @param result Resultado de la validación antes de hacer la inserción
     * @param status Estado de la solicitud desde el jsp
     * @return Retorna la vista a la que se redirige después de la petición de adicionar
     */
    @RequestMapping(value = "TipoProductoAdd.htm", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("Producto") TipoProducto tipoProducto, BindingResult result, SessionStatus status) {
        this.validarTipoProducto.validate(tipoProducto, result);
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("BD/TipoProductoAdd");
            mav.addObject("Producto", tipoProducto);
            return mav;
        } else {
            this.conexion.update("insert into tipo_producto (tipo_producto, id_tipo_linea) values (?, ?)", tipoProducto.getNombre(), Integer.parseInt(tipoProducto.getId_tipo_linea()));
            return new ModelAndView("redirect:/TipoProductoHome.htm");
        }
    }
}