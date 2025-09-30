package co.com.bdb.automation.definitions.addcaseqa;

import co.com.bdb.automation.definitions.BaseTest;
import co.com.bdb.automation.utilities.CustomRequestSpecification;
import co.com.bdb.automation.utilities.EnvironmentValuesTask;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ConsultaAddCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsultaAddCase.class);
    static EnvironmentValuesTask env = new EnvironmentValuesTask();
    private static final String BASE_URL = env.getenv("BASE_URL_SIEBEL");
    private static final String BASE_PATH = "";
    private CustomRequestSpecification request;
    private final BaseTest baseTest;
    private final String bodiesPath = "src/test/resources/bodies/apiTest/bb_addcase_qa/consultaaddcase/";
    private File bodyFile;

    public ConsultaAddCase(BaseTest baseTest) {
        this.baseTest = baseTest;
    }

    @Given("que se recibe una peticion {string}")
    public void queSeRecibeUnaPeticion(String xmlPath) {

        RestAssured.useRelaxedHTTPSValidation("SSL");

        try {
            // Leer el contenido del archivo como texto
            String rutaCompleta = bodiesPath + xmlPath;
            String contenidoBody = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(rutaCompleta)));
            // Crear la especificación con el contenido real
            request = new CustomRequestSpecification(RestAssured.given().log().all().baseUri(BASE_URL).header("Content-Type", "application/xml; charset=UTF-8").body(contenidoBody).basePath(""));
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el archivo JSON: " + e.getMessage());
        }

    }

    @Then("debe mandar status {int}")
    public void debeMandarStatus(Integer statusCodeExpected) {

        baseTest.response = request.when().post().then().log().all().extract().response();
        baseTest.response.then().log().all();
        baseTest.response.then().statusCode(statusCodeExpected);

    }

    @Then("el mensaje de respuesta debe ser {string}")
    public void elMensajeDeRespuestaDebeSer(String esperado) {
        String mensajeRecibido = baseTest.response.then().extract().xmlPath().getString("RESPUESTA.MESSAGE_TEXT");
        Assertions.assertEquals(esperado, mensajeRecibido, "El mensaje recibido no conicide con el esperado"

        );
        LOGGER.info("Mensaje esperado: " + esperado);
        LOGGER.info("Mensaje recibido: " + mensajeRecibido);
    }


}
