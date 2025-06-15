#!/usr/bin/env bash

function get_total_test_count() {
  tags=$1
  mvn clean verify -Dspring.profiles.active=local -Dcucumber.execution.dry-run=true -Dcucumber.filter.tags="$tags" > mvn.log
  grep "Tests run" mvn.log > line.log | head -1
  run=$(grep -o -E "Tests run: [[:digit:]]+" line.log | cut -d' ' -f3 | head -1)
  skipped=$(grep -o -E "Skipped: [[:digit:]]+" line.log | cut -d' ' -f2 | head -1)
  rm mvn.log
  rm line.log
  echo $(($run-$skipped))
}
