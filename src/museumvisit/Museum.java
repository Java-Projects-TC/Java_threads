package museumvisit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
    // Your solution has to work with any number of visitors.
    final int numberOfVisitors = 100;
    final Museum museum = buildLoopyMuseum(); // buildSimpleMuseum();
    // buildTinyMuseum();

    // create the threads for the visitors and get them moving
    List<Thread> visitors = new ArrayList<>();
    IntStream.range(0, numberOfVisitors).sequential().forEach(i -> {
      Thread visitorThread =
          new Thread(new Visitor("Vis" + i, museum.getEntrance()));
      visitors.add(visitorThread);
      visitorThread.start();
    });

    // wait for them to complete their visit
    visitors.forEach(v -> {
      try {
        v.join();
      } catch (InterruptedException e) {
      }
    });

    // Checking no one is left behind
    if (museum.getExit().getOccupancy() == numberOfVisitors) {
      System.out.println("\nAll the visitors reached the exit\n");
    } else {
      System.out
          .println("\n" + (numberOfVisitors - museum.getExit().getOccupancy())
              + " visitors did not reach the exit. Where are they?\n");
    }

    // check no all rooms but the exit are empty
    System.out.println(
        "Occupancy status for each room (should all be zero, but the exit site):");
    museum.getSites().forEach(s -> {
      System.out.println(
          "Site " + s.getName() + " final occupancy: " + s.getOccupancy());
    });
  }

  public static Museum buildSimpleMuseum() {
    // Create rooms
    Entrance entrance = new Entrance();
    ExhibitionRoom exhibitionRoom = new ExhibitionRoom("Exhibition room", 10);
    Exit exit = new Exit();

    // Create turnstiles
    entrance.addExitTurnstile(new Turnstile(entrance, exhibitionRoom));
    exhibitionRoom.addExitTurnstile(new Turnstile(exhibitionRoom, exit));

    // Used streams here to create the set using as little code as possible
    Set<MuseumSite> sites = Stream.of(entrance, exhibitionRoom, exit)
        .collect(Collectors.toSet());

    // Create Museum
    return new Museum(entrance, exit, sites);
  }

  public static Museum buildLoopyMuseum() {
    // Create rooms
    Entrance entrance = new Entrance();
    ExhibitionRoom venomRoom = new ExhibitionRoom("Venom room", 10);
    ExhibitionRoom whaleRoom = new ExhibitionRoom("Whale room", 10);
    Exit exit = new Exit();

    // Create turnstiles
    entrance.addExitTurnstile(new Turnstile(entrance, venomRoom));
    venomRoom.addExitTurnstile(new Turnstile(venomRoom, whaleRoom));
    whaleRoom.addExitTurnstile(new Turnstile(whaleRoom, venomRoom));
    venomRoom.addExitTurnstile(new Turnstile(venomRoom, exit));

    // again used streams. I acknowledge the fact that I could have created a
    // hash set and added the rooms but this requires 3 lines of code.
    Set<MuseumSite> sites = Stream.of(entrance, venomRoom, whaleRoom, exit)
        .collect(Collectors.toSet());

    // Create Museum
    return new Museum(entrance, exit, sites);
  }

  public static Museum buildTinyMuseum() {
    Entrance entrance = new Entrance();
    Exit exit = new Exit();

    entrance.addExitTurnstile(new Turnstile(entrance, exit));

    Set<MuseumSite> sites = Stream.of(entrance, exit)
        .collect(Collectors.toSet());

    return new Museum(entrance, exit, sites);
  }

  //public static Museum buildBigMuseum() {}

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
