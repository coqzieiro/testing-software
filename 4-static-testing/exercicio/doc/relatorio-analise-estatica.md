# Relatório de análise estática — JPacman

## Escopo

Este relatório registra a configuração das três ferramentas descritas em
`../Static testing.pdf`, a seleção de cinco achados do Checkstyle e cinco do PMD, as decisões
tomadas para cada grupo, uma correção adicional dos achados SpotBugs e os resultados após as
alterações.

## Configuração das ferramentas

### Checkstyle

O código-fonte usa indentação de quatro espaços. O módulo `Indentation` em
`rulesets/checkstyle-rules.xml` foi ajustado para refletir esse padrão:

| Propriedade | Valor |
| --- | ---: |
| `basicOffset` | 4 |
| `braceAdjustment` | 4 |
| `caseIndent` | 4 |
| `throwsIndent` | 8 |
| `lineWrappingIndentation` | 4 |
| `arrayInitIndent` | 4 |

### PMD

As configurações de execução e de geração do site aplicam os quatro conjuntos de regras indicados
no PDF: `pmd-bestpractices.xml`, `pmd-security.xml`, `pmd-performance.xml` e
`pmd-multithreading.xml`.

### SpotBugs

As configurações de execução e de geração do site usam `rulesets/spotbugs-rules.xml`, cujo filtro
limita os resultados à confiança definida no PDF.

## Achados selecionados e decisões

### Checkstyle — cinco avisos de documentação

Foram acrescentadas frases-resumo aos Javadocs de:

1. `Direction.getDeltaX`
2. `Direction.getDeltaY`
3. `AnimatedSprite.currentSprite`
4. `PacManSprites.getPacmanSprites`
5. `PacManSprites.getPacManDeathAnimation`

Os cinco avisos `SummaryJavadocCheck` eram problemas reais de documentação: os blocos começavam
diretamente com a tag `@return`, sem uma frase que resumisse o método. A alteração documenta a
intenção sem modificar o comportamento do jogo.

### PMD — cinco avisos `AvoidSynchronizedStatement`

As ocorrências selecionadas estavam nos métodos `Game.start`, `Game.stop`, `Level.move`,
`Level.start` e `Level.stop`. A regra recomenda trocar blocos `synchronized` por `ReentrantLock`
por causa do possível bloqueio de virtual threads.

Esses avisos foram classificados como falsos positivos para este projeto: o POM compila para Java
11, que não oferece virtual threads, e os blocos sincronizados protegem a coordenação do estado
do jogo. A troca sugerida não corrigiria um defeito observado. Por isso, a definição dessa regra
foi desativada em `rulesets/pmd-multithreading.xml`, com a justificativa junto à configuração.
As demais regras PMD permanecem ativas.

### SpotBugs — dois achados `DMI_RANDOM_USED_ONLY_ONCE`

Os achados em `Ghost.getInterval` e `Ghost.randomMove` eram problemas reais: cada chamada criava
um `Random`, gerava um único valor e descartava o objeto. A classe agora mantém uma instância
final de `Random` e a reutiliza nos dois métodos. Isso evita alocações repetidas sem mudar o uso
de aleatoriedade do jogo; os valores não são usados para fins de segurança.

## Resultados

| Ferramenta | Referência antes dos ajustes do exercício | Relatório final |
| --- | --- | --- |
| Checkstyle | 1.507 avisos, com predominância de incompatibilidades de indentação | 123 avisos; nenhum aviso de indentação |
| PMD | 28 violações após ativar os quatro conjuntos de regras | 23 violações; nenhuma ocorrência de `AvoidSynchronizedStatement` |
| SpotBugs | 19 achados antes do filtro de confiança; 2 com o filtro antes da correção | 0 achados após reutilizar `Random` |

A amostra exigida foi atendida com Checkstyle e PMD; os dois achados SpotBugs foram corrigidos
adicionalmente por serem problemas reais. Os demais avisos do Checkstyle e violações do PMD
permanecem no relatório para que possam ser avaliados separadamente, em vez de serem ocultados
sem análise.

## Verificação e reprodução

Execute os comandos na raiz deste projeto:

```bash
mvn site -DskipTests
mvn test
```

Na última execução, `mvn site -DskipTests` terminou com `BUILD SUCCESS` e gerou
`target/site/checkstyle.html`, `target/site/pmd.html` e `target/site/spotbugs.html`. O site também
registrou um `NoSuchMethodError` de `Sink.verbatim()` ao gerar relatórios auxiliares de
informações do projeto, licenças e CPD. Esse problema não impediu a geração dos três relatórios
de análise estática e não foi alterado por este exercício.

`mvn test` também terminou com `BUILD SUCCESS`. Como o projeto não inclui classes de teste em
`src/test/java`, nenhum teste unitário foi executado.
