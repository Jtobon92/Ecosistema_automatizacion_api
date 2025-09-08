package co.com.bdb.automation.definitions.addcaseqa;

import co.com.bdb.automation.definitions.BaseTest;
import co.com.bdb.automation.utilities.CustomRequestSpecification;
import co.com.bdb.automation.utilities.EnvironmentValuesTask;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.util.ElementScanner6;
import java.io.File;




public class ValidateIdFieldDefinitios {
    //linea para usar logger y comentariar el paso a paso
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateIdFieldDefinitios.class);
    static EnvironmentValuesTask env = new EnvironmentValuesTask();
    private static final String BASE_URL = env.getenv("BASE_URL_ADDCASE_QA");
    private static final String BASE_PATH = "bbog/uat/SiebelManagement/V1/Enterprise/BB_ADDCASE_REQ/creationCasesBM/";
    private CustomRequestSpecification request;
    private final BaseTest baseTest;
    private final String bodiesPath = "src/test/resources/bodies/apiTest/bb_addcase_qa/idcliente/";
    private File bodyFile;

    public ValidateIdFieldDefinitios(BaseTest baseTest) {
        this.baseTest = baseTest;
    }

    @Given("que el usuario realiza una solicitud al servicio con el campo LV0_ID {string}")
    public void queElUsuarioRealizaUnaSolicitudAlServicioConElCampoLV0ID(String xmlPath) {
        RestAssured.useRelaxedHTTPSValidation("SSL");
        bodyFile = new File(bodiesPath + xmlPath);
        request = new CustomRequestSpecification(RestAssured.given().log().all()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/xml; charset=UTF-8")
                .body(bodyFile)
                .basePath(BASE_PATH));

    }
    @When("se envía la solicitud al endpoint correspondiente para consultar cliente")
    public void seEnvíaLaSolicitudAlEndpointCorrespondienteParaConsultarCliente() {

        baseTest.response = request
                .when()
                .post()
                .then()
                .log().all()
                .extract()
                .response();

    }
    @Then("la respuesta debe tener un código de estado {int} siempre")
    public void laRespuestaDebeTenerUnCódigoDeEstadoSiempre(Integer id) {

        request = new CustomRequestSpecification(
                RestAssured
                        .given()
                        .log().all()
                        .baseUri(BASE_URL)
                        .basePath( BASE_PATH+id));

    }
    @Then("el cuerpo de la respuesta debe contener el siguiente mensaje {string}")
    public void elCuerpoDeLaRespuestaDebeContenerElSiguienteMensaje(String esperado) {

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
