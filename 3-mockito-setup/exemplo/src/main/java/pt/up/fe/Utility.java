package pt.up.fe;

public final class Utility {

  private Utility() {
  }

  public static String getDatabaseConnection(String environment) {
    return "http:///production/" + environment;
  }
}
