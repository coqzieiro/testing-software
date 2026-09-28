# Mockito Setup

Material prático para aprender testes unitários com Mockito e JUnit 5.

## Requisitos

- Java 11
- Maven 3.9.9 ou superior

## Módulos

### `exemplo`

Módulo com exemplos executáveis dos recursos principais do Mockito:

- criação de mocks com `@Mock`;
- injeção de dependências com `@InjectMocks`;
- configuração de retornos com `when(...).thenReturn(...)`;
- configuração de exceções;
- verificação de interações com `verify(...)`;
- uso de spies com `@Spy` e `doReturn(...)`;
- simulação de métodos estáticos com `mockStatic(...)`.

Para executar os exemplos:

```bash
cd exemplo
mvn test
```

### `exercicio`

Módulo de exercícios que aplica mocks, spies, injeção de dependências e reflexão.

Para executar os exercícios:

```bash
cd exercicio
mvn test
```

O Surefire também executa a classe `TestingSpy`, cujo nome foi mantido conforme o enunciado.

## Dependências de teste

Os dois módulos usam JUnit 5.13.4 e Mockito 5.20.0. A integração com JUnit é fornecida por `mockito-junit-jupiter`.
