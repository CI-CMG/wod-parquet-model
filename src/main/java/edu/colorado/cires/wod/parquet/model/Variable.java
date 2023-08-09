package edu.colorado.cires.wod.parquet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class Variable implements Serializable {

  private static final long serialVersionUID = 0L;

  public static StructType structType() {
    return new StructType(new StructField[]{
        new StructField("code", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("metadata", DataTypes.createArrayType(Metadata.structType()), false, org.apache.spark.sql.types.Metadata.empty())
    });
  }

  public Row asRow() {
    return new GenericRowWithSchema(new Object[]{code, metadata}, structType());
  }

  private int code;
  private List<Metadata> metadata;

  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(Variable source) {
    return new Builder(source);
  }

  public static Builder builder(Row row) {
    return new Builder(row);
  }

  @Deprecated
  public Variable() {

  }

  private Variable(int code, List<Metadata> metadata) {
    this.code = code;
    this.metadata = Collections.unmodifiableList(metadata);
  }

  public int getCode() {
    return code;
  }

  @Deprecated
  public void setCode(int code) {
    this.code = code;
  }

  public List<Metadata> getMetadata() {
    return metadata;
  }

  @Deprecated
  public void setMetadata(List<Metadata> metadata) {
    if (metadata == null) {
      metadata = new ArrayList<>(0);
    }
    this.metadata = metadata;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Variable variable = (Variable) o;
    return code == variable.code && Objects.equals(metadata, variable.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, metadata);
  }

  @Override
  public String toString() {
    return "Variable{" +
        "code=" + code +
        ", metadata=" + metadata +
        '}';
  }

  public static class Builder {

    private int code;
    private List<Metadata> metadata = new ArrayList<>(0);

    private Builder() {

    }

    private Builder(Variable source) {
      code = source.getCode();
      metadata = source.getMetadata();
    }

    private Builder(Row row) {
      code = row.getAs("code");
      metadata = RowUtils.getAs(row, "metadata", r -> Metadata.builder(r).build());
    }

    public Builder withCode(int code) {
      this.code = code;
      return this;
    }

    public Builder withMetadata(List<Metadata> metadata) {
      if (metadata == null) {
        metadata = new ArrayList<>(0);
      }
      this.metadata = new ArrayList<>(metadata);
      return this;
    }

    public Variable build() {
      return new Variable(code, metadata);
    }
  }
}










