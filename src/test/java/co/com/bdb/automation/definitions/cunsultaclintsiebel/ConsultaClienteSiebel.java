package co.com.bdb.automation.definitions.cunsultaclintsiebel;

import co.com.bdb.automation.definitions.BaseTest;
import co.com.bdb.automation.definitions.addcaseqa.ValidateIdFieldDefinitios;
import co.com.bdb.automation.utilities.CustomRequestSpecification;
import co.com.bdb.automation.utilities.EnvironmentValuesTask;
import co.com.bdb.automation.utilities.GenerateXml;
import co.com.bdb.automation.utilities.LeerCedula;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class ConsultaClienteSiebel {

    //linea para usar logger y comentariar el paso a paso
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateIdFieldDefinitios.class);
    static EnvironmentValuesTask env = new EnvironmentValuesTask();
    private static final String BASE_URL = env.getenv("BASE_URL_SIEBEL");
    //private static final String BASE_PATH = "";
    private CustomRequestSpecification request;
    private final BaseTest baseTest;
    private final String bodiesPath = "src/test/resources/bodies/apiTest/bb_consultaClinte/";
    private File bodyFile;

    public ConsultaClienteSiebel(BaseTest baseTest) {
        this.baseTest = baseTest;
    }

    @Given("que se realza el envio del cuerpo de la peticion {string}")
    public void queSeRealzaElEnvioDelCuerpoDeLaPeticion(String xmlPath) {
        RestAssured.useRelaxedHTTPSValidation("SSL");
        try {
            //String nit = LeerCedula.obtenerCedula("src/main/java/co/com/bdb/automation/docs/dataCedula.txt"); // Ruta del archivo con el NIT

            List<String> lineas = Files.readAllLines(Paths.get("src/main/java/co/com/bdb/automation/docs/dataCedula.txt"));
            String ced = lineas.get(0).trim(); // ← aquí seleccionas la línea que quieres
            String xmlOriginal = new String(Files.readAllBytes(Paths.get(bodiesPath + xmlPath)));
            String xmlModificado = GenerateXml.construirXML(xmlOriginal, ced);

            request = new CustomRequestSpecification(RestAssured.given().log().all()
                    .baseUri(BASE_URL)
                    .header("Content-Type", "application/xml; charset=UTF-8")
                    .body(xmlModificado)
                    .basePath(""));

        } catch (Exception e) {
            LOGGER.error("Error al leer o modificar el XML", e);
            throw new RuntimeException(e);
        }


    }
    @When("hago el consumo con el endPoint corespondiente")
    public void hagoElConsumoConElEndPointCorespondiente() {

        baseTest.response = request
                .when()
                .post()
                .then()
                .log().all()
                .extract()
                .response();

    }
    @Then("debe contestar con estado del codigo {int}")
    public void debeContestarConEstadoDelCodigo(Integer id) {

        request = new CustomRequestSpecification(
                RestAssured
                        .given()
                        .log().all()
                        .baseUri(BASE_URL)
                        .basePath(""+id));

    }
    @Then("arrojar en la etiqueta del response el siguiente mensaje {string}")
    public void arrojarEnLaEtiquetaDelResponseElSiguienteMensaje(String esperado) {

        String mensajeRecibido = baseTest.response.then()
                .extract()
                .xmlPath()
                .getString("RESPUESTA.MESSAGE_NBR");

        Assertions.assertEquals(esperado, mensajeRecibido,
                "El mensaje recibido no conicide con el esperado"

        );
        LOGGER.info("Mensaje esperado: "+esperado);
        LOGGER.info("Mensaje resibido: "+mensajeRecibido);


    }


    //Caso NO existe

    @Given("que se realizo un envio del cuerpo de la peticion {string}")
    public void queSeRealizoUnEnvioDelCuerpoDeLaPeticion(String xmlPath) {

        RestAssured.useRelaxedHTTPSValidation("SSL");
        try {
            List<String> lineas = Files.readAllLines(Paths.get("src/main/java/co/com/bdb/automation/docs/dataCedula.txt"));
            String ced = lineas.get(0).trim(); // ← aquí seleccionas la línea que quieres
            String xmlOriginal = new String(Files.readAllBytes(Paths.get(bodiesPath + xmlPath)));
            String xmlModificado = GenerateXml.construirXML(xmlOriginal, ced);

            request = new CustomRequestSpecification(RestAssured.given().log().all()
                    .baseUri(BASE_URL)
                    .header("Content-Type", "application/xml; charset=UTF-8")
                    .body(xmlModificado)
                    .basePath(""));

        } catch (Exception e) {
            LOGGER.error("Error al leer o modificar el XML", e);
            throw new RuntimeException(e);
        }


    }
    @When("hago el consumo para el endpoint puntual")
    public void hagoElConsumoParaElEndpointPuntual() {

        baseTest.response = request
                .when()
                .post()
                .then()
                .log().all()
                .extract()
                .response();

    }
    @When("el cliente no existe en la base de datos dando codigo {int}")
    public void elClienteNoExisteEnLaBaseDeDatosDandoCodigo(Integer id) {

        request = new CustomRequestSpecification(
                RestAssured
                        .given()
                        .log().all()
                        .baseUri(BASE_URL)
                        .basePath(""+id));

    }
    @Then("el mensaje que debe responde debe ser el siguiente")
    public void elMensajeQueDebeRespondeDebeSerElSiguiente(DataTable dataTable) {

        List<Map<String, String>> mensajes = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> mesa : mensajes) {
            String m = mesa.get("mensaje");


            String mensajeRecibido = baseTest.response.then()
                    .extract()
                    .xmlPath()
                    .getString("RESPUESTA.DefaultMessage");

            Assertions.assertEquals(m, mensajeRecibido,
                    "El mensaje recibido no conicide con el esperado"

            );
            LOGGER.info("Mensaje esperado: "+m);
            LOGGER.info("Mensaje resibido: "+mensajeRecibido);


        }
    }

}
