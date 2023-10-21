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

public class TaxonomicDataset implements Serializable {
  private static final long serialVersionUID = 0L;

  public static StructType structType() {
    return new StructType(new StructField[]{
        new StructField("values", DataTypes.createArrayType(QcAttribute.structType(), false), false, org.apache.spark.sql.types.Metadata.empty()),
    });
  }

  public Row asRow() {
    return new GenericRowWithSchema(new Object[]{values}, structType());
  }

  private List<QcAttribute> values;

  @Deprecated
  public TaxonomicDataset() {
  }

  private TaxonomicDataset(List<QcAttribute> values) {
    this.values = Collections.unmodifiableList(values);
  }

  @Nonnull
  public List<QcAttribute> getValues() {
    return values;
  }

  @Deprecated
  public void setValues(List<QcAttribute> values) {
    this.values = values == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(values));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaxonomicDataset that = (TaxonomicDataset) o;
    return Objects.equals(values, that.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(values);
  }

  @Override
  public String toString() {
    return "TaxonomicDataset{" +
        "values=" + values +
        '}';
  }

  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(TaxonomicDataset source) {
    return new Builder(source);
  }

  public static Builder builder(Row row) {
    return new Builder(row);
  }

  public static class Builder {
    private List<QcAttribute> values = new ArrayList<>(0);

    private Builder() {

    }

    private Builder(TaxonomicDataset orig) {
      values = orig.getValues();
    }

    private Builder(Row row) {
      values = RowUtils.getAs(row, "values",  r -> QcAttribute.builder(r).build());
    }

    public Builder withValues(List<QcAttribute> values) {
      this.values = values == null ? new ArrayList<>(0) : new ArrayList<>(values);
      return this;
    }

    public TaxonomicDataset build() {
      return new TaxonomicDataset(values);
    }
  }
}
