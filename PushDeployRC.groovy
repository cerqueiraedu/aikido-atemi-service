@Library('aikido-jenkins-scripts') _

def builderPodLabel = getBuilderLabel()
def imageVersion = getImageVersion("1.0")
def appName = "aikido-atemi-service"
def image

podTemplate(label: builderPodLabel, yaml: getBuilderTemplate()) {
    node (builderPodLabel) {
        container('docker-helm') {
            stage('Docker Push') {
                image = docker.image("ecerqueira/${appName}")
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