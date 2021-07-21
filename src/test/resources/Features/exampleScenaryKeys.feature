@example
Feature: exampleScenaryKeys
  Optional description of the feature
  
@test
Scenario: Crear centro de costo
  Given Estoy en el login
  Then Cargo los elementos de la pagina Login.json
  And Le doy click a Usuario
  And Al elemento Usuario le pongo este texto gerenterrhh
  And Le doy click a Contrasenia
  And Al elemento Contrasenia le pongo este texto gerenterrhh
  And Le doy click a BotonIniciarSesion
  Then Cargo los elementos de la pagina CentrosCosto.json
  And Le doy click a Organizacion
  And Le doy click a EstructuraOrganizativa
  And Le doy click a CentrosDeCosto
  And Le doy click a Nuevo
  And Al elemento Descripcion le pongo este texto CCO NUEVO
  And Al elemento Abreviatura le pongo este texto CCON
  And Al elemento NomenContable le pongo este texto NOM
  And Al elemento CtaContable le pongo este texto 1234
  And Selecciono ASEINFO Mexico en lista Compania
  And Selecciono Comercial en lista AreaFuncional
  And Captura pantalla: nuevo
  And Le doy click a GuardarCentroDeCosto
  And Captura pantalla: nuevo_resultado
  Then Prueba exitosa si elemento Mensaje contiene texto Centro de costo guardado correctamente



  @test
  Scenario: Save In Scenario Context
    Given I am in App main site
    Then I load the DOM Information Spotify_registro.json
    And I click in element Email
    And I set element Email with text chamba28@hotmail.com
    And I click in element Email Confirmacion
    Then Assert if Email Error contains text Este correo electrónico ya está conectado a una cuenta
    #And I scroll to element Already
    #And I Save text of Already as Scenario Context
    #Then Save as Scenario Context key MiVariable with value MiValor


  @test
  @Security
  Scenario: Recover data from Scenario Context
    Given I am in App main site
    Then I load the DOM Information Spotify_registro.json
    And I click in element Email
    And I set element Email with text mervindiazlugo@gmail.com
    And I click in element Email Confirmacion
    And I set Email with key value Already.text
    Then Save as Scenario Context key MiVariable with value MiValor
