# SEPost

### Файл war находится в корневой папке

## Description
SEPost - приложение, в котором можно  регистрировать почтовые отправления — письма, посылки — их передвижение между почтовыми отделениями.
Реализована возможность регистрации, обновления статуса почтового отправления(прибытие, убытие, получение адресатом), а также возможность получения информации и всей истории передвижения конкретного почтового отправления.

## Tech Stack 🔧
[![Java](https://img.shields.io/badge/Java%2017-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/) [![Spring](https://img.shields.io/badge/Spring%20Boot%203.1.2-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-framework) [![JPA](https://img.shields.io/badge/JPA-FF5733?style=for-the-badge&logo=JUnit&logoColor=white)](https://docs.oracle.com/javase/tutorial/jdbc/overview/index.html) [![PostgreSQL Database](https://img.shields.io/badge/PostgreSQL-0000FF?style=for-the-badge&logo=H2&logoColor=white)](https://www.postgresql.org/) [![JUnit](https://img.shields.io/badge/JUnit%205-9F2B68?style=for-the-badge&logo=JUnit&logoColor=white)](https://junit.org/junit5/docs/current/user-guide/)
[![Maven](https://img.shields.io/badge/Maven-00008B?style=for-the-badge&logo=Maven&logoColor=white)](https://maven.apache.org/) [![Docker](https://img.shields.io/badge/Docker-00008B?style=for-the-badge&logo=Docker&logoColor=white)](https://www.docker.com/) [![Swagger](https://img.shields.io/badge/Swagger-006400?style=for-the-badge&logo=Maven&logoColor=white)](https://swagger.io/)

## How to set up the project ▶

1) Склонируйте репозиторий
```
git clone https://github.com/Antroverden/SEPost.git
```
2) Для создания war архива введите в консоли(будет сгенерирован в папку target)
```
mvn clean package
```
3) Убедитесь, что у вас запущен Docker и запустите проект в Intellij IDEA

После запуска проекта в Intellij IDEA примеры HTTP-запросов к контроллерам можно увидеть по ссылке:
```
http://localhost:8080/swagger-ui/index.html
```

Скриншот тестового покрытия:
![TestCoverage.png](TestCoverage.png)


Скриншоты описания API — структура запросов и ответов, список допустимых операций(более подробно по адресу http://localhost:8080/swagger-ui/index.html при запущенном приложении в Intellij IDEA):
![Swagger API.png](Swagger%20API.png)
