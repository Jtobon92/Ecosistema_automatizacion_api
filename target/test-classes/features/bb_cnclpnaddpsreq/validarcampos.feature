@createClient
Feature: verificar el api para creacion persona natural

  Scenario Outline: Validar respuesta del servicio cuando el campo <campo> no cumple con los requisitos
    Given que el usuario realiza una solicitud al servicio con el campo en el body "<body>"
    When se envía el consumo al endpoint correspondiente
    Then la respuesta posee un código de estado <codigo>
    And el cuerpo de la respuesta debe contener el mensaje "<mensaje>"

    Examples:
      | campo          | body                        | mensaje                                                                         | codigo |
      | AA_NIT         | createVacio.xml             |'Número de documento' es un campo requerido. Especifique un valor para el campo  | 200    |
      | AA_NIT         | createFueraRango.xml        | Cannot invoke "String.equals(Object)" because the return value of "com.bdb.siebel.msclientmngorq.clients.restrictivelist.models.response.ValCreClientResponse$ValCreClientResponseDetails.getValidationFlag()" is null           | 200    |
      | AA_NIT         | createExiste.xml            | Ya existen los datos del cliente, proceda a modificarlos                        | 200    |
      | AA_NIT         | createCaracteres.xml        | Unexpected character '*' (code 42) (expected a name start character)            | 200    |
      | AA_NIT         | createLetras.xml            | Por favor ingresa solo numeros                                                  | 200    |
      | BB_TIPO_DOC_PN | tipoDocVacio.xml            | 'Tipo de documento' es un campo requerido. Especifique un valor para el campo   | 200    |
      | BB_TIPO_DOC_PN | tipoDocDiferente.xml        | Error en el digito de chequeo ingresado para este  NIT (28100,81) BB_FUNCLIB_PN.SEX.FieldFormula  Name:bb_valida_nit_pn  PCPC:18488  Statement:235| 200    |
      | BB_TIPO_DOC_PN | tipoDocCaracter.xml         | Cliente se encuentra en listas restrictivas o sarlaft, por favor comunícate con la DUCC                                              | 200    |
      | LAST_NAME      | primerApeVacio.xml          | 'Primer apellido' es un campo requerido. Especifique un valor para el campo     | 200    |
      | FIRST_NAME     | primerNomVacio.xml          | 'Primer nombre' es un campo requerido. Especifique un valor para el campo       | 200    |
      | AA_NIT         | createExitoso.xml           | Transaccion exitosa (28000,11)                                                  | 200    |