package museumvisit;

public class Exit extends MuseumSite {

  public Exit() {
    super("Exit");
  }

  @Override
  boolean hasAvailability() {
    return true;
  }
}
