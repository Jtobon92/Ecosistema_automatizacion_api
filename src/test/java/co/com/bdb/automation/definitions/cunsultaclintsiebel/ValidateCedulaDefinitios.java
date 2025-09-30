package co.com.bdb.automation.definitions.cunsultaclintsiebel;

import co.com.bdb.automation.definitions.BaseTest;
import co.com.bdb.automation.definitions.addcaseqa.ValidateIdFieldDefinitios;
import co.com.bdb.automation.utilities.CustomRequestSpecification;
import co.com.bdb.automation.utilities.EnvironmentValuesTask;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ValidateCedulaDefinitios {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateIdFieldDefinitios.class);
    static EnvironmentValuesTask env = new EnvironmentValuesTask();
    private static final String BASE_URL = env.getenv("BASE_URL_SIEBEL");
    private CustomRequestSpecification request;
    private final BaseTest baseTest;
    private String xmlBody;
    private final String bodiesPath = "src/test/resources/bodies/apiTest/bb_consultaClinte/validateCamposConsulta.xml";

    public ValidateCedulaDefinitios(BaseTest baseTest) {
        this.baseTest = baseTest;
    }

    @Given("el cliente tiene un numeo de cedula {int} con tipo {string}")
    public void elClienteTieneUnNumeoDeCedulaConTipo(Integer cedula, String tipo) throws IOException {
        Path path = Paths.get(bodiesPath);
        xmlBody = Files.readString(path);

        xmlBody = xmlBody.replace("${cedula}", String.valueOf(cedula))
                         .replace("${tipo}",tipo);

        request = new CustomRequestSpecification(RestAssured.given().log().all()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/xml; charset=UTF-8")
                .body(xmlBody)
                .basePath(""));

        baseTest.response = request
                .when()
                .post()
                .then()
                .log().all()
                .extract()
                .response();

    }
    @When("la respuesta debe tener de respuesta un codigo {int}")
    public void laRespuestaDebeTenerDeRespuestaUnCodigo(Integer id) {

        request = new CustomRequestSpecification(
                RestAssured
                        .given()
                        .log().all()
                        .baseUri(BASE_URL)
                        .basePath(""+id));

    }
    @Then("se debe comparar el texto de la respuesta con {string}")
    public void seDebeCompararElTextoDeLaRespuestaCon(String string) {

    }
}
