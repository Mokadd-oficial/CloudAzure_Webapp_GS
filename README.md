Java 8 • Tomcat • Azure Web App • GitHub Actions

👨‍💻 Integrantes do Grupo:

Joyce Melo De Souza — RM558294

Tiago Aiala De Lima — RM558069

Karine Maria Lopes Pereira Fernandes — RM558823

📌 Descrição do Projeto

Este projeto é uma aplicação Java simples, empacotada como WAR, contendo um Servlet que lê um arquivo CSV e exibe seu conteúdo na rota:

👉 /csv

O deploy é realizado automaticamente no Azure Web App (Linux + Tomcat) usando GitHub Actions.

📁 Estrutura do Projeto
cloudAzure_webapp/
├─ pom.xml
├─ src/main/java/com/exemplo/DisplayCSVServlet.java
├─ src/main/webapp/WEB-INF/web.xml
└─ src/main/webapp/csv/dados.csv


📌 Funções principais:

DisplayCSVServlet → lê e imprime o CSV

web.xml → mapeia /csv para o servlet

dados.csv → conteúdo exibido na resposta

🛠️ Build Local (Maven)
mvn clean package


Gera o arquivo:
📦 target/cloudAzure_webapp.war

☁️ Configuração do Azure Web App

Para funcionar corretamente, o WebApp deve ser criado com:

🐧 SO: Linux

☕ Java: JDK 8

🐱‍👤 Servidor: Tomcat 9.0

Criando via Azure CLI:
az group create --name rg-gs-karine --location eastus

az appservice plan create \
  --name plan-gs-karine \
  --resource-group rg-gs-karine \
  --sku B1 \
  --is-linux

az webapp create \
  --resource-group rg-gs-karine \
  --plan plan-gs-karine \
  --name webapp-gs-cloudcomputing2 \
  --runtime "TOMCAT|9.0-jre8"

🔄 Deploy Automático — GitHub Actions
1️⃣ Criar Secret no GitHub

GitHub → Settings → Secrets → Actions → New Secret

Name: AZURE_WEBAPP_PUBLISH_PROFILE

Value: conteúdo do arquivo .PublishSettings baixado do Azure

2️⃣ Criar workflow do Actions

📄 Arquivo:

.github/workflows/main_webapp-gs-cloudcomputing.yml


💡 Conteúdo:

name: Build and deploy Java app to Azure Web App

on:
  push:
    branches: [ "main" ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v4

    - name: Setup Java 8
      uses: actions/setup-java@v4
      with:
        java-version: '8'
        distribution: temurin

    - name: Build with Maven
      run: mvn clean package

    - name: Deploy to Azure
      uses: azure/webapps-deploy@v2
      with:
        app-name: "webapp-gs-cloudcomputing2"
        publish-profile: ${{ secrets.AZURE_WEBAPP_PUBLISH_PROFILE }}
        package: target/*.war

🔍 Testando a Aplicação

Acesse sua aplicação:

https://NOME-DO-WEBAPP.azurewebsites.net/csv


Se tudo estiver correto → o conteúdo do CSV será exibido.

🎓 Conclusão

Este projeto demonstra:
✨ Deploy automatizado com CI/CD
✨ Uso de Java 8 + Tomcat no Azure
✨ Leitura de arquivos CSV via Servlet
✨ Infraestrutura criada via Azure CLI
