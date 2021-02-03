# Best Practices 

## Executing tests

1. Run full suite
```java
cd best-practice
mvn test
```
2. Run visual tests
```java
cd best-practice
mvn clean test -Dtest=Visual*
```
3. Run desktop tests
```java
cd best-practice
mvn clean test -Dtest=Desktop*
```
