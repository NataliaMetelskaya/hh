# HH Price page tests
==

## Run tests

For run tests use ./run.sh simple script or maven command line:
mvn verify

- Use -Djp.threads=3 maven parameter if you want to run all tests in many threads
- Use -Dconfig.browser=chrome(or firefox) maven parameter if you want to run tests in other browsers

## Test results

Test result location:
target/jbehave/view/index.html
target/jbehave/view/report.html
target/jbehave/view/navigator.html (works in firefox only)
target/jbehave/view/maps.html
