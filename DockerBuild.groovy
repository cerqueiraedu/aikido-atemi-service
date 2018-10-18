@Library('aikido-jenkins-scripts') _

def builderPodLabel = getBuilderLabel()
def appName = "aikido-atemi-service"
def image

podTemplate(label: builderPodLabel, yaml: getBuilderTemplate()) {
    node (builderPodLabel) {
        container('docker-helm') {
            stage('Fetching Code') { 
                checkout scm
            }
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
                def imageTag = tag;
                image = docker.image("ecerqueira/${appName}:${imageTag}")
                docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
                    image.push(imageVersion)
                    image.push("latest")
                }  
            }
        }
    }
}