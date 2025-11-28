
@bbupdsecreq
Feature: verificacion de los servicios adcase dato seguro 36_BB_UPDSECREQ

  Scenario Outline: verificar obligatoridad de los campo para el servicio BB_UPDSECREQ
    Given que se envia un cuerpo xml para la validacion "<body>"
    When  la respuesta del estado es <codigo>
    Then  el mensaje que comparte el response es "<mensaje>"

    Examples:
    |body|codigo|mensaje     |
    |tipDocVacio.xml         |200      |Cliente no existe:   1018453361 (0,0) BB_UPDSECREQ_PK.BB_UPDSECREQ.OnExecute  Name:OnRequest  PCPC:2824  Stat      |
    |tipoDocNumeros.xml      |200      |Error de validacion de camposCampo : TIPO DE DOCUMENTO CLIENTE  Con el valor :  15254353       (0,0) BB_UPD           |
    |tipoDocCaracter.xml     |200      |Error de validacion de camposCampo : TIPO DE DOCUMENTO CLIENTE  Con el valor :  **@       (0,0) BB_UPD  |
    |tipoDocMinuscula.xml    |200      |Error de validacion de camposCampo : TIPO DE DOCUMENTO CLIENTE  Con el valor :  c       (0,0) BB_UPD    |
    |numCcVacio.xml          |200      |Cliente no existe:  C  (0,0) BB_UPDSECREQ_PK.BB_UPDSECREQ.OnExecute  Name:OnRequest  PCPC:2824  Stat    |
    |numCcNull.xml           |200      |Cliente no existe:  C null (0,0) BB_UPDSECREQ_PK.BB_UPDSECREQ.OnExecute  Name:OnRequest  PCPC:25        |
    |numCcLetras.xml         |200      |Cliente no existe:  C abcd xyz (0,0) BB_UPDSECREQ_PK.BB_UPDSECREQ.OnExecute  Name:OnRequest  PCPC:25    |
    |sinDatosSeguro.xml      |200      |Ingrese informacion a actualizar. (0,0) BB_UPDSECREQ_PK.BB_UPDSECREQ.OnExecute  Name:OnRequest  PCPC    |
    |casoExitoso.xml         |200      |Actualizacion Exitosa.                                                                                  |
    |longituNumCel.xml       |200      |El numero de celular: 3125 no es válido, favor validar la longitud del mismo. (0,0) INFO_SEG.DA         |
    |longituMayornumCel.xml  |200      |El numero de celular: 31258878735534523535354367377 no es válido, favor validar la longitud del mismo. (0,0) INFO_SEG.DA|
    |correoInvalido.xml      |200      |El correo electrónico: abc no es válido, favor validar la dirección. (0,0) INFO_SEG.DATOS               |
    |correoSinAroa.xml       |200      |El correo electrónico: jtobonbancodebogota.com.co no es válido, favor validar la dirección. (0,0) INFO_SEG.DATOS        |


     ### YA USADO ###