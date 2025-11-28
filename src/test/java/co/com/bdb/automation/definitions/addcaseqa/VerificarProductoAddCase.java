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

public class VerificarProductoAddCase {


    //linea para usar logger y comentariar el paso a paso
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateIdFieldDefinitios.class);
    static EnvironmentValuesTask env = new EnvironmentValuesTask();
    private static final String BASE_URL = env.getenv("BASE_URL_ADDCASE_QA");
    private static final String BASE_PATH = "bbog/uat/SiebelManagement/V1/Enterprise/BB_ADDCASE_REQ/creationCasesBM/";
    private CustomRequestSpecification request;
    private final BaseTest baseTest;
    private final String bodiesPath = "src/test/resources/bodies/apiTest/bb_addcase_qa/produccliente/";
    private File bodyFile;

    public VerificarProductoAddCase(BaseTest baseTest) {
        this.baseTest = baseTest;
    }

    @Given("que envion el siguiente cuerpo en xml {string}")
    public void queEnvionElSiguienteCuerpoEnXml(String xmlPath) {
        RestAssured.useRelaxedHTTPSValidation("SSL");
        bodyFile = new File(bodiesPath + xmlPath);
        request = new CustomRequestSpecification(RestAssured.given().log().all()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/xml; charset=UTF-8")
                .body(bodyFile)
                .basePath(BASE_PATH));

    }

    @When("debe contestar con un estado de codigo {int}")
    public void debeContestarConUnEstadoDeCodigo(Integer statusCodeExpected) {
        baseTest.response = request.when().post().then().log().all().extract().response();
        baseTest.response.then().log().all();
        baseTest.response.then().statusCode(statusCodeExpected);

    }
    @Then("de nos ser asi el mensaje debe ser el siguiente {string}")
    public void deNosSerAsiElMensajeDebeSerElSiguiente(String esperado) {

        String mensajeRecibido = baseTest.response.then()
                .extract()
                .xmlPath()
                .getString("RESPUESTA.MESSAGE_DESCR");

        Assertions.assertEquals(esperado, mensajeRecibido,
                "El mensaje recibido no conicide con el esperado"

        );
        LOGGER.info("Mensaje esperado: "+esperado);
        LOGGER.info("Mensaje resibido: "+mensajeRecibido);
        System.out.println("MENSAJE ESPERADO"+ esperado);


    }
}
