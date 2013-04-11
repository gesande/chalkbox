chalkbox - using ansi colors when writing text 
trying to encapsulate the domain from http://en.wikipedia.org/wiki/ANSI_escape_code

import eclipse formatting settings from ./buildSrc/eclipse/formatting/settings.xml

some eclipse plugins you might consider useful (not required for developing chalkbox):

eclipse-groovy plugin
  http://dist.springsource.org/release/GRECLIPSE/e3.7/

eclipse-gradle plugin
  http://kaczanowscy.pl/tomek/2010-03/gradle-ide-integration-eclipse-plugin

Developing chalkbox:

install subversion
checkout the code

see tool installation scripts (mainly for ubuntu) in install project:
./install/groovy/for/ubuntu.sh 
./install/gradle/for/ubuntu.sh
or install them manually

build your eclipse settings:
gradle eclipseSettings

after that just import the projects to eclipse

