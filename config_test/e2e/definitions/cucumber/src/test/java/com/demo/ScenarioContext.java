package com.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class ScenarioContext extends ConcurrentHashMap<String, Object> {

  private final List<Runnable> cleanUps = new ArrayList<>();

  public void addCleanUpAfterScenario(Runnable cleanUp) {
    cleanUps.add(() -> {
      try {
        cleanUp.run();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    });
  }

  public void cleanUpAfterScenario() {
    cleanUps.parallelStream().forEach(Runnable::run);
  }
}
