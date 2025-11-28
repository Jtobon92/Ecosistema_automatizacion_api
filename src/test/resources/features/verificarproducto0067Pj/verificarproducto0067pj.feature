
@verificarProductopj
Feature: verificar que los cliente PN si poseen este producto y los de tipo documento
         diferente no lo tengan

  Scenario Outline: verificar exitencia del producto  fondo sumar inversion
    Given que se necesita verificar los "<cliente>" con tipo "<tipoCli>" con el producto "<producto>" fondo sumar
    When  la respuesta del codigo de status debe ser 200
    Then y el mensaje de la respuesta es el siguiente "<mensaje>"
    Examples:

    |tipoCli |cliente|mensaje                |producto|
    |N       |356329    |Persona Juridica    |0067    |
    |N       |9985555770|Persona Juridica    |0067    |
    |P       |61028     |Persona Natural     |0067    |
    |E       |412571    |Persona Natural     |0067    |
    |T       |5266059   |Persona Juridica    |0067    |
    |R       |1100003275|Persona Juridica    |0067    |
    |        |1100003275|Persona Juridica    |0067    |
    |C       |80851133  |Persona Juridica    |0067    |
    |c       |52475211  |Persona Natural     |0067    |
    |C       |79801295  |Persona Natural     |0067    |
    |C       |80797417  |Persona Natural     |0067    |
    |C       |80793093  |Persona Natural     |0067    |
    |#$$%    |1032496132|Persona Natural     |0067    |
    |N       |dshgfdasy |Persona Natural     |0067    |
    |N       |9985555770|Persona Juridica    |130ML   |
    |C       |52475211  |Persona Natural     |130ML   |
    |E       |412571    |Persona Natural     |130ML   |
    |C       |80797417  |Persona Natural     |020AH   |
    |N       |9985555770|Persona Juridica    |020AH   |
    |N       |9985555770|Persona Juridica    |        |






