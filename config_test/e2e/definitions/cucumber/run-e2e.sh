#!/usr/bin/env bash

echo "Running E2E tests..."

# Ejecuta los tests con Maven
mvn verify

# Captura el código de salida
exit_code=$?

if [ $exit_code -eq 0 ]; then
  echo "E2E tests passed."
else
  echo "E2E tests failed."
fi

exit $exit_code