# selenium
command line selenium

=======================================
Approach 1

sudo apt-get install xvfb

DISPLAY=:1 xvfb-run java -jar ~/installation/selenium/selenium-server-standalone-3.4.0.jar

=====================================

Approach 2

java -jar -Dwebdriver.firefox.driver=~/installations/selenium/geckodriver ~/installation/selenium/selenium-server-standalone-3.4.0.jar -role hub -hubConfig ~/installation/selenium/hub.json

java -jar ~/installation/selenium/selenium-server-standalone-3.4.0.jar -role webdriver -hub http://localhost:4444/grid/register -port 5556 -browser browserName=firefox
