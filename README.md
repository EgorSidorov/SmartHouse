# Virtual remote control for smart home.
## Management using the CLI interface. 
## The service is implemented using a Spring Boot application.
## Build

### Service
Add resources file in path: src/test/resources by pattern: <br />
> spring.jpa.hibernate.ddl-auto = update <br />
> spring.datasource.driver-class-name=org.h2.Driver <br />
> spring.jpa.database-platform = org.hibernate.dialect.H2Dialect <br />
> spring.jpa.generate-ddl=true <br />
> spring.datasource.url=jdbc:h2:mem:db;INIT=CREATE SCHEMA IF NOT EXISTS SMART_HOUSE;DB_CLOSE_DELAY=-1 <br />
> spring.datasource.username=LOCAL_H2_LOGIN <br />
> spring.datasource.password=LOCAL_H2_PASSWORD <br />
> cache.ttl=30 <br />

Add resources file in path: src/main/resources by pattern: <br />
> spring.jpa.hibernate.ddl-auto = create <br />
> #for MySQL: org.hibernate.dialect.MySQL5Dialect <br />
> spring.jpa.database-platform = YOUR_DATABASE_DIALECT <br />
> spring.jpa.generate-ddl=true <br />
> #for MySQL at localhost: jdbc:mysql://localhost:3306/smartHouse?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow) <br />
> spring.datasource.url=YOUR_URL_CONNECTION_STRING <br />
> spring.datasource.username=DB_LOGIN <br />
> spring.datasource.password=DB_PASSWORD <br />
> #in minutes <br />
> cache.ttl=30 <br />

For spring application build:https://www.baeldung.com/spring-boot-run-maven-vs-executable-jar <br />
In maven: run mvn spring-boot:run <br />

### CLI 
For CLI Application run maven goal mvn assembly:single, and run generated jar with first argument of your server address,  <br />
for example: http://localhost:8080. <br />

## Issue:
HIbernate with some databases incorrectly determines the storage type of the UUID field. The program defines for MySQL: BINARY (16).

## Tests:
Tests with in memory database h2 are implemented for the service with Spring. <br />
For the cli, sequential tests are implemented with scripts for adding profiles, houses, buttons, devices, binding buttons and devices, calling buttons, saving and deleting profiles.<br />

## List CLI command: <br />
-      --callButton=<callButton> <br />
                             Call Button <br />
-      --callButtonDevice=<callButtonDevice>[,<callButtonDevice>...] <br />
                             Call Button for Device without mapping <br />
-      --createButton=<createButton>[,<createButton>...] <br />
                             Create new button <br />
-      --createDevice=<createDevice> <br />
                             Create new device <br />
-      --createHouse=<createHouse> <br />
                             Create new house <br />
-      --createProfile=<createProfile> <br />
                             Create new profile <br />
-      --deleteButton=<deleteButton> <br />
                             Delete button <br />
-      --deleteDevice=<deleteDevice> <br />
                             Delete device <br />
-      --deleteHouse=<deleteHouse> <br />
                             Delete house <br />
-      --deleteProfile=<deleteProfile> <br />
                             Delete Profile <br />
-      --help                 Show this help message and exit. <br />
-      --mapButtonToDevice=<mapButtonToDevice>[,<mapButtonToDevice>...] <br />
                             Connect button and device <br />
-      --saveProfile          Save profile <br />
-      --setHouse=<setHouse>  Set house <br />
-      --setProfile=<setProfile> <br />
                             Set profile <br />
-      --showCurrentProfile   Show current profile. <br />
-      --undoState            Undo state to last. For house state profile. For <br />
                               profile set undefined. <br />

Patterns:

For cli, the Commander pattern of adding commands is used. Also, the State pattern is implemented to control the allowed states and the ability to call buttons in current state. <br />
For the web service, the ORM model of interaction with the database is used together with hibernate, IOC Spring patterns, the separation of layers Model, Controller, Service, Repository. <br />
