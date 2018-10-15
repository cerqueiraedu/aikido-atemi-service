@Library('aikido-jenkins-scripts') _

def builderPodLabel = getBuilderLabel()
def imageVersion = getImageVersion("1.0")
def appName = "aikido-atemi-service"
def image

podTemplate(label: builderPodLabel, yaml: getBuilderTemplate()) {
    node (builderPodLabel) {
        container('docker-helm') {
            stage('Fetching Code') { 
                checkout scm
            }
            stage('Docker Build') {
                image = docker.build("ecerqueira/${appName}:${imageVersion}", ".")
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
                    image.push(imageVersion)
                    image.push("latest")
                }  
            }
            stage('Helm Deploying') {
                runHelmDeployment("aikido-app-charts", "default", "alpha-production", appName)
            }
        }
    }
}