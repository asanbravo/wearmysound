package com.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@CucumberContextConfiguration
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class,
    classes = {SpringConfiguration.class})
public class SpringCucumberContext {

  private static ApplicationContext applicationContext;

  private static int counter = 0;

  @Autowired
  private ScenarioContext scenarioContext;

  @Autowired
  public void setApplicationContext(ApplicationContext applicationContext) {
    SpringCucumberContext.applicationContext = applicationContext;
  }

  /**
   * Need this method so the cucumber will recognize this class as glue and load spring context configuration
   */
  @Before
  public void setUpSpring(Scenario scenario) {
    counter = counter + 1;
    var total_count = System.getProperty("total_count");

    log.info("-----------------------");
    log.info("Ejecutando: " + counter + "/" + total_count);
    log.info("-----------------------");
  }

  @After
  public void cleanUp(Scenario scenario) {
    scenarioContext.cleanUpAfterScenario();
  }

  @AfterAll
  public static void checkNotMatchedTrafficParrotMapping() throws IOException, InterruptedException {
  }
}