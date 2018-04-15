package Controller;

import Models.Conexion;
import Models.Producto;
import Models.ValidateUser;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Camilo
 */
@Controller
public class AddBuyController {
    private ValidateUser validarusuario;
    private JdbcTemplate jdbcTemplateUser;
    
 
        //Constructor de clase con la conexion y validar que los datos no esten vacios o espacios
    public AddBuyController() {
        Conexion conn = new Conexion();
        this.validarusuario = new ValidateUser();
        this.jdbcTemplateUser = new JdbcTemplate(conn.conectar());
    }
    
    
    @RequestMapping(value = "addBuy.htm", method = RequestMethod.GET)
    public ModelAndView viewBD(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        int id = Integer.parseInt(request.getParameter("id"));
        mav.setViewName("BD/addBuy");
        String sql = "select * from productos where id='" + id + "';";
        List response;
        response = this.jdbcTemplateUser.queryForList(sql);
//        mav.addObject("Usuario", newProductod(id, u.getNombre(),u.getInformacion(),u.getImagen(),u.getPrecio()));
        mav.addObject("Datos", response);
        return mav;
    } 
    
    
       @RequestMapping(value = "/COMPRA_add.htm", method = RequestMethod.POST)
    public  ModelAndView agregarCompra(@RequestParam("Cantidad") int cantidad,@RequestParam("Id") int id,@RequestParam("Nombre") String nombre,@RequestParam("Precio") int precio) {
          String sql = "select * from compra_temporal where id='" + id + "';";
          List response;
          response = this.jdbcTemplateUser.queryForList(sql);
           System.out.println("La respuesta es: "+ response);
          if (response.isEmpty()) {
              Producto compra = new Producto(id, nombre, precio,"No", "No",0, cantidad);
           compra.agregarCompraProducto(id, nombre, precio, cantidad);
          return new ModelAndView("redirect:/admBuys.htm");
          }
          else {
              Producto compra = new Producto(id, nombre, precio,"No", "No",0, cantidad);
           compra.sumarCompraProducto(id, nombre, precio, cantidad);
          return new ModelAndView("redirect:/admBuys.htm");
          }
    }
    
        @RequestMapping(value = "/COMPRA_liquidar.htm",method = {RequestMethod.POST, RequestMethod.GET})
        public  ModelAndView liquidarCompra() {
         Producto compra = new Producto();
         compra.liquidarCompra();
          
          return new ModelAndView("redirect:/addBuy.htm?id=1");
    }
    
}
