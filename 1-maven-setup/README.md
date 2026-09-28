# Maneva Setup

Projeto Maven de demonstração configurado para Java 11 e para o framework JPacman.

## Requisitos

- Java 11
- Maven 3.9.11 ou superior

A versão mínima do Maven é validada pelo `maven-enforcer-plugin` durante a compilação.

## Dependência local

O projeto utiliza o JPacman Framework 8.1.1 a partir do arquivo local abaixo:

```text
libs/jpacman-framework-8.1.1.jar
```

Esse arquivo deve permanecer no diretório `libs` para que o Maven consiga resolver a dependência.

## Compilar

No diretório deste projeto, execute:

```bash
mvn compile
```

## Limpar artefatos gerados

```bash
mvn clean
```
