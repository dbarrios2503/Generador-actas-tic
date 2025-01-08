package api.generador_acta.Controler;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/tif11")
public class TIF11actaController {

    @GetMapping("/")
    public String mostrarFormulario() {
        return "formulario11"; // Muestra el formulario en src/main/resources/templates/formulario11.html
    }

    @PostMapping("/generar-acta2")
    public ResponseEntity<byte[]> generarActa(
            @RequestParam("nombreEquipo") String nombreEquipo,
            @RequestParam("numeroSerie") String numeroSerie,
            @RequestParam("cargo") String cargo,
            @RequestParam("jefeInmediato") String jefeInmediato,
            @RequestParam("marca") String marca,
            @RequestParam("modelo") String modelo,
            @RequestParam("area") String area,
            @RequestParam("responsable") String responsable,
            @RequestParam("observaciones") String observaciones,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("dia") String dia,
            @RequestParam("mes") String mes,
            @RequestParam("anio") String anio
    ) {
        try (FileInputStream fis = new FileInputStream("src/main/resources/templates/acta_template2.docx");
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            XWPFDocument documento = new XWPFDocument(fis);

            // Reemplazar los marcadores con los datos del formulario
            for (XWPFParagraph parrafo : documento.getParagraphs()) {
                for (var run : parrafo.getRuns()) {
                    String texto = run.getText(0);
                    if (texto != null) {
                        texto = texto.replace("{{NombreEquipo}}", nombreEquipo)
                                     .replace("{{NumeroSerie}}", numeroSerie)
                                     .replace("{{Cargo}}", cargo)
                                     .replace("{{JefeInmediato}}", jefeInmediato)
                                     .replace("{{Marca}}", marca)
                                     .replace("{{Modelo}}", modelo)
                                     .replace("{{Area}}", area)
                                     .replace("{{Responsable}}", responsable)
                                     .replace("{{Observaciones}}", observaciones)
                                     .replace("{{Ciudad}}", ciudad)
                                     .replace("{{Dia}}", dia)
                                     .replace("{{Mes}}", mes)
                                     .replace("{{Anio}}", anio);
                        run.setText(texto, 0);
                    }
                }
            }

            documento.write(baos);

            // Configurar la respuesta HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", "TI-F-11_PRÉSTAMO_Y_DEVOLUCIÓN_DE_EQUIPOS_TECNOLÓGICOS.docx");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace(); // Registrar el error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
