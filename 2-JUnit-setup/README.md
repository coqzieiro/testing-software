# Introdução ao JUnit

Projeto Maven de exemplo para praticar testes unitários com JUnit 5.

## Requisitos

- Java 11
- Maven 3.9 ou superior

## Estrutura

- `src/main/java`: código da aplicação
- `src/test/java`: testes automatizados
- `src/main/java/pt/up/fe/Calculator.java`: classe com a operação de multiplicação
- `src/test/java/pt/up/fe/CalculatorTest.java`: teste unitário de `Calculator`

## Dependências

O projeto usa `junit-jupiter` na versão 5.13.4, disponível apenas no escopo de teste.

## Executar os testes

No diretório deste projeto, execute:

```bash
mvn test
```

Para apenas compilar as fontes e os testes:

```bash
mvn test-compile
```
