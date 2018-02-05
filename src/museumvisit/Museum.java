package museumvisit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Museum {

  private final Entrance entrance;
  private final Exit exit;
  private final Set<MuseumSite> sites;

  public Museum(Entrance entrance, Exit exit, Set<MuseumSite> sites) {
    this.entrance = entrance;
    this.exit = exit;
    this.sites = sites;
  }

  public static void main(String[] args) {
    final int numberOfVisitors = 100; // Your solution has to work with any
    // number of visitors
    final Museum museum = buildSimpleMuseum(); // buildLoopyMuseum();

    // create the threads for the visitors and get them moving

    // wait for them to complete their visit

    // Checking no one is left behind
    if (museum.getExit().getOccupancy() == numberOfVisitors) {
      System.out.println("\nAll the visitors reached the exit\n");
    } else {
      System.out
          .println("\n" + (numberOfVisitors - museum.getExit().getOccupancy())
              + " visitors did not reach the exit. Where are they?\n");
    }

    System.out.println(
        "Occupancy status for each room (should all be zero, but the exit site):");
    museum.getSites().forEach(s -> {
      System.out.println(
          "Site " + s.getName() + " final occupancy: " + s.getOccupancy());
    });
  }

  public static Museum buildSimpleMuseum() {
    Entrance entrance = new Entrance();
    ExhibitionRoom exhibitionRoom = new ExhibitionRoom("Exhibition room", 10);
    Exit exit = new Exit();

    entrance.addExitTurnstile(new Turnstile(entrance, exhibitionRoom));
    exhibitionRoom.addExitTurnstile(new Turnstile(exhibitionRoom, exit));

    // Used streams here to create the set using as little code as possible
    Set<MuseumSite> sites = Stream.of(exhibitionRoom)
        .collect(Collectors.toSet());

    return new Museum(entrance, exit, sites);
  }

  public static Museum buildLoopyMuseum() {
    Entrance entrance = new Entrance();
    ExhibitionRoom venomRoom = new ExhibitionRoom("Venom room", 10);
    ExhibitionRoom whaleRoom = new ExhibitionRoom("Whale room", 10);
    Exit exit = new Exit();

    entrance.addExitTurnstile(new Turnstile(entrance, venomRoom));
    venomRoom.addExitTurnstile(new Turnstile(venomRoom, whaleRoom));
    whaleRoom.addExitTurnstile(new Turnstile(whaleRoom, venomRoom));
    venomRoom.addExitTurnstile(new Turnstile(venomRoom, exit));

    Set<MuseumSite> sites = Stream.of(venomRoom, whaleRoom)
        .collect(Collectors.toSet());

    return new Museum(entrance, exit, sites);
  }

  public Entrance getEntrance() {
    return entrance;
  }

  public Exit getExit() {
    return exit;
  }

  public Set<MuseumSite> getSites() {
    return sites;
  }
}
