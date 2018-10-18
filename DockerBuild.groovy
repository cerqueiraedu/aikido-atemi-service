@Library('aikido-jenkins-scripts') _

def builderPodLabel = getBuilderLabel()
def appName = "aikido-atemi-service"
def image
def imageTag

podTemplate(label: builderPodLabel, yaml: getBuilderTemplate()) {
    node (builderPodLabel) {
        stage('Fetching Code') { 
            def commitHash = checkout(scm).GIT_COMMIT
            imageTag = sh (
                script: "git tag --contains ${commitHash}",
                returnStdout: true
            ).trim()
        }
        /*container('docker-helm') {
            stage('Docker Build') {
                image = docker.build("ecerqueira/${appName}", ".")
            }
            stage('Running Tests') {
                def testResultsPath = "${env.WORKSPACE}/build"
                runNodeTests(appName, testResultsPath)
            }
            stage('Collect Test Results') {
                junit "**/build/*.xml"
            }
            stage('Docker Push') {
                docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
                    image.push("${imageTag}")
                    image.push("latest")
                }  
            }
        }*/
    }
}