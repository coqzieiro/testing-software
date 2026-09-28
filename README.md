# Teste de Software

Repositório com exemplos e exercícios práticos sobre teste, verificação e validação de software em Java. O material apresenta Maven, JUnit 5 e Mockito em uma sequência progressiva.

## Pré-requisitos

- Java 11
- Maven 3.9 ou superior

Verifique as ferramentas instaladas com:

```bash
java -version
mvn -version
```

## Roteiro de Estudo

| Etapa | Conteúdo | Objetivo |
| --- | --- | --- |
| [1 - Maven Setup](1-maven-setup/README.md) | Configuração de um projeto Maven | Compilar um projeto Java e entender o gerenciamento de dependências. |
| [2 - JUnit Setup](2-JUnit-setup/README.md) | Testes unitários com JUnit 5 | Criar e executar testes automatizados. |
| [3 - Mockito Setup](3-mockito-setup/README.md) | Mocks, spies e verificação de interações | Testar classes que dependem de outros componentes. |

Recomenda-se seguir as etapas na ordem apresentada, pois cada uma parte dos conceitos anteriores.

## Como Executar

Cada etapa possui seu próprio README com instruções e comandos específicos. Entre no diretório desejado e consulte a documentação correspondente. Por exemplo:

```bash
cd 2-JUnit-setup
cat README.md
```

Os módulos que possuem testes podem ser executados com:

```bash
mvn test
```

Execute o comando dentro do diretório do módulo indicado na documentação.

## Organização

```text
testing-software/
├── 1-maven-setup/   # Configuração e compilação com Maven
├── 2-JUnit-setup/   # Fundamentos de testes unitários com JUnit 5
├── 3-mockito-setup/ # Isolamento de dependências com Mockito
└── README.md
```
