README — cloudAzure_webapp (Java 8 + Tomcat + Azure)

Projeto Java simples que empacota um WAR com um Servlet que lê um arquivo CSV e exibe seu conteúdo via URL /csv.
O deploy é feito automaticamente no Azure Web App (Linux + Tomcat) usando GitHub Actions.

1. Estrutura do Projeto
pom.xml
src/main/java/com/exemplo/DisplayCSVServlet.java
src/main/webapp/WEB-INF/web.xml
src/main/webapp/csv/dados.csv


DisplayCSVServlet.java → lê e imprime o conteúdo do CSV

web.xml → mapeia /csv para o servlet

dados.csv → arquivo lido pelo servlet

2. Build local
mvn clean package


Gera o arquivo:

target/cloudAzure_webapp.war

3. Configuração do Azure Web App

É obrigatório usar:

Sistema Operacional: Linux

Java: JDK 8

Servidor Web: Tomcat 9.0 (ou 8.5)

Criação rápida via Azure CLI:

az group create --name rg-gs-karine --location eastus

az appservice plan create --name plan-gs-karine --resource-group rg-gs-karine --sku B1 --is-linux

az webapp create \
  --resource-group rg-gs-karine \
  --plan plan-gs-karine \
  --name webapp-gs-cloudcomputing2 \
  --runtime "TOMCAT|9.0-jre8"

4. Configurar Deploy via GitHub Actions
4.1 Criar Secret no GitHub

No repositório → Settings → Secrets → Actions → New Secret
Nome: AZURE_WEBAPP_PUBLISH_PROFILE
Valor: conteúdo do arquivo .PublishSettings baixado do Azure.

4.2 Criar workflow

Arquivo:

.github/workflows/main_webapp-gs-cloudcomputing.yml


Conteúdo:

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
