package edu.colorado.cires.wod.parquet.model;

import java.io.Serializable;
import java.util.Objects;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class ProfileData implements Serializable {

  private static final long serialVersionUID = 0L;

  public static StructType structType() {
    return new StructType(new StructField[]{
        new StructField("variableCode", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("value", DataTypes.DoubleType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("qcFlag", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("originatorsFlag", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
    });
  }

  public Row asRow() {
    return new GenericRowWithSchema(new Object[]{variableCode, value, qcFlag, originatorsFlag}, structType());
  }

  private int variableCode;
  private double value;
  private int qcFlag;
  private int originatorsFlag;

  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(ProfileData source) {
    return new Builder(source);
  }

  public static Builder builder(Row row) {
    return new Builder(row);
  }

  @Deprecated
  public ProfileData() {

  }

  private ProfileData(int variableCode, double value, int qcFlag, int originatorsFlag) {
    this.variableCode = variableCode;
    this.value = value;
    this.qcFlag = qcFlag;
    this.originatorsFlag = originatorsFlag;
  }

  public int getVariableCode() {
    return variableCode;
  }

  @Deprecated
  public void setVariableCode(int variableCode) {
    this.variableCode = variableCode;
  }

  public double getValue() {
    return value;
  }

  @Deprecated
  public void setValue(double value) {
    this.value = value;
  }

  public int getQcFlag() {
    return qcFlag;
  }

  @Deprecated
  public void setQcFlag(int qcFlag) {
    this.qcFlag = qcFlag;
  }

  public int getOriginatorsFlag() {
    return originatorsFlag;
  }

  @Deprecated
  public void setOriginatorsFlag(int originatorsFlag) {
    this.originatorsFlag = originatorsFlag;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProfileData that = (ProfileData) o;
    return variableCode == that.variableCode && Double.compare(that.value, value) == 0 && qcFlag == that.qcFlag
        && originatorsFlag == that.originatorsFlag;
  }

  @Override
  public int hashCode() {
    return Objects.hash(variableCode, value, qcFlag, originatorsFlag);
  }

  @Override
  public String toString() {
    return "ProfileData{" +
        "variableCode=" + variableCode +
        ", value=" + value +
        ", qcFlag=" + qcFlag +
        ", originatorsFlag=" + originatorsFlag +
        '}';
  }

  public static class Builder {

    private int variableCode;
    private double value;
    private int qcFlag;
    private int originatorsFlag;

    private Builder() {

    }

    private Builder(ProfileData source) {
      variableCode = source.getVariableCode();
      value = source.getValue();
      qcFlag = source.getQcFlag();
      originatorsFlag = source.getOriginatorsFlag();
    }

    private Builder(Row row) {
      variableCode = row.getAs("variableCode");
      value = row.getAs("value");
      qcFlag = row.getAs("qcFlag");
      originatorsFlag = row.getAs("originatorsFlag");
    }

    public Builder withVariableCode(int variableCode) {
      this.variableCode = variableCode;
      return this;
    }

    public Builder withValue(double value) {
      this.value = value;
      return this;
    }

    public Builder withQcFlag(int qcFlag) {
      this.qcFlag = qcFlag;
      return this;
    }

    public Builder withOriginatorsFlag(int originatorsFlag) {
      this.originatorsFlag = originatorsFlag;
      return this;
    }

    public ProfileData build() {
      return new ProfileData(variableCode, value, qcFlag, originatorsFlag);
    }
  }
}










