package museumvisit;

public class Exit extends MuseumSite {

  public Exit() {
    super("Exit");
  }

  @Override
  public boolean hasAvailability() {
    return true;
  }
}
