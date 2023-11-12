#Autor: Julián Ávila

  @HistoriaDeUsuario
  Feature: Check the title of the metis web application panel
    As a user, I log in to the metis application to verify the title of the form validation panel

  @CasoTituloPanel
  Scenario Outline: Verify the title of the form validation panel
    Given julian enters the metis web app
    When enter your credentials and enter the app
    | userName   | password   |
    | <userName> | <password> |
    And julian enters the form validation option
    Then julian checks that the panel title is correct
    | panelTitle   |
    | <panelTitle> |
    Examples:
      | userName | password | panelTitle     |
       ##@externaldata@./src/test/resources/datadriven/FormPanelValidation.xlsx@PanelTitle
      |demo|demo|Form Validation|
