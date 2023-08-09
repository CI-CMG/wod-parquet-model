package edu.colorado.cires.wod.parquet.model;

import java.io.Serializable;
import java.util.Objects;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class Metadata implements Serializable {

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

  private int code;
  private double value;

  @Deprecated
  public Metadata() {
  }

  private Metadata(int code, double value) {
    this.code = code;
    this.value = value;
  }

  public int getCode() {
    return code;
  }

  @Deprecated
  public void setCode(int code) {
    this.code = code;
  }

  public double getValue() {
    return value;
  }

  @Deprecated
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
    Metadata metadata = (Metadata) o;
    return code == metadata.code && Double.compare(metadata.value, value) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, value);
  }

  @Override
  public String toString() {
    return "Metadata{" +
        "code=" + code +
        ", value=" + value +
        '}';
  }

  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(Metadata source) {
    return new Builder(source);
  }

  public static Builder builder(Row row) {
    return new Builder(row);
  }

  public static class Builder {

    private int code;
    private double value;

    private Builder() {

    }

    private Builder(Metadata source) {
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

    public Metadata build() {
      return new Metadata(code, value);
    }
  }
}










