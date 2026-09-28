package pt.up.fe.withprivate;

public class MyClassWithPrivateFieldAndMethod {

  public String field1 = "";

  public String valueSetByPrivateMethod = "";

  private String hiddenField = "initial";

  public String getValue() {
    return this.hiddenField;
  }

  public String getValueSetByPrivateMethod() {
   return this.valueSetByPrivateMethod;
  }

  public String toBeMockedByMockito() {
    return "stuff";
  }

  private void meineMethod() {
    this.valueSetByPrivateMethod = "lalal";
  }
}
