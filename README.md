# REST services using Spring

A aplicação foi criada com Spring Boot e Java 8 portanto basta baixar o projeto em sua máquina com Java 8 instalado.

Para rodar todos os testes (Unit Tests e System Tests), vá ao *terminal* e na pasta raiz do projeto executar o seguinte comando:

>mvn test

Para executar a aplicação (realizar o build e em seguida o deploy), basta ir ao *terminal* e na pasta raiz do projeto executar o seguinte comando:

>mvn spring-boot:run

O deploy será feito automaticamente no servidor de aplicação embarcado do Spring Boot.

A aplicação inicia na porta 8080 da sua máquina, portanto utilizar *http://localhost:8080* nas operações e garantir que não tenha nada rodando nessa porta ;-) .
 
Para testes manuais, favor utilizar alguma aplicação como *curl* ou *ARC* (Advanced Rest Client). 

Usar os inputs como descritos na documentação do desafio:

#### 1. Criar imóveis em Spotippos
Request:
```
POST /properties
```

Body:
```json
{
  "x": 222,
  "y": 444,
  "title": "Imóvel código 1, com 5 quartos e 4 banheiros",
  "price": 1250000,
  "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
  "beds": 4,
  "baths": 3,
  "squareMeters": 210
}
```

Response (atentar para o header quando executar o teste =) :
```json
{
    "id": 8001,
    "title": "Imóvel código 1, com 5 quartos e 4 banheiros",
    "price": 1250000,
    "x": 222,
    "y": 444,
    "beds": 4,
    "baths": 3,
    "squareMeters": 210,
    "provinces": [
      "Scavy"
    ],
}
```
#### 2. Mostrar um imóvel específico em Spotippos
Request:
```
  GET /properties/{id}
```
Response:

```json
{
    "id": 8001,
    "title": "Imóvel código 1, com 5 quartos e 4 banheiros",
    "price": 1250000,
    "x": 222,
    "y": 444,
    "beds": 4,
    "baths": 3,
    "squareMeters": 210,
    "provinces": [
      "Scavy"
    ],
}
```
#### 3. Buscar imóveis em Spotippos

```
  GET /properties?ax={integer}&ay={integer}&bx={integer}&by={integer}
```

Response:

```json
{
    "foundProperties": 5,
    "properties": [
      {
        "id": 1339,
        "title": "Imóvel código 1339, com 5 quartos e 4 banheiros.",
        "price": 1523000,
        "x": 70,
        "y": 95,
        "beds": 5,
        "baths": 4,
        "squareMeters": 152,
        "provinces": [
          "Scavy"
        ],
      },
      {
        "id": 1541,
        "title": "Imóvel código 1541, com 2 quartos e 1 banheiros.",
        "price": 408000,
        "x": 64,
        "y": 90,
        "beds": 2,
        "baths": 1,
        "squareMeters": 40,
        "provinces": [
          "Scavy"
        ],
      },
        {"..."},
        {"..."},
        {"..."}
      ]
}
```