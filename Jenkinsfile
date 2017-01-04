node {
    // Mark the code checkout 'stage'....
    stage 'Checkout'
    // Get some code from a GitHub repository
    checkout scm
    // Note: if this is copy and pasted into pipeline script, the following will work while the above handles branches and such
    // git url: 'https://github.com/saucelabs-sample-test-frameworks/Java-TestNG-Selenium.git'

    docker.image('maven:3.3.9-jdk-7').inside {
        stage 'Compile'
        sh "mvn compile"
        stage 'Test'
        sauce('saucelabs') {
            sauceconnect(useGeneratedTunnelIdentifier: true, verboseLogging: true) {
                sh "mvn test"
            }
        }
    }

    stage 'Collect Results'
    step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
    step([$class: 'SauceOnDemandTestPublisher'])
}
