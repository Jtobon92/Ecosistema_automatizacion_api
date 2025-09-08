
@idCliente
Feature: Validación del campo LV0_ID en la solicitud

  Scenario Outline: Validar respuesta del servicio cuando el campo LV0_ID no cumple con los requisitos
    Given que el usuario realiza una solicitud al servicio con el campo LV0_ID "<body>"
    When se envía la solicitud al endpoint correspondiente para consultar cliente
    Then la respuesta debe tener un código de estado <codigo> siempre
    And el cuerpo de la respuesta debe contener el siguiente mensaje "<mensaje_error>"

    Examples:
      | body                      | mensaje_error                                               |codigo|
      | createVacioId.xml         | El número de documento es requerido                         |200   |
      | createNullId.xml          | Cliente no existe.                                          |200   |
      | tipoInválido.xml          | Cliente no existe.                                          |200   |
      | noEnviado.xml             | Cliente no existe.                                          |200   |
      | longitudCorta.xml         | El campo LV0_ID debe tener al menos 8 caracteres            |200   |
      | longitudLarga.xml         | El número de documento debe tener entre 1 y 17 caracteres   |200   |
      | createExitoso.xml         | Exitoso                                                     |200   |