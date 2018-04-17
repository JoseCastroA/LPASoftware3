/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
/**
 *
 * @author trisb
 */
public class ValidarTipoProducto implements Validator{
    @Override
    public boolean supports(Class<?> type) {
        return TipoProducto.class.isAssignableFrom(type);
    }

    /**
     * Verifica que los campos no estén vacíos
     * @param tipoProducto Tipo de producto
     * @param errors Tipos de errores
     */
    @Override
    public void validate(Object tipoProducto, Errors errors) {
        TipoProducto persona = (TipoProducto) tipoProducto;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Nombre", "required.Nombre", "Todos los campos son obligatorios");
      }
}
