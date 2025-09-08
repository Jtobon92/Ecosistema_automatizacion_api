
@consultaaddcase
Feature: validar un usuario addcase y que tiene casos creados

  Scenario: verificar que el caso fue creado
    Given que se recibe una peticion "consulta.xml"
    When verifico el caso nuevo
    Then debe mandar status 201
    And el mensaje de respuesta debe ser "Ok 0"