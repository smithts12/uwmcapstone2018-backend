# UWM Capstone Project - RESTful Service

&nbsp;
----

The provided codebase is a [spring-boot](https://projects.spring.io/spring-boot/) based project that requires [git](https://git-scm.com/downloads), 
[java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) _(or greater)_ and 
[apache maven](https://maven.apache.org/download.cgi) be installed on your machine.

##### Concatenation
_The functionality to take two separate values and combines them together._
* Java 
    * [Implementation](https://gitlab.uwm-nm-te-capstone.com/uwm-capstone-2018/backend/blob/master/src/main/java/edu/uwm/capstone/util/Concatenation.java)
        * [Unit Test](https://gitlab.uwm-nm-te-capstone.com/uwm-capstone-2018/backend/blob/master/src/test/java/edu/uwm/capstone/util/ConcatenationUnitTest.java)
    * [Rest Controller](https://gitlab.uwm-nm-te-capstone.com/uwm-capstone-2018/backend/blob/master/src/main/java/edu/uwm/capstone/controller/ConcatenationRestController.java) 
        * [Unit Test](https://gitlab.uwm-nm-te-capstone.com/uwm-capstone-2018/backend/blob/master/src/test/java/edu/uwm/capstone/controller/ConcatenationRestControllerUnitTest.java)
        * [Component Test](https://gitlab.uwm-nm-te-capstone.com/uwm-capstone-2018/backend/blob/master/src/test-component/java/edu/uwm/capstone/controller/ConcatenationRestControllerComponentTest.java)
        
##### Palindrome
_Determine whether or not the provided value is a word, phrase, or sequence that reads the same backward as forward._
* Java 
    * [Implementation](https://gitlab.uwm-nm-te-capstone.com/uwm-capstone-2018/backend/blob/master/src/main/java/edu/uwm/capstone/util/Palindrome.java)
        * [Unit Test](https://gitlab.uwm-nm-te-capstone.com/uwm-capstone-2018/backend/blob/master/src/test/java/edu/uwm/capstone/util/PalindromeUnitTest.java)
    * [Rest Controller](https://gitlab.uwm-nm-te-capstone.com/uwm-capstone-2018/backend/blob/master/src/main/java/edu/uwm/capstone/controller/PalindromeRestController.java)
        * [Unit Test](https://gitlab.uwm-nm-te-capstone.com/uwm-capstone-2018/backend/blob/master/src/test/java/edu/uwm/capstone/controller/PalindromeRestControllerUnitTest.java)
        * [Component Test](https://gitlab.uwm-nm-te-capstone.com/uwm-capstone-2018/backend/blob/master/src/test-component/java/edu/uwm/capstone/controller/PalindromeRestControllerComponentTest.java)
        
&nbsp;
---

### Building this Project

To perform a build and execute all unit tests:
```
mvn clean install
```

To execute all component tests:
```
mvn -P test-component test
```

&nbsp;
---

### Using this Project

To run the RESTful services:
```
mvn spring-boot:run
```

or you can execute the JAR that is created by the install command above via:
```
java -jar target/*.jar
```

Once the application is running locally Swagger based REST documentation and testing will be available at:
- [http://localhost:8333/backend/swaggerui](http://localhost:8333/backend/swaggerui)

and the Concatenation and Palindrome REST endpoints will also be accessible.

Concatenation example:
- [http://localhost:8333/backend/concatenate/value1/VALUE2](http://localhost:8333/backend/concatenate/value1/VALUE2)

Palindrome example:
- [http://localhost:8333/backend/palindrome/radar](http://localhost:8333/backend/palindrome/radar)
- [http://localhost:8333/backend/palindrome/notapalindrome](http://localhost:8333/backend/palindrome/notapalindrome)


### Use Docker for project 

`Dockerfile` available at root of project could be used to create a docker image which could be shipped, shared. In order to build this container, run following commands

```
docker build -t backend
```
 and then this docker image could be used to run application on your machine                                                                                                                            

```
docker run -p port-to-be-run:port-of-application backend
```

here application runs on 8333. You can also pull docker image from registry and run it locally, in order to pull centralized image from registry use following:

```
docker pull registry.uwm-nm-te-capstone.com:8083/backend

```
then use above listed run command to run this docker image on your machine. 

