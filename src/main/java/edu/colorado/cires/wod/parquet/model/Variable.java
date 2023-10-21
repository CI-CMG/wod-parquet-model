package edu.colorado.cires.wod.parquet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

/**
 *
 * (3) Variable-specific secondary header: Information specific to each individual measured variable such as originator’s units, scales, and methods.
 *
 */
public class Variable implements Serializable {

  private static final long serialVersionUID = 0L;

  public static StructType structType() {
    return new StructType(new StructField[]{
        new StructField("code", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("metadata", DataTypes.createArrayType(Metadata.structType(), false), false, org.apache.spark.sql.types.Metadata.empty())
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

  /**
   * 1 NCEI Accession Number
   * 2 NCEI Project Code
   * 3 WOD Platform Code
   * 4 NCEI Institution Code
   * 5 Cast/Tow number
   * 7 Originator’s station number
   * 8 Depth Precision
   * 9 Ocean Weather Station
   * 10 Bottom Depth (meters)
   * 11 Cast Duration (hours)
   * 12 Cast Direction (down assumed)
   * 13 High-resolution pairs
   * 14 Water Color
   * 15 Water Transparency (Secchi disk)
   * 16 Wave Direction (WMO 0877 or NCEI 0110)
   * 17 Wave Height (WMO 1555 or NCEI 0104)
   * 18 Sea State (WMO 3700 or NCEI 0109)
   * 19 Wind Force (Beaufort scale or NCEI 0052)
   * 20 Wave Period (WMO 3155 or NCEI 0378)
   * 21 Wind Direction (WMO 0877 or NCEI 0110)
   * 22 Wind Speed (knots)
   * 23 Barometric Pressure (millibars)
   * 24 Dry Bulb Temperature (°C)
   * 25 Wet Bulb Temperature (°C)
   * 26 Weather Conditions (WMO 4501/4677)
   * 27 Cloud Type (WMO 0500 or NCEI 0053)
   * 28 Cloud Cover (WMO 2700 or NCEI 0105)
   * 29 Probe Type
   * 30 Calibration Depth
   * 31 Calibration Temperature
   * 32 Recorder (WMO 4770)
   * 33 Depth Correction
   * 34 Bottom Hit
   * App2 ID1
   * File 35 File 36
   * File 37 File 38
   * 39
   * 40
   * 41 2.1 45 46 47 2.2 48
   * 49
   * 2.3 54
   * 71
   * 2.4 72
   * 2.5 73
   * 2.6 74 2.7 77
   * 2.8 84 2.9 85 86 87 88 91 2.10 92 2.11 93 2.12 94 2.13 95 96
   * 97 2.14 98
   * 99
   * DESCRIPTION App2
   * Digitization Method (NCEI 0612) 2.15
   * Digitization Interval (NCEI 0613) 2.16
   * Data Treatment and Storage Method (NCEI 2.17 0614)
   * Trace Correction Temperature Correction
   * Instrument for reference temperature (NCEI 2.18 0615)
   * Horizontal visibility (WMO Code 4300) 2.19 Absolute Humidity (g/m3)
   * Reference/Sea Surface Temperature
   * Sea Surface Salinity
   * Year in which probe was manufactured
   * Speed of ship (knots) when probe was dropped
   * Depth fix 2.20 Real time
   * XBT Wait (code no longer used)
   * XBT Frequency (code no longer used) Oceanographic measuring vehicle 2.21 xCO2 in atmosphere (ppm)
   * ARGOS fix code 2.24 ARGOS time (hours) from last fix
   * ARGOS time (hours) to next fix
   * Height (meters) of XBT launch
   * Depth of sea surface sensor
   * Database ID 2.25 UKHO Bibliographic Reference Number 2.26 Consecutive profile in a tow segment
   * WMO Identification Code
   * Originator’s Depth unit 2.27 Originator’s flags 2.28
   * Water Sampler 2.29
   * ARGOS ID number
   * Time stamp (YYYYJJJ, Y=year, J= year day) to indicate when ASCII version of cast was
   * d
   *
   *
   * @return
   */
  public int getCode() {
    return code;
  }

  @Deprecated
  public void setCode(int code) {
    this.code = code;
  }

  @Nonnull
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










