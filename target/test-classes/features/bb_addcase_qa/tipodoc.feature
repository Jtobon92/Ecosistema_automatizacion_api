@addcasetidoc
Feature: Validación del campo obligatorio LV0_TIPO_DOC
  @addcase
  Scenario Outline: Validar respuesta del servicio cuando el campo LV0_TIPO_DOC no cumple con los requisitos
    Given que el usuario realiza una solicitud al servicio con el campo LV0_TIPO_DOC "<body>"
    When la respuesta debe tener un código de estado <codigo>
    Then el cuerpo de la respuesta debe contener el mensaje de error "<mensaje_error>"

    Examples:
      | body                    |mensaje_error                                       |codigo|
      | createVacio.xml         |El tipo de documento de tener maximo un caracter    |200   |
      | createNulo.xml          |El tipo de documento de tener maximo un caracter    |200   |
      | createNoEnviado.xml     |Cliente no existe.                                  |200   |
      | createExitoso.xml       |Exitoso                                             |200   |
      | createInvalido.xml      |Cliente no existe.                                  |200   |
