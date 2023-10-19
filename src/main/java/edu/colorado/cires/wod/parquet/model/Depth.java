package edu.colorado.cires.wod.parquet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class Depth implements Serializable {

  private static final long serialVersionUID = 0L;

  public static StructType structType() {
    return new StructType(new StructField[]{
        new StructField("depth", DataTypes.DoubleType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("depthErrorFlag", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("originatorsFlag", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("data", DataTypes.createArrayType(ProfileData.structType(), false), false, org.apache.spark.sql.types.Metadata.empty())
    });
  }

  public Row asRow() {
    return new GenericRowWithSchema(new Object[]{depth, depthErrorFlag, originatorsFlag, data}, structType());
  }

  private double depth;
  private int depthErrorFlag;
  private int originatorsFlag;
  private List<ProfileData> data = new ArrayList<>(0);

  @Deprecated
  public Depth() {
  }

  private Depth(double depth, int depthErrorFlag, int originatorsFlag, @Nonnull List<ProfileData> data) {
    this.depth = depth;
    this.depthErrorFlag = depthErrorFlag;
    this.originatorsFlag = originatorsFlag;
    this.data = Collections.unmodifiableList(data);
  }

  public double getDepth() {
    return depth;
  }

  @Deprecated
  public void setDepth(double depth) {
    this.depth = depth;
  }

  public int getDepthErrorFlag() {
    return depthErrorFlag;
  }

  @Deprecated
  public void setDepthErrorFlag(int depthErrorFlag) {
    this.depthErrorFlag = depthErrorFlag;
  }

  public int getOriginatorsFlag() {
    return originatorsFlag;
  }

  @Deprecated
  public void setOriginatorsFlag(int originatorsFlag) {
    this.originatorsFlag = originatorsFlag;
  }

  @Nonnull
  public List<ProfileData> getData() {
    return data;
  }

  @Deprecated
  public void setData(List<ProfileData> data) {
    if (data == null) {
      data = new ArrayList<>(0);
    }
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Depth depth1 = (Depth) o;
    return Double.compare(depth1.depth, depth) == 0 && depthErrorFlag == depth1.depthErrorFlag && originatorsFlag == depth1.originatorsFlag
        && Objects.equals(data, depth1.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(depth, depthErrorFlag, originatorsFlag, data);
  }

  @Override
  public String toString() {
    return "Depth{" +
        "depth=" + depth +
        ", depthErrorFlag=" + depthErrorFlag +
        ", originatorsFlag=" + originatorsFlag +
        ", data=" + data +
        '}';
  }

  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(Depth source) {
    return new Builder(source);
  }

  public static Builder builder(Row row) {
    return new Builder(row);
  }

  public static class Builder {

    private double depth;
    private int depthErrorFlag;
    private int originatorsFlag;
    private List<ProfileData> data = new ArrayList<>(0);

    private Builder() {
    }

    private Builder(Depth source) {
      depth = source.getDepth();
      depthErrorFlag = source.getDepthErrorFlag();
      originatorsFlag = source.getOriginatorsFlag();
      data = source.getData();
    }

    private Builder(Row row) {
      depth = row.getAs("depth");
      depthErrorFlag = row.getAs("depthErrorFlag");
      originatorsFlag = row.getAs("originatorsFlag");
      data = RowUtils.getAs(row, "data", r -> ProfileData.builder(r).build());
    }

    public Builder withDepth(double depth) {
      this.depth = depth;
      return this;
    }

    public Builder withDepthErrorFlag(int depthErrorFlag) {
      this.depthErrorFlag = depthErrorFlag;
      return this;
    }

    public Builder withOriginatorsFlag(int originatorsFlag) {
      this.originatorsFlag = originatorsFlag;
      return this;
    }

    public Builder withData(List<ProfileData> data) {
      if (data == null) {
        data = new ArrayList<>(0);
      }
      this.data = new ArrayList<>(data);
      return this;
    }

    public Depth build() {
      return new Depth(depth, depthErrorFlag, originatorsFlag, data);
    }
  }
}










