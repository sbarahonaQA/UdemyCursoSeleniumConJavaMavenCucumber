@example
Feature: CentrosCosto
  Pruebas para entidad CentrosCosto
  
@test
Scenario: Crear centro de costo
  Given Cargar login
  Then Cargar elementos de la pagina Login.json
  And Al elemento Usuario se le pone texto gerenterrhh
  And Al elemento Contrasenia se le pone texto gerenterrhh
  And Click a BotonIniciarSesion
  Then Cargar elementos de la pagina CentrosCosto.json
  And Click a Organizacion
  And Click a EstructuraOrganizativa
  And Click a CentrosDeCosto
  And Click a Nuevo
  And Al elemento Descripcion se le pone texto CCO NUEVO
  And Al elemento Abreviatura se le pone texto CCON
  And Al elemento NomenContable se le pone texto NOM
  And Al elemento CtaContable se le pone texto 1234
  And Seleccionar ASEINFO Mexico de la lista Compania
  And Seleccionar Comercial de la lista AreaFuncional
  And Capturar pantalla: nuevo
  And Click a GuardarCentroDeCosto
  And Capturar pantalla: nuevo_resultado
  Then Prueba exitosa si elemento Mensaje contiene texto Centro de costo guardado correctamente


  @test
  Scenario: Editar centro de costo
    Given Cargar login
    Then Cargar elementos de la pagina Login.json
    And Al elemento Usuario se le pone texto gerenterrhh
    And Al elemento Contrasenia se le pone texto gerenterrhh
    And Click a BotonIniciarSesion
    Then Cargar elementos de la pagina CentrosCosto.json
    And Click a Organizacion
    And Click a EstructuraOrganizativa
    And Click a CentrosDeCosto
    And Buscar este termino 1234 en CajaBusquedaSmartlist
    And Click a PrimerElementoDeSmartlist
    And Click a Editar
    And Al elemento Descripcion se le pone texto CCO EDITADO
    And Capturar pantalla: editar
    And Click a GuardarCentroDeCosto
    And Capturar pantalla: editar_resultado
    Then Prueba exitosa si elemento Mensaje contiene texto Centro de costo guardado correctamente


  @test
  Scenario: Eliminar centro de costo
    Given Cargar login
    Then Cargar elementos de la pagina Login.json
    And Al elemento Usuario se le pone texto gerenterrhh
    And Al elemento Contrasenia se le pone texto gerenterrhh
    And Click a BotonIniciarSesion
    Then Cargar elementos de la pagina CentrosCosto.json
    And Click a Organizacion
    And Click a EstructuraOrganizativa
    And Click a CentrosDeCosto
    And Buscar este termino 1234 en CajaBusquedaSmartlist
    And Click a PrimerElementoDeSmartlist
    And Click a Eliminar
    And Aceptar aletar
    And Esperar que el elemento Mensaje este presente
    And Capturar pantalla: eliminar_resultado
    Then Prueba exitosa si elemento Mensaje contiene texto Centro de costo eliminado correctamente