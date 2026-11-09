# Exercício: Property-based testing

Implementação dos exercícios do material de jqwik:

- `PassingGrade`: propriedades para aprovação, reprovação, limites e entradas inválidas.
- `Unique`: valores únicos em ordem decrescente; o gerador de teste sempre inclui duplicatas e há casos explícitos para array vazio e `null`.
- `IntArraySearch`: propriedade para busca a partir do índice informado, casos parametrizados do enunciado e casos explícitos para array vazio e limites do índice.
- `MyStringStack`: verifica a pilha comparando sequências geradas de ações com um modelo independente `Deque`.
- A etapa 1.4 converte os testes de `squareAt`, `createBoard`, `createLevel`, `makeGhostSquare` e `ImageSprite.draw` em propriedades jqwik na etapa [5-blackbox](../../5-blackbox/exercicio/src/test/java).

## Executar

Requisitos: JDK 11 e Maven.

```bash
mvn test
```

Para executar também as propriedades do Pacman:

```bash
cd ../../5-blackbox/exercicio
mvn test
```

As propriedades não injetam bugs na implementação. A propriedade da pilha inclui
sempre uma sequência de pelo menos quatro inserções seguida de `clear`, portanto
falha se `clear` for alterado para limpar somente pilhas com menos de quatro
elementos. De forma semelhante, a propriedade de aprovação cobre o limite mínimo,
detectando a troca do limiar de `9.5` para `7.5`.

O caso manual de `indexOf` cobre array nulo, valor encontrado (incluindo repetido),
valor não encontrado e índice negativo/maior que o array. Ele não cobre array vazio
nem índice exatamente igual ao tamanho; esses limites são cobertos separadamente.
