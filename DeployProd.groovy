@Library('aikido-jenkins-scripts') _

def builderPodLabel = getBuilderLabel()
def appName = "aikido-atemi-service"

podTemplate(label: builderPodLabel, yaml: getBuilderTemplate()) {
    node (builderPodLabel) {
        container('docker-helm') {
            stage('Helm Deploying') {
                runHelmDeployment("aikido-app-charts", "default", "release", appName)
            }
        }
    }
}