
@crearCuentasCrm
Feature:  crear cuenta de ahorrro y corrientes para personas naturar y juridica

  Scenario Outline: creacion de cuenta persona natura y juridica
    Given que tengo tipo de cedula "<tipced>" con numero "<numero>"
    When  la respuesta de la creacion debe ser 200
    Then  y debe traer la descripcion del campo como respuesta "<respuesta>"

    Examples:
    |tipced             |numero    |respuesta                                            |
    |urn://gov.co/C     |6541126   |OK                                                   |
    |urn://gov.co/c     |1018453361|"Fallas Tecnicas nos impiden procesar la Transaccion"|
    |urn://gov.co/N     |8300304120|Ok                                                   |
    |urn://gov.co/I     |7123458769|PK                                                   |
    |urn://gov.co/P     |2000406   |OK                                                   |
    |urn://gov.co/E     |70701     |OK                                                   |
    |urn://gov.co/R     |1100004122|OK                                                   |
    |urn://gov.co/T     |1097502415|OK                                                   |
    |urn://gov.co/      |          |                                                     |
    |                   |1097502413|"Fallas Tecnicas nos impiden procesar la Transaccion"|
    |$$%&#              |1022958482|"Fallas Tecnicas nos impiden procesar la Transaccion"|
    |urn://gov.co/C     |********  |"Fallas Tecnicas nos impiden procesar la Transaccion"|


