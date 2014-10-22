# HH Price page tests

## Run tests

Use simple script for run tests on the  Unix systems:
  ./run.sh
Use maven commands for Windows: 
  mvn clean install -Djp.skip=false

## Additional parameters

-Djp.threads=3 			- if you want to run all tests in any threads
-Dconfig.browser=chrome		- if you want to run tests in other browsers (chromeor or firefox)

## Test results

Test result location:
- target/jbehave/view/index.html
- target/jbehave/view/report.html
- target/jbehave/view/navigator.html (works in firefox only)
- target/jbehave/view/maps.html
