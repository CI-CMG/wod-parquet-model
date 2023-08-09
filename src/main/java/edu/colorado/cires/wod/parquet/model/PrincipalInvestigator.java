package edu.colorado.cires.wod.parquet.model;

import java.io.Serializable;
import java.util.Objects;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class PrincipalInvestigator implements Serializable {
  private static final long serialVersionUID = 0L;

  public static StructType structType() {
    return new StructType(new StructField[]{
        new StructField("variable", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("code", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty())
    });
  }

  public Row asRow() {
    return new GenericRowWithSchema(new Object[]{variable, code}, structType());
  }

  private int variable;
  private int code;

  @Deprecated
  public PrincipalInvestigator() {
  }

  private PrincipalInvestigator(int variable, int code) {
    this.variable = variable;
    this.code = code;
  }


  public int getVariable() {
    return variable;
  }

  @Deprecated
  public void setVariable(int variable) {
    this.variable = variable;
  }

  public int getCode() {
    return code;
  }

  @Deprecated
  public void setCode(int code) {
    this.code = code;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PrincipalInvestigator that = (PrincipalInvestigator) o;
    return variable == that.variable && code == that.code;
  }

  @Override
  public int hashCode() {
    return Objects.hash(variable, code);
  }

  @Override
  public String toString() {
    return "PrincipalInvestigator{" +
        "variable=" + variable +
        ", code=" + code +
        '}';
  }

  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(PrincipalInvestigator source) {
    return new Builder(source);
  }

  public static Builder builder(Row row) {
    return new Builder(row);
  }

  public static class Builder {
    private int variable;
    private int code;

    private Builder() {

    }

    private Builder(PrincipalInvestigator source) {
      variable = source.getVariable();
      code = source.getCode();
    }

    private Builder(Row row) {
      variable = row.getAs("variable");
      code = row.getAs("code");
    }

    public Builder withVariable(int variable) {
      this.variable = variable;
      return this;
    }

    public Builder withCode(int code) {
      this.code = code;
      return this;
    }

    public PrincipalInvestigator build() {
      return new PrincipalInvestigator(variable, code);
    }
  }
}










