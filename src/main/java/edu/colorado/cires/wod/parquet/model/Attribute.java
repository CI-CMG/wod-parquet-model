
package edu.colorado.cires.wod.parquet.model;

import java.io.Serializable;
import java.util.Objects;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class Attribute implements Serializable {
  private static final long serialVersionUID = 0L;

  public static StructType structType() {
    return new StructType(new StructField[]{
        new StructField("code", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("value", DataTypes.DoubleType, false, org.apache.spark.sql.types.Metadata.empty())
    });
  }

  public Row asRow() {
    return new GenericRowWithSchema(new Object[]{code, value}, structType());
  }

  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(Attribute source) {
    return new Builder(source);
  }

  public static Builder builder(Row row) {
    return new Builder(row);
  }

  private int code;
  private double value;

  private Attribute(int code, double value) {
    this.code = code;
    this.value = value;
  }

  @Deprecated
  public Attribute() {
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Attribute attribute = (Attribute) o;
    return code == attribute.code && Double.compare(attribute.value, value) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, value);
  }

  @Override
  public String toString() {
    return "Attribute{" +
        "code=" + code +
        ", value=" + value +
        '}';
  }

  public static class Builder {
    private int code;
    private double value;

    private Builder() {

    }
    private Builder(Attribute source) {
      code = source.getCode();
      value = source.getValue();
    }

    private Builder(Row row) {
      code = row.getAs("code");
      value = row.getAs("value");
    }

    public Builder withCode(int code) {
      this.code = code;
      return this;
    }

    public Builder withValue(double value) {
      this.value = value;
      return this;
    }

    public Attribute build() {
      return new Attribute(code, value);
    }
  }
}










