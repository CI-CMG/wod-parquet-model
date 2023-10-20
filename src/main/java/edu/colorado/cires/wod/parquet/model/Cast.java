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


/**
 * Parquet object model representing a WOD cast: A set of profiles (or a single profile) taken concurrently.
 * This represents the root of the WOD Parquet schema.
 */
public class Cast implements Serializable {

  private static final long serialVersionUID = 0L;

  /**
   * Returns an Apache Spark {@link StructType} representing the root of the WOD Parquet schema.
   *
   * @return a {@link StructType} representing the root of the WOD Parquet schema
   */
  public static StructType structType() {
    return new StructType(new StructField[]{
        new StructField("dataset", DataTypes.StringType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("castNumber", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("country", DataTypes.StringType, false, org.apache.spark.sql.types.Metadata.empty()),
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
        new StructField("variables", DataTypes.createArrayType(Variable.structType(), false), false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("principalInvestigators", DataTypes.createArrayType(PrincipalInvestigator.structType(), false), false,
            org.apache.spark.sql.types.Metadata.empty()),
        new StructField("attributes", DataTypes.createArrayType(Attribute.structType(), false), false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("biologicalAttributes", DataTypes.createArrayType(Attribute.structType(), false), false,
            org.apache.spark.sql.types.Metadata.empty()),
        new StructField("taxonomicDatasets", DataTypes.createArrayType(TaxonomicDataset.structType(), false), false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("depths", DataTypes.createArrayType(Depth.structType(), false), false, org.apache.spark.sql.types.Metadata.empty())
    });
  }

  /**
   * This can be represented as a generic Spark {@link Row}
   *
   * @return this class represented as a generic Spark {@link Row}
   */
  public Row asRow() {
    return new GenericRowWithSchema(new Object[]{
        dataset,
        castNumber,
        country,
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
  private String country;
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

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public Cast() {
  }

  private Cast(@Nonnull String dataset, int castNumber, String country, Integer cruiseNumber, String originatorsCruise, long timestamp, int year, int month, int day, double time, double longitude, double latitude,
      int profileType, String originatorsStationCode,  @Nonnull String geohash,  @Nonnull List<Variable> variables,  @Nonnull List<PrincipalInvestigator> principalInvestigators,
      @Nonnull List<Attribute> attributes,  @Nonnull List<Attribute> biologicalAttributes,  @Nonnull List<TaxonomicDataset> taxonomicDatasets,  @Nonnull List<Depth> depths) {
    this.dataset = dataset;
    this.castNumber = castNumber;
    this.country = country;
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

  /**
   * Returns a WOD dataset: a data collection from similar instruments with similar resolution.
   * Common Values:
   * OSD - Ocean Station Data, Low-resolution CTD/XCTD, Plankton data
   * CTD - High-resolution Conductivity-Temperature-Depth / XCTD data
   * MBT - Mechanical / Digital / Micro Bathythermograph data
   * XBT - Expendable Bathythermograph data
   * SUR - Surface-only data
   * APB - Autonomous Pinniped data
   * MRB - Moored buoy data
   * PFL - Profiling float data
   * DRB - Drifting buoy data
   * UOR - Undulating Oceanographic Recorder data
   * GLD - Glider data
   *
   * @return usually a three-letter code representing a WOD dataset
   */
  @Nonnull
  public String getDataset() {
    return dataset;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setDataset(String dataset) {
    this.dataset = dataset;
  }

  /**
   * Each cast in the WOD18 is assigned a unique cast number.
   *
   * @return the unique identifier for this cast
   */
  public int getCastNumber() {
    return castNumber;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setCastNumber(int castNumber) {
    this.castNumber = castNumber;
  }

  /**
   * A two-character code assigned to each country. Each code is unique to a country and is assigned by NCEI.
   * Common Values:
   * DE GERMANY
   * DU EAST GERMANY
   * AR ARGENTINA
   * AU AUSTRALIA
   * AT AUSTRIA
   * BE BELGIUM
   * BR BRAZIL
   * BG BULGARIA
   * CA CANADA
   * CL CHILE
   * TW TAIWAN
   * CO COLOMBIA
   * KR KOREA; REPUBLIC OF
   * DK DENMARK
   * EG EGYPT
   * EC ECUADOR
   * ES SPAIN
   * US UNITED STATES
   * FI FINLAND
   * FR FRANCE
   * GR GREECE
   * IN INDIA
   * ID INDONESIA
   * IE IRELAND
   * IS ICELAND
   * IL ISRAEL
   * IT ITALY
   * JP JAPAN
   * LB LEBANON
   * LR LIBERIA
   * MG MADAGASCAR
   * MA MOROCCO
   * MX MEXICO
   * NO NORWAY
   * NC NEW CALEDONIA
   * NZ NEW ZEALAND
   * PK PAKISTAN
   * NL NETHERLANDS
   * PE PERU
   * PH PHILIPPINES
   * PL POLAND
   * PT PORTUGAL
   * RO ROMANIA
   * GB GREAT BRITAIN
   * CN CHINA
   * SE SWEDEN
   * TH THAILAND
   * TN TUNISIA
   * TR TURKEY
   * SU SOVIET UNION
   * ZA SOUTH AFRICA
   * UY URUGUAY
   * VE VENEZUELA
   * YU YUGOSLAVIA
   * 99 UNKNOWN
   * AG ANTIGUA
   * DZ ALGERIA
   * AO ANGOLA
   * BB BARBADOS
   * BS BAHAMAS
   * CR COSTA RICA
   * CU CUBA
   * CY CYPRUS
   * EE ESTONIA
   * FJ FIJI
   * GH GHANA
   * HN HONDURAS
   * HK HONG KONG
   * CI COTE D'IVOIRE
   * KW KUWAIT
   * LV LATVIA
   * LT LITHUANIA
   * MU MAURITIUS
   * MT MALTA
   * MC MONACO
   * MY MALAYSIA
   * MR MAURITANIA
   * NG NIGERIA
   * PA PANAMA
   * CD CONGO; THE DEMOCRATIC REPUBLIC OF THE
   * RU RUSSIAN FEDERATION
   * SA SAUDI ARABIA
   * SC SEYCHELLES
   * SN SENEGAL
   * SG SINGAPORE
   * SL SIERRA LEONE
   * VC SAINT VINCENT AND THEN GRENADINES
   * TO TONGA
   * TT TRINIDAD AND TOBAGO
   * UA UKRAINE
   * WS SAMOA; WESTERN
   * YE YEMEN
   * ZZ MISCELLANEOUS ORGANIZATION
   * MH MARSHALL ISLANDS
   * HR CROATIA
   * EU EUROPEAN UNION
   *
   * @return a two-character code representing a country
   */
  public String getCountry() {
    return country;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * @return
   */
  public Integer getCruiseNumber() {
    return cruiseNumber;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setCruiseNumber(Integer cruiseNumber) {
    this.cruiseNumber = cruiseNumber;
  }

  /**
   *
   * @return
   */
  public String getOriginatorsCruise() {
    return originatorsCruise;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setOriginatorsCruise(String originatorsCruise) {
    this.originatorsCruise = originatorsCruise;
  }

  /**
   *
   * @return
   */
  public long getTimestamp() {
    return timestamp;
  }

  /**
   *
   * @param timestamp
   */
  @Deprecated
  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  /**
   *
   * @return
   */
  public int getYear() {
    return year;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setYear(int year) {
    this.year = year;
  }

  /**
   *
   * @return
   */
  public int getMonth() {
    return month;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setMonth(int month) {
    this.month = month;
  }

  /**
   *
   * @return
   */
  public int getDay() {
    return day;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setDay(int day) {
    this.day = day;
  }

  /**
   *
   * @return
   */
  public double getTime() {
    return time;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setTime(double time) {
    this.time = time;
  }

  /**
   *
   * @return
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  /**
   *
   * @return
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  /**
   *
   * @return
   */
  public int getProfileType() {
    return profileType;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setProfileType(int profileType) {
    this.profileType = profileType;
  }

  /**
   *
   * @return
   */
  public String getOriginatorsStationCode() {
    return originatorsStationCode;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setOriginatorsStationCode(String originatorsStationCode) {
    this.originatorsStationCode = originatorsStationCode;
  }

  /**
   *
   * @return
   */
  @Nonnull
  public String getGeohash() {
    return geohash;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setGeohash(String geohash) {
    this.geohash = geohash;
  }

  /**
   *
   * @return
   */
  @Nonnull
  public List<Variable> getVariables() {
    return variables;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setVariables(List<Variable> variables) {
    if (variables == null) {
      variables = new ArrayList<>(0);
    }
    this.variables = new ArrayList<>(variables);
  }

  /**
   *
   * @return
   */
  @Nonnull
  public List<PrincipalInvestigator> getPrincipalInvestigators() {
    return principalInvestigators;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setPrincipalInvestigators(List<PrincipalInvestigator> principalInvestigators) {
    if (principalInvestigators == null) {
      principalInvestigators = new ArrayList<>(0);
    }
    this.principalInvestigators = new ArrayList<>(principalInvestigators);
  }

  /**
   *
   * @return
   */
  @Nonnull
  public List<Attribute> getAttributes() {
    return attributes;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setAttributes(List<Attribute> attributes) {
    if (attributes == null) {
      attributes = new ArrayList<>(0);
    }
    this.attributes = new ArrayList<>(attributes);
  }

  /**
   *
   * @return
   */
  @Nonnull
  public List<Attribute> getBiologicalAttributes() {
    return biologicalAttributes;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setBiologicalAttributes(List<Attribute> biologicalAttributes) {
    if (biologicalAttributes == null) {
      biologicalAttributes = new ArrayList<>(0);
    }
    this.biologicalAttributes = new ArrayList<>(biologicalAttributes);
  }

  /**
   *
   * @return
   */
  @Nonnull
  public List<TaxonomicDataset> getTaxonomicDatasets() {
    return taxonomicDatasets;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
  @Deprecated
  public void setTaxonomicDatasets(List<TaxonomicDataset> taxonomicDatasets) {
    if (taxonomicDatasets == null) {
      taxonomicDatasets = new ArrayList<>(0);
    }
    this.taxonomicDatasets = taxonomicDatasets;
  }

  /**
   *
   * @return
   */
  @Nonnull
  public List<Depth> getDepths() {
    return depths;
  }

  /**
   * DO NOT USE
   *
   * This is only public in order to allow for Spark conversion.
   *
   * @deprecated use {@link  #builder()}
   */
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
        && Objects.equals(taxonomicDatasets, cast.taxonomicDatasets) && Objects.equals(depths, cast.depths)
        && Objects.equals(country, cast.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataset, castNumber, country, cruiseNumber, originatorsCruise, timestamp, year, month, day, time, longitude, latitude, profileType,
        originatorsStationCode, geohash, variables, principalInvestigators, attributes, biologicalAttributes, taxonomicDatasets, depths);
  }

  @Override
  public String toString() {
    return "Cast{" +
        "dataset='" + dataset + '\'' +
        ", castNumber=" + castNumber +
        ", country='" + country + '\'' +
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

  /**
   *
   * @return
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   *
   * @param source
   * @return
   */
  public static Builder builder(Cast source) {
    return new Builder(source);
  }

  /**
   *
   * @param row
   * @return
   */
  public static Builder builder(Row row) {
    return new Builder(row);
  }

  /**
   *
   */
  public static class Builder {

    private String dataset;
    private int castNumber;
    private String country;
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
      country = source.getCountry();
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
      country = row.getAs("country");
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

    /**
     *
     * @param dataset
     * @return
     */
    public Builder withDataset(String dataset) {
      this.dataset = dataset;
      return this;
    }

    /**
     *
     * @param castNumber
     * @return
     */
    public Builder withCastNumber(int castNumber) {
      this.castNumber = castNumber;
      return this;
    }

    /**
     *
     * @param cruiseNumber
     * @return
     */
    public Builder withCruiseNumber(Integer cruiseNumber) {
      this.cruiseNumber = cruiseNumber;
      return this;
    }

    /**
     *
     * @param country
     * @return
     */
    public  Builder withCountry(String country){
      this.country = country;
      return this;
    }

    /**
     *
     * @param originatorsCruise
     * @return
     */
    public Builder withOriginatorsCruise(String originatorsCruise) {
      this.originatorsCruise = originatorsCruise;
      return this;
    }

    /**
     *
     * @param timestamp
     * @return
     */
    public Builder withTimestamp(long timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     *
     * @param year
     * @return
     */
    public Builder withYear(int year) {
      this.year = year;
      return this;
    }

    /**
     *
     * @param month
     * @return
     */
    public Builder withMonth(int month) {
      this.month = month;
      return this;
    }

    /**
     *
     * @param day
     * @return
     */
    public Builder withDay(int day) {
      this.day = day;
      return this;
    }

    /**
     *
     * @param time
     * @return
     */
    public Builder withTime(double time) {
      this.time = time;
      return this;
    }

    /**
     *
     * @param longitude
     * @return
     */
    public Builder withLongitude(double longitude) {
      this.longitude = longitude;
      return this;
    }

    /**
     *
     * @param latitude
     * @return
     */
    public Builder withLatitude(double latitude) {
      this.latitude = latitude;
      return this;
    }

    /**
     *
     * @param profileType
     * @return
     */
    public Builder withProfileType(int profileType) {
      this.profileType = profileType;
      return this;
    }

    /**
     *
     * @param originatorsStationCode
     * @return
     */
    public Builder withOriginatorsStationCode(String originatorsStationCode) {
      this.originatorsStationCode = originatorsStationCode;
      return this;
    }

    /**
     *
     * @param geohash
     * @return
     */
    public Builder withGeohash(String geohash) {
      this.geohash = geohash;
      return this;
    }

    /**
     *
     * @param variables
     * @return
     */
    public Builder withVariables(List<Variable> variables) {
      this.variables = variables == null ? new ArrayList<>(0) : new ArrayList<>(variables);
      return this;
    }

    /**
     *
     * @param principalInvestigators
     * @return
     */
    public Builder withPrincipalInvestigators(List<PrincipalInvestigator> principalInvestigators) {
      this.principalInvestigators = principalInvestigators == null ? new ArrayList<>(0) : new ArrayList<>(principalInvestigators);
      return this;
    }

    /**
     *
     * @param attributes
     * @return
     */
    public Builder withAttributes(List<Attribute> attributes) {
      this.attributes = attributes == null ? new ArrayList<>(0) : new ArrayList<>(attributes);
      return this;
    }

    /**
     *
     * @param biologicalAttributes
     * @return
     */
    public Builder withBiologicalAttributes(List<Attribute> biologicalAttributes) {
      this.biologicalAttributes = biologicalAttributes == null ? new ArrayList<>(0) : new ArrayList<>(biologicalAttributes);
      return this;
    }

    /**
     *
     * @param taxonomicDatasets
     * @return
     */
    public Builder withTaxonomicDatasets(List<TaxonomicDataset> taxonomicDatasets) {
      this.taxonomicDatasets = taxonomicDatasets == null ? new ArrayList<>(0) : new ArrayList<>(taxonomicDatasets);
      return this;
    }

    /**
     *
     * @param depths
     * @return
     */
    public Builder withDepths(List<Depth> depths) {
      this.depths = depths == null ? new ArrayList<>(0) : new ArrayList<>(depths);
      return this;
    }

    /**
     *
     * @return
     */
    public Cast build() {
      return new Cast(
          dataset,
          castNumber,
          country,
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










