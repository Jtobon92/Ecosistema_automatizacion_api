@validarCamposCed
Feature: consumo de los servicios crm siebel servicio BB_CLPNGETCPSREQ

  Scenario Outline: que se hace validacion personalizada en el cuerpo XML
    Given el cliente tiene un numeo de cedula <cedula> con tipo "<tipo>"
    When  la respuesta debe tener de respuesta un codigo 200
    Then  se debe comparar el texto de la respuesta con "<mensaje>"

    Examples:
    |tipo |cedula          |mensaje |
    |C    |1018453361      |0       |
    |c    |1018453361      |0       |
    |N    |800191323       |0       |
    |     |1018453361      |0       |
    |E    |211189          |0       |

