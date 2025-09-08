package co.com.bdb.automation.definitions.createclintreq;

import co.com.bdb.automation.definitions.BaseTest;
import co.com.bdb.automation.utilities.CustomRequestSpecification;
import co.com.bdb.automation.utilities.EnvironmentValuesTask;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;

import java.io.File;

public class createClientDefinition {

    static EnvironmentValuesTask env = new EnvironmentValuesTask();
    private static final String BASE_URL = env.getenv("BASE_URL_SIEBEL");
    private static final String BASE_PATH = "bbog/uat/SiebelManagement/V1/Enterprise/BB_CNCLPNADDPSREQ/creationNaturalPerson/";
    private CustomRequestSpecification request;
    private final BaseTest baseTest;
    private final String bodiesPath = "src/test/resources/bodies/apiTest/bb_cnclpnaddpsreq/validacioncampos/";
    private File bodyFile;

    public createClientDefinition(BaseTest baseTest) {
        this.baseTest = baseTest;
    }


    @Given("que el usuario realiza una solicitud al servicio con el campo en el body {string}")
    public void queElUsuarioRealizaUnaSolicitudAlServicioConElCampoAANITEnElBody(String xmlPath) {

        RestAssured.useRelaxedHTTPSValidation("SSL");
        bodyFile = new File(bodiesPath + xmlPath);
        request = new CustomRequestSpecification(RestAssured.given().log().all()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/xml; charset=UTF-8")
                .body(bodyFile)
                .basePath(BASE_PATH));

    }
    @When("se envía el consumo al endpoint correspondiente")
    public void seEnvíaElConsumoAlEndpointCorrespondiente() {
        baseTest.response = request
                .when()
                .post()
                .then()
                .log().all()
                .extract()
                .response();

    }
    @Then("la respuesta posee un código de estado {int}")
    public void laRespuestaPoseeUnCódigoDeEstado(Integer id) {
        request = new CustomRequestSpecification(
                RestAssured
                        .given()
                        .log().all()
                        .baseUri(BASE_URL)
                        .basePath(BASE_PATH + id));

    }
    @Then("el cuerpo de la respuesta debe contener el mensaje {string}")
    public void elCuerpoDeLaRespuestaDebeContenerElMensaje(String esperado) {
        String mensajeRecibido = baseTest.response.then()
                .extract()
                .xmlPath()
                .getString("RESPUESTA.DefaultMessage");

        Assertions.assertEquals(esperado, mensajeRecibido,
                "El mensaje recibido no conicide con el esperado"
        );

    }



}
