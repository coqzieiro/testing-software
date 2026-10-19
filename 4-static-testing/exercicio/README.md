# Exercício de teste estático — JPacman

Este projeto contém uma versão do JPacman usada para praticar análise estática em Java com
Checkstyle, PMD e SpotBugs. A configuração e as decisões tomadas para o exercício do PDF estão
registradas em [`doc/relatorio-analise-estatica.md`](doc/relatorio-analise-estatica.md).

## Pré-requisitos

- JDK 11
- Maven 3.9.11 ou superior

## Gerar os relatórios

Execute a partir deste diretório:

```bash
mvn site -DskipTests
```

Os relatórios HTML são gerados em `target/site/`:

- `target/site/checkstyle.html`
- `target/site/pmd.html`
- `target/site/spotbugs.html`

Para compilar e executar os testes automatizados disponíveis:

```bash
mvn test
```

O projeto fornecido não contém classes de teste em `src/test/java`; portanto, o comando valida o
ciclo Maven e a compilação, mas não executa testes unitários.

## Configuração aplicada

- O Checkstyle usa indentação de quatro espaços, conforme o código-fonte do projeto.
- O PMD aplica as regras de boas práticas, segurança, desempenho e concorrência indicadas no PDF.
- O SpotBugs usa `rulesets/spotbugs-rules.xml` para filtrar os relatórios pela confiança definida
  no exercício.

Consulte o relatório documentado para os achados selecionados, as correções e os resultados da
última execução.
