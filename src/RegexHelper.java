public final class RegexHelper {
  private RegexHelper() { // private constructor to emulate static function
  }

  public static String delivery() {
    return "";
  }

  public static String previews() {
    return "([A-Z]{3}\\d{6})\\w*";
  }

  static final String option = "([^#\\s]+)[\\s]*=[\\s]*([^#\\s]+)#*.*";
  static final String rawComic = "\\d+\\s(\\w{6,11})\\s\\d+,\\d\\d\\s(.+)\\s#(\\d+)\\s";
  static final String rawComicVariant = "\\d+\\s(\\w{6,11})\\s\\d+,\\d\\d\\s(.+)\\s#(\\d+)(\\.\\w+)";

}
