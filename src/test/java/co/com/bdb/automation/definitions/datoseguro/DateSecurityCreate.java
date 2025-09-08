package co.com.bdb.automation.definitions.datoseguro;

import co.com.bdb.automation.definitions.BaseTest;
import co.com.bdb.automation.definitions.addcaseqa.ValidateIdFieldDefinitios;
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

public class DateSecurityCreate {
    //linea para usar logger y comentariar el paso a paso
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateIdFieldDefinitios.class);
    static EnvironmentValuesTask env = new EnvironmentValuesTask();
    private static final String BASE_URL = env.getenv("BASE_URL_SIEBEL");
    private static final String BASE_PATH = "SiebelManagement/V1/Enterprise/BB_UPDSECREQ/updateSecureData/";
    private CustomRequestSpecification request;
    private final BaseTest baseTest;
    private final String bodiesPath = "src/test/resources/bodies/apiTest/validatedatoseguro/";
    private File bodyFile;

    public DateSecurityCreate(BaseTest baseTest) {
        this.baseTest = baseTest;
    }

    @Given("que se envia un cuerpo xml para la validacion {string}")
    public void queSeEnviaUnCuerpoXmlParaLaValidacion(String xmlPath) {
        RestAssured.useRelaxedHTTPSValidation("SSL");
        bodyFile = new File(bodiesPath + xmlPath);
        request = new CustomRequestSpecification(RestAssured.given().log().all()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/xml; charset=UTF-8")
                .body(bodyFile)
                .basePath(BASE_PATH));


    }
    @When("se cargue el endpoit correctamente del servicio")
    public void seCargueElEndpoitCorrectamenteDelServicio() {
        baseTest.response = request
                .when()
                .post()
                .then()
                .log().all()
                .extract()
                .response();

    }
    @Then("la respuesta del estado es {int}")
    public void laRespuestaDelEstadoEs(Integer id) {
        request = new CustomRequestSpecification(
                RestAssured
                        .given()
                        .log().all()
                        .baseUri(BASE_URL)
                        .basePath(BASE_PATH + id));
    }
    @Then("el mensaje que comparte el response es {string}")
    public void elMensajeQueComparteElResponseEs(String esperado) {

        String mensajeRecibido = baseTest.response.then()
                .extract()
                .xmlPath()
                .getString("RESPUESTA.MESSAGE_TEXT");

        Assertions.assertEquals(esperado, mensajeRecibido,
                "El mensaje recibido no conicide con el esperado"

        );
        LOGGER.info("Mensaje esperado: "+esperado);
        LOGGER.info("Mensaje resibido: "+mensajeRecibido);

    }

}
