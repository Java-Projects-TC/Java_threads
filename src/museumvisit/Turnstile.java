package museumvisit;

import java.util.Optional;

public class Turnstile {

  private final MuseumSite originRoom;
  private final MuseumSite destinationRoom;

  public Turnstile(MuseumSite originRoom, MuseumSite destinationRoom) {
    assert !originRoom.equals(destinationRoom);
    this.originRoom = originRoom;
    this.destinationRoom = destinationRoom;
  }

  public Optional<MuseumSite> passToNextRoom() {
    // Determine ordering between the two rooms joined bt the turnstile in
    // order to remove the circular dependency;
    MuseumSite firstRoomToLock, secondRoomToLock;
    if (originRoom.getName().compareTo(destinationRoom.getName()) < 0) {
      firstRoomToLock = originRoom;
      secondRoomToLock = destinationRoom;
    } else {
      firstRoomToLock = destinationRoom;
      secondRoomToLock = originRoom;
    }

    // lock the rooms in order to carry out the code in the critical region.
    synchronized (firstRoomToLock) {
      synchronized (secondRoomToLock) {

        // critical region
        if (this.destinationRoom.hasAvailability()) {
          originRoom.exit();
          destinationRoom.enter();
          return Optional.of(destinationRoom);
        } else {
          return Optional.empty();
        }
      }
    }
  }

  public MuseumSite getOriginRoom() {
    return this.originRoom;
  }

  public MuseumSite getDestinationRoom() {
    return this.destinationRoom;
  }
}
