package museumvisit;

import java.util.Optional;

public class Turnstile {

  private final MuseumSite originRoom;
  private final MuseumSite destinationRoom;

  public Turnstile(MuseumSite originRoom, MuseumSite destinationRoom) {
    assert !originRoom.equals(destinationRoom);
    this.originRoom = originRoom;
    this.destinationRoom = destinationRoom;
    // complete here if needed
  }

  public Optional<MuseumSite> passToNextRoom() {
    // assertions for exiting entire museum or not
    if (this.destinationRoom.hasAvailability()) {
      this.originRoom.exit();
      this.destinationRoom.enter();
      return Optional.of(this.destinationRoom);
    } else
      return Optional.empty();
  }

  public MuseumSite getOriginRoom() {
    return this.originRoom;
  }

  public MuseumSite getDestinationRoom() {
    return this.destinationRoom;
  }
}
