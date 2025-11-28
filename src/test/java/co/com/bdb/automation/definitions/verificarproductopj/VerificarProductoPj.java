package co.com.bdb.automation.definitions.verificarproductopj;

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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VerificarProductoPj {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateIdFieldDefinitios.class);
    static EnvironmentValuesTask env = new EnvironmentValuesTask();
    private static final String BASE_URL = env.getenv("BASE_URL_SIEBEL");
    private CustomRequestSpecification request;
    private final BaseTest baseTest;
    private String xmlBody;
    private final String bodiesPath = "src/test/resources/bodies/apiTest/verificarproductopj/verificarproductopj.xml";


    public VerificarProductoPj(BaseTest baseTest) {
        this.baseTest = baseTest;
    }

    @Given("que se necesita verificar los {string} con tipo {string} con el producto {string} fondo sumar")
    public void queSeNecesitaVerificarLosConTipoConElProductoFONDOSUMAR(String cedula, String tipo, String producto) throws IOException {

        Path path = Paths.get(bodiesPath);
        xmlBody = Files.readString(path);

        xmlBody = xmlBody.replace("${cedula}", String.valueOf(Long.valueOf(cedula)))
                .replace("${producto}", String.valueOf(producto))
                .replace("${tipo}",tipo);


        request = new CustomRequestSpecification(RestAssured.given().log().all()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/xml; charset=UTF-8")
                .body(xmlBody)
                .basePath(""));


    }
    @When("la respuesta del codigo de status debe ser {int}")
    public void laRespuestaDelCodigoDeStatusDebeSer(Integer statusCode) {

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
    @Then("y el mensaje de la respuesta es el siguiente {string}")
    public void yElMensajeDeLaRespuestaEsElSiguiente(String esperado) {

        String mensajeRecibido = baseTest.response.then()
                .extract()
                .xmlPath()
                .getString("RESPUESTA.INFOGENERAL1.BB_TIPOCLTE_DESC");

        Assertions.assertEquals(esperado, mensajeRecibido,
                "El mensaje recibido no conicide con el esperado"


        );
        LOGGER.info("MENSAJE ESPERADO: "+esperado);
        LOGGER.info("MENSAJE RECIBIDO: "+mensajeRecibido);


    }


}
