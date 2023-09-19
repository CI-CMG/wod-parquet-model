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

public class Cast implements Serializable {

  private static final long serialVersionUID = 0L;

  public static StructType structType() {
    return new StructType(new StructField[]{
        new StructField("dataset", DataTypes.StringType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("castNumber", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("cruiseNumber", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("originatorsCruise", DataTypes.StringType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("timestamp", DataTypes.LongType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("year", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("month", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("day", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("time", DataTypes.DoubleType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("longitude", DataTypes.DoubleType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("latitude", DataTypes.DoubleType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("profileType", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("originatorsStationCode", DataTypes.StringType, true, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("geohash", DataTypes.StringType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("variables", DataTypes.createArrayType(Variable.structType()), false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("principalInvestigators", DataTypes.createArrayType(PrincipalInvestigator.structType()), false,
            org.apache.spark.sql.types.Metadata.empty()),
        new StructField("attributes", DataTypes.createArrayType(Attribute.structType()), false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("biologicalAttributes", DataTypes.createArrayType(Attribute.structType()), false,
            org.apache.spark.sql.types.Metadata.empty()),
        new StructField("taxonomicDatasets", DataTypes.createArrayType(TaxonomicDataset.structType()), false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("depths", DataTypes.createArrayType(Depth.structType()), false, org.apache.spark.sql.types.Metadata.empty())
    });
  }

  public Row asRow() {
    return new GenericRowWithSchema(new Object[]{
        dataset,
        castNumber,
        cruiseNumber,
        originatorsCruise,
        timestamp,
        year,
        month,
        day,
        time,
        longitude,
        latitude,
        profileType,
        originatorsStationCode,
        geohash,
        variables,
        principalInvestigators,
        attributes,
        biologicalAttributes,
        taxonomicDatasets,
        depths,
    }, structType());
  }


  private String dataset;
  private int castNumber;
  private Integer cruiseNumber;
  private String originatorsCruise;
  private long timestamp;
  private int year;
  private int month;
  private int day;
  private double time;
  private double longitude;
  private double latitude;
  private int profileType;
  private String originatorsStationCode;
  private String geohash;
  private List<Variable> variables;
  private List<PrincipalInvestigator> principalInvestigators;
  private List<Attribute> attributes;
  private List<Attribute> biologicalAttributes;
  private List<TaxonomicDataset> taxonomicDatasets;
  private List<Depth> depths;

  @Deprecated
  public Cast() {
  }

  private Cast(String dataset, int castNumber, Integer cruiseNumber, String originatorsCruise, long timestamp, int year, int month, int day, double time, double longitude, double latitude,
      int profileType, String originatorsStationCode, String geohash, List<Variable> variables, List<PrincipalInvestigator> principalInvestigators,
      List<Attribute> attributes, List<Attribute> biologicalAttributes, List<TaxonomicDataset> taxonomicDatasets, List<Depth> depths) {
    this.dataset = dataset;
    this.castNumber = castNumber;
    this.cruiseNumber = cruiseNumber;
    this.originatorsCruise = originatorsCruise;
    this.timestamp = timestamp;
    this.year = year;
    this.month = month;
    this.day = day;
    this.time = time;
    this.longitude = longitude;
    this.latitude = latitude;
    this.profileType = profileType;
    this.originatorsStationCode = originatorsStationCode;
    this.geohash = geohash;
    this.variables = Collections.unmodifiableList(variables);
    this.principalInvestigators = Collections.unmodifiableList(principalInvestigators);
    this.attributes = Collections.unmodifiableList(attributes);
    this.biologicalAttributes = Collections.unmodifiableList(biologicalAttributes);
    this.taxonomicDatasets = Collections.unmodifiableList(taxonomicDatasets);
    this.depths = Collections.unmodifiableList(depths);
  }

  public String getDataset() {
    return dataset;
  }

  @Deprecated
  public void setDataset(String dataset) {
    this.dataset = dataset;
  }

  public int getCastNumber() {
    return castNumber;
  }

  @Deprecated
  public void setCastNumber(int castNumber) {
    this.castNumber = castNumber;
  }

  public Integer getCruiseNumber() {
    return cruiseNumber;
  }

  @Deprecated
  public void setCruiseNumber(Integer cruiseNumber) {
    this.cruiseNumber = cruiseNumber;
  }

  public String getOriginatorsCruise() {
    return originatorsCruise;
  }

  @Deprecated
  public void setOriginatorsCruise(String originatorsCruise) {
    this.originatorsCruise = originatorsCruise;
  }

  public long getTimestamp() {
    return timestamp;
  }

  @Deprecated
  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public int getYear() {
    return year;
  }

  @Deprecated
  public void setYear(int year) {
    this.year = year;
  }

  public int getMonth() {
    return month;
  }

  @Deprecated
  public void setMonth(int month) {
    this.month = month;
  }

  public int getDay() {
    return day;
  }

  @Deprecated
  public void setDay(int day) {
    this.day = day;
  }

  public double getTime() {
    return time;
  }

  @Deprecated
  public void setTime(double time) {
    this.time = time;
  }

  public double getLongitude() {
    return longitude;
  }

  @Deprecated
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  @Deprecated
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public int getProfileType() {
    return profileType;
  }

  @Deprecated
  public void setProfileType(int profileType) {
    this.profileType = profileType;
  }

  public String getOriginatorsStationCode() {
    return originatorsStationCode;
  }

  @Deprecated
  public void setOriginatorsStationCode(String originatorsStationCode) {
    this.originatorsStationCode = originatorsStationCode;
  }

  public String getGeohash() {
    return geohash;
  }

  @Deprecated
  public void setGeohash(String geohash) {
    this.geohash = geohash;
  }

  public List<Variable> getVariables() {
    return variables;
  }

  @Deprecated
  public void setVariables(List<Variable> variables) {
    if (variables == null) {
      variables = new ArrayList<>(0);
    }
    this.variables = new ArrayList<>(variables);
  }

  public List<PrincipalInvestigator> getPrincipalInvestigators() {
    return principalInvestigators;
  }

  @Deprecated
  public void setPrincipalInvestigators(List<PrincipalInvestigator> principalInvestigators) {
    if (principalInvestigators == null) {
      principalInvestigators = new ArrayList<>(0);
    }
    this.principalInvestigators = new ArrayList<>(principalInvestigators);
  }

  public List<Attribute> getAttributes() {
    return attributes;
  }

  @Deprecated
  public void setAttributes(List<Attribute> attributes) {
    if (attributes == null) {
      attributes = new ArrayList<>(0);
    }
    this.attributes = new ArrayList<>(attributes);
  }

  public List<Attribute> getBiologicalAttributes() {
    return biologicalAttributes;
  }

  @Deprecated
  public void setBiologicalAttributes(List<Attribute> biologicalAttributes) {
    if (biologicalAttributes == null) {
      biologicalAttributes = new ArrayList<>(0);
    }
    this.biologicalAttributes = new ArrayList<>(biologicalAttributes);
  }

  public List<TaxonomicDataset> getTaxonomicDatasets() {
    return taxonomicDatasets;
  }

  @Deprecated
  public void setTaxonomicDatasets(List<TaxonomicDataset> taxonomicDatasets) {
    if (taxonomicDatasets == null) {
      taxonomicDatasets = new ArrayList<>(0);
    }
    this.taxonomicDatasets = taxonomicDatasets;
  }

  public List<Depth> getDepths() {
    return depths;
  }

  @Deprecated
  public void setDepths(List<Depth> depths) {
    if (depths == null) {
      depths = new ArrayList<>(0);
    }
    this.depths = depths;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cast cast = (Cast) o;
    return castNumber == cast.castNumber && timestamp == cast.timestamp && year == cast.year && month == cast.month && day == cast.day
        && Double.compare(cast.time, time) == 0 && Double.compare(cast.longitude, longitude) == 0
        && Double.compare(cast.latitude, latitude) == 0 && profileType == cast.profileType && Objects.equals(dataset, cast.dataset)
        && Objects.equals(cruiseNumber, cast.cruiseNumber) && Objects.equals(originatorsCruise, cast.originatorsCruise)
        && Objects.equals(originatorsStationCode, cast.originatorsStationCode) && Objects.equals(geohash, cast.geohash)
        && Objects.equals(variables, cast.variables) && Objects.equals(principalInvestigators, cast.principalInvestigators)
        && Objects.equals(attributes, cast.attributes) && Objects.equals(biologicalAttributes, cast.biologicalAttributes)
        && Objects.equals(taxonomicDatasets, cast.taxonomicDatasets) && Objects.equals(depths, cast.depths);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataset, castNumber, cruiseNumber, originatorsCruise, timestamp, year, month, day, time, longitude, latitude, profileType,
        originatorsStationCode, geohash, variables, principalInvestigators, attributes, biologicalAttributes, taxonomicDatasets, depths);
  }

  @Override
  public String toString() {
    return "Cast{" +
        "dataset='" + dataset + '\'' +
        ", castNumber=" + castNumber +
        ", cruiseNumber=" + cruiseNumber +
        ", originatorsCruise='" + originatorsCruise + '\'' +
        ", timestamp=" + timestamp +
        ", year=" + year +
        ", month=" + month +
        ", day=" + day +
        ", time=" + time +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        ", profileType=" + profileType +
        ", originatorsStationCode='" + originatorsStationCode + '\'' +
        ", geohash='" + geohash + '\'' +
        ", variables=" + variables +
        ", principalInvestigators=" + principalInvestigators +
        ", attributes=" + attributes +
        ", biologicalAttributes=" + biologicalAttributes +
        ", taxonomicDatasets=" + taxonomicDatasets +
        ", depths=" + depths +
        '}';
  }

  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(Cast source) {
    return new Builder(source);
  }

  public static Builder builder(Row row) {
    return new Builder(row);
  }

  public static class Builder {

    private String dataset;
    private int castNumber;
    private Integer cruiseNumber;
    private String originatorsCruise;
    private long timestamp;
    private int year;
    private int month;
    private int day;
    private double time;
    private double longitude;
    private double latitude;
    private int profileType;
    private String originatorsStationCode;
    private String geohash;
    private List<Variable> variables = new ArrayList<>(0);
    private List<PrincipalInvestigator> principalInvestigators = new ArrayList<>(0);
    private List<Attribute> attributes = new ArrayList<>(0);
    private List<Attribute> biologicalAttributes = new ArrayList<>(0);
    private List<TaxonomicDataset> taxonomicDatasets = new ArrayList<>(0);
    private List<Depth> depths = new ArrayList<>(0);

    private Builder() {
    }

    private Builder(Cast source) {
      dataset = source.getDataset();
      castNumber = source.getCastNumber();
      cruiseNumber = source.getCruiseNumber();
      originatorsCruise = source.getOriginatorsCruise();
      timestamp = source.getTimestamp();
      year = source.getYear();
      month = source.getMonth();
      day = source.getDay();
      time = source.getTime();
      longitude = source.getLongitude();
      latitude = source.getLatitude();
      profileType = source.getProfileType();
      originatorsStationCode = source.getOriginatorsStationCode();
      geohash = source.getGeohash();
      variables = source.getVariables();
      principalInvestigators = source.getPrincipalInvestigators();
      attributes = source.getAttributes();
      biologicalAttributes = source.getBiologicalAttributes();
      taxonomicDatasets = source.getTaxonomicDatasets();
      depths = source.getDepths();
    }

    private Builder(Row row) {
      dataset = row.getAs("dataset");
      castNumber = row.getAs("castNumber");
      cruiseNumber = row.getAs("cruiseNumber");
      originatorsCruise = row.getAs("originatorsCruise");
      timestamp = row.getAs("timestamp");
      year = row.getAs("year");
      month = row.getAs("month");
      day = row.getAs("day");
      time = row.getAs("time");
      longitude = row.getAs("longitude");
      latitude = row.getAs("latitude");
      profileType = row.getAs("profileType");
      originatorsStationCode = row.getAs("originatorsStationCode");
      geohash = row.getAs("geohash");
      variables = RowUtils.getAs(row, "variables", r -> Variable.builder(r).build());
      principalInvestigators = RowUtils.getAs(row, "principalInvestigators", r -> PrincipalInvestigator.builder(r).build());
      attributes = RowUtils.getAs(row, "attributes", r -> Attribute.builder(r).build());
      biologicalAttributes = RowUtils.getAs(row, "biologicalAttributes", r -> Attribute.builder(r).build());
      taxonomicDatasets = RowUtils.getAs(row, "taxonomicDatasets", r -> TaxonomicDataset.builder(r).build());
      depths = RowUtils.getAs(row, "depths", r -> Depth.builder(r).build());
    }

    public Builder withDataset(String dataset) {
      this.dataset = dataset;
      return this;
    }

    public Builder withCastNumber(int castNumber) {
      this.castNumber = castNumber;
      return this;
    }

    public Builder withCruiseNumber(Integer cruiseNumber) {
      this.cruiseNumber = cruiseNumber;
      return this;
    }

    public Builder withOriginatorsCruise(String originatorsCruise) {
      this.originatorsCruise = originatorsCruise;
      return this;
    }

    public Builder withTimestamp(long timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public Builder withYear(int year) {
      this.year = year;
      return this;
    }

    public Builder withMonth(int month) {
      this.month = month;
      return this;
    }

    public Builder withDay(int day) {
      this.day = day;
      return this;
    }

    public Builder withTime(double time) {
      this.time = time;
      return this;
    }

    public Builder withLongitude(double longitude) {
      this.longitude = longitude;
      return this;
    }

    public Builder withLatitude(double latitude) {
      this.latitude = latitude;
      return this;
    }

    public Builder withProfileType(int profileType) {
      this.profileType = profileType;
      return this;
    }

    public Builder withOriginatorsStationCode(String originatorsStationCode) {
      this.originatorsStationCode = originatorsStationCode;
      return this;
    }

    public Builder withGeohash(String geohash) {
      this.geohash = geohash;
      return this;
    }

    public Builder withVariables(List<Variable> variables) {
      this.variables = variables == null ? new ArrayList<>(0) : new ArrayList<>(variables);
      return this;
    }

    public Builder withPrincipalInvestigators(List<PrincipalInvestigator> principalInvestigators) {
      this.principalInvestigators = principalInvestigators == null ? new ArrayList<>(0) : new ArrayList<>(principalInvestigators);
      return this;
    }

    public Builder withAttributes(List<Attribute> attributes) {
      this.attributes = attributes == null ? new ArrayList<>(0) : new ArrayList<>(attributes);
      return this;
    }

    public Builder withBiologicalAttributes(List<Attribute> biologicalAttributes) {
      this.biologicalAttributes = biologicalAttributes == null ? new ArrayList<>(0) : new ArrayList<>(biologicalAttributes);
      return this;
    }

    public Builder withTaxonomicDatasets(List<TaxonomicDataset> taxonomicDatasets) {
      this.taxonomicDatasets = taxonomicDatasets == null ? new ArrayList<>(0) : new ArrayList<>(taxonomicDatasets);
      return this;
    }

    public Builder withDepths(List<Depth> depths) {
      this.depths = depths == null ? new ArrayList<>(0) : new ArrayList<>(depths);
      return this;
    }

    public Cast build() {
      return new Cast(
          dataset,
          castNumber,
          cruiseNumber,
          originatorsCruise,
          timestamp,
          year,
          month,
          day,
          time,
          longitude,
          latitude,
          profileType,
          originatorsStationCode,
          geohash,
          variables,
          principalInvestigators,
          attributes,
          biologicalAttributes,
          taxonomicDatasets,
          depths
      );
    }
  }
}










