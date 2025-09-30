@ConsultaSiebel
Feature: consumo de los servicios crm siebel servicio BB_CLPNGETCPSREQ

  Scenario: conultar la existencia de un cliente en crm siebel
    Given que se realza el envio del cuerpo de la peticion "consultaClienteSiebel.xml"
    When debe contestar con estado del codigo 200
    Then arrojar en la etiqueta del response el siguiente mensaje "0"


    Scenario: consultar que el cliente no esta creado en crm siebel
      Given que se realizo un envio del cuerpo de la peticion "consultaClienteSiebel.xml"
      When  el cliente no existe en la base de datos dando codigo 200
      Then el mensaje que debe responde debe ser el siguiente
      |mensaje|
      |SETID:BB001 El cliente no existe (0,0) BB_CLPNGETCPSREQ_PK.BB_CLPNGETCPSREQ_CL.OnExecute  Name:OnRequest  PCPC:4288  Statement:47 |

