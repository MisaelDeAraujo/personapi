# Person API
Java RESTful API para cadastro de Pessoas e Endereços

```mermaid
classDiagram
    class Address {
        - id: Integer
        - publicPlace: String
        - zipCode: String
        - number: String
        - city: String
        - state: String
        - addressType: AddressType
    }

    class Person {
        - id: Integer
        - completeName: String
        - birthday: String
        - addresses: List
    }

    class AddressType {
        - mainAddress: String
    }

    class AddressRepository
    class PersonRepository
    class ZipCodeValidatorService
    class AddressService
    class PersonService
    class AddressController
    class PersonController

    Address --> AddressType
    Address --> AddressRepository
    Person --> PersonRepository
    AddressService --> AddressRepository
    AddressService --> PersonRepository
    AddressService --> ZipCodeValidatorService
    PersonService --> PersonRepository
    PersonService --> AddressRepository
    PersonService --> ZipCodeValidatorService
    AddressController --> AddressService
    PersonController --> PersonService

    class AddressDto
    class PersonDto

    AddressController --> AddressDto
    PersonController --> PersonDto

```

<hr>

**Tecnologias usadas**

* [**`Java 17`**](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [**`Spring Boot 3`**](https://spring.io/projects/spring-boot)
* [**`Spring Data JPA`**](https://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html)
* [**`RestTemplate`**](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html)
* [**`H2 Database`**](https://www.h2database.com/html/main.html)
* [**`PostgreSQL`**](https://www.postgresql.org/)
* [**`Mockito `**](https://site.mockito.org/)
* [**`Flyway `**](https://flywaydb.org/)
* [**`Lombok `**](https://projectlombok.org/)
* [**`Swagger `**](https://swagger.io/)
* [**`Docker `**](https://www.docker.com/)

<hr>

### Use comando maven `\mvn clean install` dentro do diretorio do projeto para buildar o .jar, depois inice o docker-compose com comando `docker-compose up`
[**`Swagger UI`**](http://localhost:8080/swagger-ui/index.html)
