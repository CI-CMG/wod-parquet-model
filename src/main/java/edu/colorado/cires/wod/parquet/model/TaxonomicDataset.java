package edu.colorado.cires.wod.parquet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class TaxonomicDataset implements Serializable {
  private static final long serialVersionUID = 0L;

  public static StructType structType() {
    return new StructType(new StructField[]{
        new StructField("attributes", DataTypes.createArrayType(QcAttribute.structType()), false, org.apache.spark.sql.types.Metadata.empty()),
    });
  }

  public Row asRow() {
    return new GenericRowWithSchema(new Object[]{attributes}, structType());
  }

  private List<QcAttribute> attributes;

  @Deprecated
  public TaxonomicDataset() {
  }

  private TaxonomicDataset(List<QcAttribute> attributes) {
    this.attributes = Collections.unmodifiableList(attributes);
  }

  public List<QcAttribute> getAttributes() {
    return attributes;
  }

  @Deprecated
  public void setAttributes(List<QcAttribute> attributes) {
    this.attributes = attributes == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(attributes));
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
    return Objects.equals(attributes, that.attributes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attributes);
  }

  @Override
  public String toString() {
    return "TaxonomicDataset{" +
        "attributes=" + attributes +
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
    private List<QcAttribute> attributes = new ArrayList<>(0);

    private Builder() {

    }

    private Builder(TaxonomicDataset orig) {
      attributes = orig.getAttributes();
    }

    private Builder(Row row) {
      attributes = RowUtils.getAs(row, "attributes",  r -> QcAttribute.builder(r).build());
    }

    public Builder withAttributes(List<QcAttribute> attributes) {
      this.attributes = attributes == null ? new ArrayList<>(0) : new ArrayList<>(attributes);
      return this;
    }

    public TaxonomicDataset build() {
      return new TaxonomicDataset(attributes);
    }
  }
}
