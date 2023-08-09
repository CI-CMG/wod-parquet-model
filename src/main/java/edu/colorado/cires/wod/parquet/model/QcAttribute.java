package edu.colorado.cires.wod.parquet.model;

import java.io.Serializable;
import java.util.Objects;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class QcAttribute implements Serializable {

  private static final long serialVersionUID = 0L;

  public static StructType structType() {
    return new StructType(new StructField[]{
        new StructField("code", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("value", DataTypes.DoubleType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("qcFlag", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("originatorsFlag", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
    });
  }

  public Row asRow() {
    return new GenericRowWithSchema(new Object[]{code, value, qcFlag, originatorsFlag}, structType());
  }

  private int code;
  private double value;
  private int qcFlag;
  private int originatorsFlag;

  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(QcAttribute source) {
    return new Builder(source);
  }

  public static Builder builder(Row row) {
    return new Builder(row);
  }

  @Deprecated
  public QcAttribute() {

  }

  private QcAttribute(int code, double value, int qcFlag, int originatorsFlag) {
    this.code = code;
    this.value = value;
    this.qcFlag = qcFlag;
    this.originatorsFlag = originatorsFlag;
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
    QcAttribute that = (QcAttribute) o;
    return code == that.code && Double.compare(that.value, value) == 0 && qcFlag == that.qcFlag && originatorsFlag == that.originatorsFlag;
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, value, qcFlag, originatorsFlag);
  }

  @Override
  public String toString() {
    return "QcAttribute{" +
        "code=" + code +
        ", value=" + value +
        ", qcFlag=" + qcFlag +
        ", originatorsFlag=" + originatorsFlag +
        '}';
  }

  public static class Builder {

    private int code;
    private double value;
    private int qcFlag;
    private int originatorsFlag;

    private Builder() {

    }

    private Builder(QcAttribute source) {
      code = source.getCode();
      value = source.getValue();
      qcFlag = source.getQcFlag();
      originatorsFlag = source.getOriginatorsFlag();
    }

    private Builder(Row row) {
      code = row.getAs("code");
      value = row.getAs("value");
      qcFlag = row.getAs("qcFlag");
      originatorsFlag = row.getAs("originatorsFlag");
    }


    public Builder withCode(int code) {
      this.code = code;
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

    public QcAttribute build() {
      return new QcAttribute(code, value, qcFlag, originatorsFlag);
    }
  }
}










