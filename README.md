# Descrição
Pagamento e antecipação de débitos relacionados
a um cliente.

# Build
Para build do projeto são nacessárias as seguintes ferramentas:

* java 8
* mvn > 3.3.9

Para execução da build deve-se utilizar o comando abaixo:

```
mvn clean install
```

Após a realização da build será produzido um .jar que pode ser encontrado na pasta /target.

Para executar a aplicação acessar pasta target: cd/target e executar o comando abaixo:

```
java -jar apix2019-microservice-payment-java-1.0.jar --PORT=8181 --DATABASE_URL=jdbc:mysql://localhost:3306/apix2019 --DATABASE_USER=root --DATABASE_PASSWORD=apix2019 --REGISTER_ENDPOINT=http://localhost:4242/register/v1/registrations --TWILIO_ACCOUNT_SID=testeaccount --TWILIO_ACCOUNT_TOKEN=authToken --TWILIO_ORIGIN_PHONE=originPhone
```

Variaveis de ambiente

PORT= Porta em que o microserviço vai rodar. Ex: 8181

DATABASE_URL= Endereço do banco de dados mysql. Ex: jdbc:mysql://localhost:3306/apix2019

DATABASE_USER= Usuário de acesso ao banco de dados. Ex: apix2019

DATABASE_PASSWORD= Senha de acesso ao banco de dados. Ex: apix2019

REGISTER_ENDPOINT= Endereço para acessar ms-register e consultar score. Ex: http://localhost:4242/register/v1/registrations

TWILIO_ACCOUNT_SID= Identificador da conta cadastrada no Twilo

TWILIO_ACCOUNT_TOKEN= Token da conta cadastrada no Twilo

TWILIO_ORIGIN_PHONE=Número origem disponibilizado pelo Twilo (Trial number)

# Collection Postman
https://www.getpostman.com/collections/1a0bc2e1c8903b5916c0
