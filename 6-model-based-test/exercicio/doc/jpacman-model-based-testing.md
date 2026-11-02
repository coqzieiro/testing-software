# JPacman model-based testing

## 1. State model

This model covers the lifecycle controlled by `Game` and its `Level`. It uses
the public `Game.isInProgress()` observation together with the level's player
and pellet conditions.

| State | Observable condition |
|---|---|
| Ready | Not in progress; at least one player is alive and pellets remain; game has not been started yet. |
| Running | `isInProgress()` is true. |
| Paused | Not in progress; at least one player is alive and pellets remain; game was stopped after starting. |
| Won | No pellets remain. `Game.levelWon()` stops the game. |
| Lost | No player is alive. `Game.levelLost()` stops the game. |

Ready and Paused have the same observable data in the current API. The
difference is historical (whether `start()` has previously succeeded), and
the API does not expose a separate paused flag. Both can be resumed by
`start()` while the player is alive and pellets remain. Won and Lost are
effectively terminal: `Game.start()` does not start a level without pellets
or a living player.

```text
 Ready --start [alive, pellets > 0]--> Running --stop--> Paused
                                         ^                 |
                                         |------ start ----|
                                         |
                              +----------+----------+
                              |                     |
                         levelWon               levelLost
                              v                     v
                            Won                   Lost

 Running --start--> Running (idempotent)
 Running --move--> Running
 Paused --move--> Paused (movement ignored)
 Won --start--> Won (rejected: no pellets)
 Lost --start--> Lost (rejected: no living player)
```

## 2. Transition tree

The tree is bounded to one lifecycle pass; otherwise the Ready/Paused/Running
cycle can repeat indefinitely.

```text
Ready
`-- start [alive and pellets remain] -> Running
    |-- start -> Running
    |-- move -> Running
    |-- stop -> Paused
    |   |-- start [alive and pellets remain] -> Running
    |   `-- move -> Paused (ignored)
    |-- levelWon -> Won
    |   `-- start -> Won (rejected)
    `-- levelLost -> Lost
        `-- start -> Lost (rejected)
```

## 3. Transition table

| Current state | Event and guard | Next state | Expected behavior |
|---|---|---|---|
| Ready | `start`, player alive and pellets remain | Running | Register observer and start level |
| Lost | `start` | Lost | Do not start level |
| Won | `start` | Won | Do not start level |
| Running | `start` | Running | No-op |
| Running | `stop` | Paused | Stop level |
| Paused | `start`, player alive and pellets remain | Running | Resume level |
| Running | `move` | Running | Forward move to level |
| Paused | `move` | Paused | Ignore move |
| Running | `levelWon` | Won | Stop game |
| Running | `levelLost` | Lost | Stop game |

## 4. JUnit path tests

`src/test/java/nl/tudelft/jpacman/game/GameStateMachineTest.java` tests the
start/stop/resume cycle, both rejected-start guards, idempotent start, gated
movement, and the win/loss terminal transitions. Mockito supplies the level
boundary so each event is deterministic and no scheduled ghost thread can
interfere with a test.

Run them from this directory with:

```sh
mvn test
```
