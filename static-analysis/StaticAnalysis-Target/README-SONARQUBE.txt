What do we need to use SonarQube?
-------------------------------------------------------------------------------

i) Download and install SonarQube:
    https://www.sonarqube.org/downloads/
    => install/sonarqube-x.y/

ii) Start SonarQube
    $ cd install/sonarqube-6.6/
    $ bin/linux-x86-64/sonar.sh start

    URL: localhost:9000
        admin/admin

iii) Download and install Sonar Scanner
    https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner
    => install/sonar-scanner-x.y.z-linux

    export SONAR_SCANNER=/home/student/install/sonar-scanner-3.0.3.778-linux
    export PATH=$SONAR_SCANNER/bin:$PATH


iv) Add sonar-project.properties file to the target project

    $ cd Static-Analysis-Target

    $ vi sonar-project.properties

    # must be unique in a given SonarQube instance
    sonar.projectKey=StaticAnalysis-Target
    # this is the name and version displayed in the SonarQube UI.
    sonar.projectName=StaticAnalysis-Target
    sonar.projectVersion=1.0

    # Path is relative to the sonar-project.properties file. Replace "\" by "/" on Windows.
    # This property is optional if sonar.modules is set.
    sonar.sources=.

    sonar.java.binaries=target/classes

    # Encoding of the source code. Default is default system encoding
    #sonar.sourceEncoding=UTF-8

    $ sonar-scanner

