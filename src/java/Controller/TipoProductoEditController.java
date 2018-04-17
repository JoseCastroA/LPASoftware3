/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Conexion;
import Models.TipoProducto;
import Models.ValidarTipoProducto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
 * @author trisb
 */
@Controller
public class TipoProductoEditController {

    private ValidarTipoProducto validarTipoProducto;
    private JdbcTemplate conexion;

    /**
     * Constructor
     */
    public TipoProductoEditController() {
        this.validarTipoProducto = new ValidarTipoProducto();
        Conexion conn = new Conexion();
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
     * Edita un producto en la base de datos con los datos nuevos a partir de su id
     * @param tipoProducto Tipo de producto a modificar
     * @param result Resultado al validar la petición
     * @param status Estado de la petición
     * @param request Request desde el jsp para la petición 
     * @return La vista a la que se redirige después de la edición
     */
    @RequestMapping(value = "TipoProductoEdit.htm", method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute("Producto") TipoProducto tipoProducto, BindingResult result, SessionStatus status, HttpServletRequest request){
        this.validarTipoProducto.validate(tipoProducto, result);
        if(result.hasErrors()){
            ModelAndView mav=new ModelAndView();
            mav.setViewName("BD/TipoProductoEdit");
            mav.addObject("Producto",tipoProducto);
            return mav;
        }else{
            int id = Integer.parseInt(request.getParameter("id"));
            this.conexion.update("update tipo_producto set tipo_producto=?, id_tipo_linea=? where id=?;",tipoProducto.getNombre(), Integer.parseInt(tipoProducto.getId_tipo_linea()),id);
            return new ModelAndView("redirect:/TipoProductoHome.htm");
        }
    }

    /**
     * Busca un tipo de producto a partir de id
     * @param id Id del tipo de producto a buscar
     * @return Retorna el tipo de producto buscado
     */
    public TipoProducto seleccionarProducto(int id) {
        final TipoProducto tipoProducto = new TipoProducto();
        String query = "select * from tipo_producto where id='" + id + "';";
        return (TipoProducto) conexion.query(query, new ResultSetExtractor<TipoProducto>() {
            @Override
            public TipoProducto extractData(ResultSet result) throws SQLException, DataAccessException {
                if (result.next()) {
                    tipoProducto.setNombre(result.getString("tipo_producto"));
                    tipoProducto.setId_tipo_linea(result.getString("id_tipo_linea"));
                }
                return tipoProducto;
            }
        });
    }
}

