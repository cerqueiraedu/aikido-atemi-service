def trustedText = readTrusted 'PodBuilder.yaml'
def image

podTemplate(label: 'atemi-service-builder', yaml: trustedText) {
    node ('atemi-service-builder') {
        stage('Cloning Repository...') { 
            container('jnlp') {
                checkout scm
            }
        }
        container('docker-helm') {
            stage('Docker Build') {
                image = docker.build("ecerqueira/atemi-service:3.1.0", ".")
            }
            stage('Running Tests') {
                try {
                    sh 'docker volume create test-results'
                    sh 'docker run --name atemi-test -v test-results:/build/test-results ecerqueira/atemi-service:3.1.0 npm run test-jenkins'
                    sh 'docker cp atemi-test:/build/test-results.xml test-results.xml'
                }
                catch(ex){
                    throw ex
                }
                finally {
                    sh 'docker rm atemi-test'
                }
            }
            stage('Results') {
                junit '*.xml'
            }
            stage('Docker Push') {
                docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
                    image.push()
                    image.push("latest")
                }  
            }
        }
        stage('Helm Deploying') {
            container('docker-helm'){
              //  sh 'helm init --client-only --skip-refresh'
              //  sh 'helm repo add aikido-charts https://cerqueiraedu.github.io/aikido-app-charts/charts'
              //  sh 'helm upgrade --install --namespace default --wait alpha-production-atemi-service aikido-charts/atemi-service'
            }
        }   
    }
}