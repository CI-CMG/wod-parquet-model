package edu.colorado.cires.wod.parquet.model;

import java.io.Serializable;
import java.util.Objects;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

/**
 * lead scientists or engineers for a particular research cruise or project
 *
 *
 * The Principal Investigator (PI) is also identified by numeric code and by variable code. The PI is the person (or persons), responsible for data collection and this information is included whenever available. A list of the numeric codes associated with each PI can be found in the file: primary_investigator_list.pdf. For the purpose of assigning PI codes, plankton data are identified as variable 14 for all plankton, -5002 for zooplankton, and -5006 for phytoplankton.
 */
public class PrincipalInvestigator implements Serializable {
  private static final long serialVersionUID = 0L;

  public static StructType structType() {
    return new StructType(new StructField[]{
        new StructField("variableCode", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("piCode", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty())
    });
  }

  public Row asRow() {
    return new GenericRowWithSchema(new Object[]{variableCode, piCode}, structType());
  }

  private int variableCode;
  private int piCode;

  @Deprecated
  public PrincipalInvestigator() {
  }

  private PrincipalInvestigator(int variableCode, int piCode) {
    this.variableCode = variableCode;
    this.piCode = piCode;
  }


  public int getVariableCode() {
    return variableCode;
  }

  @Deprecated
  public void setVariableCode(int variableCode) {
    this.variableCode = variableCode;
  }

  public int getPiCode() {
    return piCode;
  }

  @Deprecated
  public void setPiCode(int piCode) {
    this.piCode = piCode;
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
    return variableCode == that.variableCode && piCode == that.piCode;
  }

  @Override
  public int hashCode() {
    return Objects.hash(variableCode, piCode);
  }

  @Override
  public String toString() {
    return "PrincipalInvestigator{" +
        "variableCode=" + variableCode +
        ", piCode=" + piCode +
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
    private int variableCode;
    private int piCode;

    private Builder() {

    }

    private Builder(PrincipalInvestigator source) {
      variableCode = source.getVariableCode();
      piCode = source.getPiCode();
    }

    private Builder(Row row) {
      variableCode = row.getAs("variableCode");
      piCode = row.getAs("piCode");
    }

    public Builder withVariableCode(int variable) {
      this.variableCode = variable;
      return this;
    }

    public Builder withPiCode(int code) {
      this.piCode = code;
      return this;
    }

    public PrincipalInvestigator build() {
      return new PrincipalInvestigator(variableCode, piCode);
    }
  }
}










