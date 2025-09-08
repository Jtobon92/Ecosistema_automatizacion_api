Feature: Validación del campo obligatorio servicio add case campo LV1_PRODUCT_ID

  Scenario Outline: verificar la obligatoriedad del campo LV1_PRODUCT_ID
    Given  que envion el siguiente cuerpo en xml "<body>"
    When se envia el endpoint correspondiente del servicio
    Then debe contestar con un estado de codigo <codigo>
    And  de nos ser asi el mensaje debe ser el siguiente "<mensaje>"


    Examples:
    |body                       |codigo       |mensaje                                            |
    |createExitosoProd.xml      |200          |Exitoso                                            |
    |createVacioProd.xml        |200          |La línea de producto es requerida                  |
    |createinvalitProd.xml      |200          |Tipo de producto no valido                         |
    |createNullProd.xml         |200          |Tipo de producto no valido                         |
    |createNoexisteProd.xml     |200          |Producto no existe o se encuentra en estado cerrado|
    |createOtroExistenteProd.xml|200          |Exitoso                                            |