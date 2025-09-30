package co.com.bdb.automation.definitions.addcaseqa;


import co.com.bdb.automation.definitions.BaseTest;
import co.com.bdb.automation.utilities.CustomRequestSpecification;
import co.com.bdb.automation.utilities.EnvironmentValuesTask;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;


import java.io.File;



public class AddCaseDefinitions {
    static EnvironmentValuesTask env = new EnvironmentValuesTask();
    private static final String BASE_URL = env.getenv("BASE_URL_ADDCASE_QA");
    private static final String BASE_PATH = "bbog/uat/SiebelManagement/V1/Enterprise/BB_ADDCASE_REQ/creationCasesBM/";
    private CustomRequestSpecification request;
    private final BaseTest baseTest;
    private final String bodiesPath = "src/test/resources/bodies/apiTest/bb_addcase_qa/tipodoc/";
    private File bodyFile;

    public AddCaseDefinitions(BaseTest baseTest) {
        this.baseTest = baseTest;
    }

    @Given("que el usuario realiza una solicitud al servicio con el campo LV0_TIPO_DOC {string}")
    public void queElUsuarioRealizaUnaSolicitudAlServicioConElCampoLV0TIPODOC(String xmlPath) {

        RestAssured.useRelaxedHTTPSValidation("SSL");
        bodyFile = new File(bodiesPath + xmlPath);
        request = new CustomRequestSpecification(RestAssured.given().log().all()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/xml; charset=UTF-8")
                .body(bodyFile)
                .basePath(BASE_PATH));

    }

    @When("la respuesta debe tener un código de estado {int}")
    public void laRespuestaDebeTenerUnCódigoDeEstado(Integer statusCode) {
        baseTest.response = request
                .when()
                .post()
                .then()
                .log().all()
                .extract()
                .response();
        baseTest.response.then().log().all();
        baseTest.response.then().statusCode(statusCode);
    }
    @Then("el cuerpo de la respuesta debe contener el mensaje de error {string}")
    public void elCuerpoDeLaRespuestaDebeContenerElMensajeDeError(String esperado) {

        String mensajeRecibido = baseTest.response.then()
                .extract()
                .xmlPath()
                .getString("RESPUESTA.MESSAGE_DESCR");

        Assertions.assertEquals(esperado, mensajeRecibido,
                "El mensaje recibido no conicide con el esperado"

        );

    }



}
