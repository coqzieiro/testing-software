package pt.up.fe.service;

import static pt.up.fe.model.Race.HOBBIT;
import static pt.up.fe.model.Race.MAIA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import pt.up.fe.model.Movie;
import pt.up.fe.model.Race;
import pt.up.fe.model.Ring;
import pt.up.fe.model.TolkienCharacter;
import pt.up.fe.service.DataService;

class DataServiceTest {

  private DataService dataService;

  @BeforeEach
  public void setup() {
    this.dataService = new DataService();
  }

  @Test
  public void ensureThatInitializationOfTolkeinCharactorsWorks() {
    TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);
    // TODO check that age is 33
    assertEquals(33, frodo.getAge());
    // TODO check that name is "Frodo"
    assertEquals("Frodo", frodo.getName());
    // TODO check that name is not "Frodon"
    assertNotEquals("Frodon", frodo.getName());
  }

  @Test
  public void ensureFellowShipCharacterAccessByNameReturnsNullForUnknownCharacter() {
    // TODO imlement a check that dataService.getFellowshipCharacter returns null for an
    // unknow felllow, e.g. "Lars"
    assertNull(this.dataService.getFellowshipCharacter("Lars"));
  }

  @Test
  public void ensureFellowShipCharacterAccessByNameWorksGivenCorrectNameIsGiven() {
    // TODO imlement a check that dataService.getFellowshipCharacter returns a fellow for an
    // existing felllow, e.g. "Frodo"
    TolkienCharacter frodo = this.dataService.getFellowshipCharacter("Frodo");

    assertNotNull(frodo);
    assertEquals("Frodo", frodo.getName());
  }

  @Test
  public void ensureThatEqualsWorksForCharaters() {
    Object jake = new TolkienCharacter("Jake", 43, HOBBIT);
    Object sameJake = jake;
    Object jakeClone = new TolkienCharacter("Jake", 12, HOBBIT);
    // TODO check that:
    // jake is equal to sameJake
    // jake is not equal to jakeClone
    assertEquals(jake, sameJake);
    assertNotEquals(jake, jakeClone);
  }

  @Test
  public void checkInheritance() {
    TolkienCharacter tolkienCharacter = this.dataService.getFellowship().get(0);
    // TODO check that tolkienCharacter.getClass is not a movie class
    assertNotEquals(Movie.class, tolkienCharacter.getClass());
  }

  @Test
  public void ensureThatFrodoAndGandalfArePartOfTheFellowsip() {
    List<TolkienCharacter> fellowship = this.dataService.getFellowship();
    // TODO check that Frodo and Gandalf are part of the fellowship
    assertTrue(fellowship.stream().anyMatch(character -> character.getName().equals("Frodo")));
    assertTrue(fellowship.stream().anyMatch(character -> character.getName().equals("Gandalf")));
  }

  @Test
  public void ensureThatOneRingBearerIsPartOfTheFellowship() {
    List<TolkienCharacter> fellowship = this.dataService.getFellowship();
    // TODO test that at least one ring bearer is part of the fellowship
    assertTrue(fellowship.contains(this.dataService.getRingBearers().get(Ring.oneRing)));
  }

  // TODO Use @RepeatedTest(int) to execute this test 1000 times
  @RepeatedTest(1000)
  @Tag("slow")
  @DisplayName("Ensure that we can call getFellowShip multiple times")
  public void ensureThatWeCanRetrieveFellowshipMultipleTimes() {
    this.dataService = new DataService();
    assertNotNull(dataService.getFellowship());
    // TODO remove once @RepeatedTest is in place
  }

  @Test
  public void ensureOrdering() {
    List<TolkienCharacter> fellowship = this.dataService.getFellowship();
    // TODO ensure that the order of the fellowship is:
    // frodo, sam, merry,pippin, gandalf,legolas,gimli,aragorn,boromir
    assertEquals(List.of("Frodo", "Sam", "Merry", "Pippin", "Gandalf", "Legolas", "Gimli", "Aragorn", "Boromir"),
      fellowship.stream().map(TolkienCharacter::getName).collect(java.util.stream.Collectors.toList()));
  }

  @Test
  public void ensureAge() {
    List<TolkienCharacter> fellowship = this.dataService.getFellowship();
    // TODO test ensure that all hobbits and men are younger than 100 years
    assertTrue(fellowship.stream()
      .filter(character -> character.getRace() == Race.HOBBIT || character.getRace() == Race.MAN)
      .allMatch(character -> character.getAge() < 100));

    // TODO also ensure that the elfs, dwars the maia are all older than 100 years
    assertTrue(fellowship.stream()
      .filter(character -> character.getRace() == Race.ELF || character.getRace() == Race.DWARF
        || character.getRace() == Race.MAIA)
      .allMatch(character -> character.getAge() > 100));
    
    // HINT fellowship.stream might be useful here
  }

  @Test
  public void ensureThatFellowsStayASmallGroup() {
    List<TolkienCharacter> fellowship = this.dataService.getFellowship();
    // TODO Write a test to get the 20 element from the fellowship throws an
    // IndexOutOfBoundsException
    assertThrows(IndexOutOfBoundsException.class, () -> fellowship.get(20));
  }

  @Test
  public void ensureServiceDoesNotRunToLong() {
    // TODO Write a test to ensure that update does not run longer than 3 seconds
    // Tip: Use the assertTimeout assert statement.
    assertTimeout(Duration.ofSeconds(3), () -> assertTrue(this.dataService.update()));
  }
}
