/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.generador_acta.Controler;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ActasController {

    @GetMapping("/inicio")
    public String mostrarPaginaPrincipal() {
        // Retorna el nombre del archivo HTML principal (sin la extensión .html)
        return "index";
    }

    @GetMapping("/tif02")
    public String mostrarFormularioTIF02() {
        // Retorna la vista del formulario para TI-F-02
        return "formulario02";
    }

    @GetMapping("/tif11")
    public String mostrarFormularioTIF11() {
        // Retorna la vista del formulario para TI-F-11
        return "formulario11";
    }
}
