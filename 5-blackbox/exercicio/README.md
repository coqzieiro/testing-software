# JPacman-Framework

[Pacman](https://en.wikipedia.org/wiki/Pac-Man)-like game used for teaching software testing.
It exposes students to the use of [Apache Maven](https://maven.apache.org/index.html), [JUnit](https://junit.org/junit5/), and [Mockito](https://site.mockito.org/).

## Requisitos

- JDK 11
- Apache Maven 3.9.11

## Executar o jogo

A partir desta pasta (`5-blackbox/exercicio`):

```bash
mvn package
java -jar target/jpacman-framework-8.1.1.jar
```

Na janela que abrir, clique em **Start** e use as **setas do teclado** para mover o Pacman.

Se o `mvn package` falhar por falta de internet (plugins não baixados), execute diretamente a partir das classes compiladas:

```bash
mvn compile
java -cp "target/classes:$HOME/.m2/repository/com/google/guava/guava/33.5.0-jre/guava-33.5.0-jre.jar:$HOME/.m2/repository/com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3.jar" nl.tudelft.jpacman.Launcher
```

## Executar os testes

```bash
mvn test
```

Os testes de caixa-preta (particionamento em classes de equivalência e análise de valores-limite) estão em `src/test/java`; a análise correspondente está em [doc/particoes-e-valores-limite.txt](doc/particoes-e-valores-limite.txt).
