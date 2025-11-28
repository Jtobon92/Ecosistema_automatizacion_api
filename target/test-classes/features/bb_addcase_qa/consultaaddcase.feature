
@consultaaddcase
Feature: validar un usuario addcase y que tiene casos creados

  Scenario: verificar que el caso fue creado
    Given que se recibe una peticion "consulta.xml"
    When debe mandar status 201
    Then el mensaje de respuesta debe ser "Ok 0"


     ### YA USADO ###