package edu.colorado.cires.wod.parquet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
        new StructField("originatorsCruise", DataTypes.StringType, true, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("timestamp", DataTypes.LongType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("year", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("month", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("day", DataTypes.IntegerType, false, org.apache.spark.sql.types.Metadata.empty()),
        new StructField("time", DataTypes.DoubleType, true, org.apache.spark.sql.types.Metadata.empty()),
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
  private int cruiseNumber;
  private String originatorsCruise;
  private long timestamp;
  private int year;
  private int month;
  private int day;
  private Double time;
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

  private Cast(@Nonnull String dataset, int castNumber, @Nullable String country, int cruiseNumber, @Nullable String originatorsCruise, long timestamp, int year, int month, int day, @Nullable Double time, double longitude, double latitude,
      int profileType, @Nullable String originatorsStationCode,  @Nonnull String geohash,  @Nonnull List<Variable> variables,  @Nonnull List<PrincipalInvestigator> principalInvestigators,
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
   * In WOD18, a cruise identifier consists of two parts, the ISO 3166-1 country code and the unique cruise number.
   * The unique cruise number is only unique with respect to the country code. For data for which there is no way to
   * identify a specific cruise, a cruise number of zero (0) is used.
   *
   * @return the unique cruise number per country code
   */
  @Nullable
  public int getCruiseNumber() {
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
  public void setCruiseNumber(int cruiseNumber) {
    this.cruiseNumber = cruiseNumber;
  }

  /**
   * The alphanumeric cruise identification provided by the originator. If the originator’s code is purely numeric, it will be found in the attributes
   * with code 7.
   *
   * @return the alphanumeric cruise identification provided by the originator or null if not provided
   */
  @Nullable
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
   * A representative timestamp of when the cast data were recorded as the number of milliseconds from 1970/1/1 UTC.
   *
   *
   * If the day value was not recorded, this timestamp assumes the first day of the month. Likewise, if a time value
   * was not recorded, this timestamp assumes midnight UTC.
   *
   * @return timestamp of when the cast data was recorded as the number of milliseconds from 1970/1/1 UTC
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
   * The year the cast data were recorded.
   *
   * @return the year the cast data were recorded
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
   * The month the cast data were recorded, 1-12.
   *
   * @return the month the cast data were recorded, 1-12
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
   * The day the cast data were recorded, 0-31.
   *
   *
   * Please note that some data have been submitted with a day of zero (0) and we have kept these in the database as such.
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
   * A floating point representation of the time the data were recorded as 24 based hours with fractional hours.
   *
   * Ex.  13:30 clock time = 13.5 in this representation
   *
   * @return the time the data were recorded as 24 based hours with fractional hours or null if not available
   */
  @Nullable
  public Double getTime() {
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
  public void setTime(Double time) {
    this.time = time;
  }

  /**
   * E / W degrees where the cast was recorded. -180.0 - 180.0
   *
   * @return E / W degrees where the cast was recorded
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
   * N / S degrees where the cast was recorded. -90.0 - 90.0
   *
   * @return N / S degrees where the cast was recorded
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
   * A flag indicating if the cast contains profiles at the observed depths or at standard depths.
   * Allowed Values:
   * 0 = Observed depths
   * 1 = Standard depths: A depth below the sea surface at which water properties should be measured and reported, either directly or by
   * interpolation, according to the proposal by the International Association of Physical Oceanography in 1936.
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
   * The alphanumeric station identification provided by the originator. If the originator’s code is purely numeric, it
   * will be found in the attributes with code 7.
   *
   * @return the alphanumeric station identification provided by the originator or null
   */
  @Nullable
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
   * A three-character <a href="https://en.wikipedia.org/wiki/Geohash">geohash</a> derived from the cast's longitude
   * and latitude.  This can be used to spatially group casts.
   *
   * @return a three-character geohash where this cast was recorded
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
   * The {@link Variable}s associated with the cast. These elements contain metadata information specific to each individual measured variable in the
   * profiles, such as originator’s units, scales, and methods.
   *
   *
   * Common Codes:
   * 1 NCEI accession number: unique number assigned by NCEI to each batch of data received. Sometimes the variables for a cast are received at different times or from different sources and therefore may have different accession numbers. We have attempted to merge these casts together and kept the source information intact
   * 2 Project: identifies the research project associated with the data collection.
   * 3 Scale: The units for temperature and salinity are based on the internationally agreed referenced measurement standards (i.e. ITS Temperature Scale, Practical Salinity Scale, and pH scales). Table 3 provides the detailed list of variables and units
   * 4 Institution: identifies institution associated with the investigator who sampled the specific variable
   * 5 Instrument
   * 6 Methods
   * 8 Originator’s units
   * 10 Equilibrator type: describes the design of the instrument used for equilibrating seawater with air in preparation for measuring CO2 concentrations
   * 11 Filter type and size
   * 12 Incubation time: 25 is dawn to noon, 26 is noon to dusk; otherwise, value is in hours
   * 13 CO2 sea warming: temperature change in transporting water from the sea surface to the CO2 analysis site
   * 15 Analysis temperature: temperature of seawater at the time of CO2 analysis
   * 16 Uncalibrated: set to 1 if instrument is uncalibrated
   * 17 Contains nitrite: set to 1 if nitrate value is actually nitrate+nitrite
   * 18 Normal Standard Seawater batch: the code gives the IAPSO normal standard seawater batch number, P-Series, i.e. code 78 means normal standard seawater batch P78
   * 19 Adjustment: this is an adjustment (correction) value made to Argo profiling floats. The adjustment is a real value (i.e. decimal number) and is the mean difference between original (real-time) and adjusted (delayed-mode) profile of temperature, salinity, oxygen, or pressure for all values below 500 meters depth. If a profile has an adjustment value (even if this value is 0.0, it indicates that the profile has gone through additional quality control by the Argo project and is considered either adjusted real-time or delayed-mode data
   *
   * @return a list of metadata information specific to each individual measured variable
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
   * The {@link PrincipalInvestigator}s associated with the cast, lead scientists or engineers for a particular research cruise or project.
   *
   * @return a list of lead scientists or engineers for a particular research cruise or project
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
   * Additional information about the cast, such as meteorological data, sea floor depth, instrument, ship (platform), institute, and project.
   *
   *
   * Common Codes:
   * 1 - NCEI accession number: a unique number assigned by NCEI to each group of data received in the NCEI Ocean Archive
   * 2 - NCEI project: identifies the project associated with the data
   * 3 - Platform: identifies the platform associated with the data
   * 4 - Institution: code identifies the institution which sampled the data
   * 5 - Cast/Tow Number: sequential number representing each over-the-side operation or discrete sampling at a cast or continuous tow
   * 7 - Originator’s station number: numeric station number assigned by the data submitter or data originator
   * 8 - Depth Precision: precision of the depth field (number of digits to the right of the decimal)
   * 9 - Ocean Weather Station: identifies data from the various ocean weather stations
   * 10 - Bottom depth: depth from water surface to sediment-water interface, in meters;
   * 11 - Cast duration: duration of the cast, in hours
   * 12 - Cast Direction: if a direction is not present, down is assumed, description of codes found in
   * 13 - High-resolution pairs: unique cast number identifying where high-resolution CTD and low-resolution OSD data are both available
   * 14 - Water Color: a modified Forel-Ule color scale is used
   * 15 - Water transparency: Secchi disk visibility depth, in meters
   * 16 - Wave Direction (WMO 0877)
   * 17 - Wave Height (WMO 1555)
   * 18 - Sea State (WMO 3700)
   * 19 - Wind Force (Beaufort Scale)
   * 20 - Wave Period (WMO 3155 or NCEI 0378)
   * 21 - Wind Direction (WMO 0877)
   * 22 - Wind speed: surface or near-surface wind speed, in knots
   * 23 - Barometric pressure: the atmospheric pressure at sea level due to the gravitational force on the column of air above it (millibar)
   * 24 - Dry bulb temperature: identical to air temperature, in °C
   * 25 - Wet bulb temperature: the temperature a parcel of air would have if it were cooled adiabatically with no heat transfer, in °C
   * 26 - Weather Condition (WMO 4501 and WMO 4677)
   * 27 - Cloud Type (WMO 0500)
   * 28 - Cloud Cover (WMO 2700)
   * 29 - Probe Type
   * 30 - Calibration Depth: deviation on a bathythermograph (BT) from the zero depth. This difference between points was used to adjust the profile when it was digitized
   * 31 - Calibration Temperature: deviation on a BT from a 16.7°C reference point. This difference between points was used to adjust the profile when it was digitized
   * 32 - Recorder Type (WMO 4770)
   * 33 - Depth Correction: a zero (0) is assigned if the original depth-time equation was used for the XBT data collected after a corrected depth-time equation was introduced; a one (1) is assigned if a corrected depth-time equation was used
   * 34 - Bottom Hit: a one (1) is assigned if the probe hits the bottom
   * 35 - Digitization Method (NCEI 0612)
   * 36 - Digitization Interval (NCEI 0613)
   * 37 - Data Treatment and Storage (NCEI 0614)
   * 38 - Trace Correction: average difference between the surface trace and the surface depth line of the grid for a BT
   * 39 - Temperature Correction (°C): correction for difference between reference temperature and BT reading or correction to the original data by the submitter – in some cases the correction has already been applied
   * 40 - Instrument for Reference Temperature (NCEI 0615)
   * 41 - Horizontal Visibility (WMO 4300)
   * 45 - Absolute Humidity (g·m-3): sometimes referred to as the vapor density, - the ratio of the mass of water vapor present to the volume occupied by the moist air mixture present in the atmosphere
   * 46 - Reference/Sea Surface Temperature: temperature used to check the probe or a separate measure of sea surface temperature
   * 47 - Sea Surface Salinity of the layer of sea water nearest to the atmosphere
   * 48 - Year: in which probe was manufactured
   * 49 - Speed: ship speed (knots) when probe was dropped
   * 54 - Depth Fix: equation needed to calculate correct depth
   * 71 - Real-time: identifies data received over the WMO Global Telecommunication System within 24 hours of measurement. Real-time data is identified with the number one (1)
   * 72 - XBT Wait: is the time difference between the launch of the probe and the time it begins recording data (NB: this code is no longer used)
   * 73 - XBT Frequency: is the sampling rate of the recorder (NB: this code is no longer used)
   * 74 - Oceanographic Measuring Vehicle
   * 77 - xCO2 in atmosphere (ppm): mole fraction of CO2 in dry gas sample
   * 84 - ARGOS Fix Code: ARGOS satellite fix and location accuracy
   * 85 - ARGOS time (hours) from last fix: used to calculate position of APB
   * 86 - ARGOS time (hours) to next fix: used to calculate position of APB
   * 87 - Height (meters) of XBT launcher
   * 88 - Depth of sea surface sensor (meters)
   * 91 - Database ID: Identifies source of data
   * 92 - UKHO Bibliographic Reference number: source for digitized cards from the United Kingdom Hydrographic Office (vessels, institutes, sea area)
   * 93 - Consecutive profile in tow segment: used to identify one up or down half-cycle in underway data
   * 94 - WMO Identification code: code assigned to buoys or profiling floats by WMO
   * 95 - Originator’s Depth Unit: units used by the data originator to report depth values. If code is absent, depths were reported in meters
   * 96 - Originator’s Flags: These flags are assigned only to the observed depth data. If this code is absent, there are no originator’s flags.
   * 97 - Water Sampler: devices used to capture water sample (bucket, specific bottle type
   * 98 - ARGOS ID number: assigned by the ARGOS project office
   * 99 - Time Stamp: in format YYYYJJJ (where YYYY=year, JJJ=Julian year day) time- stamp when the ASCII version of a cast was created.
   *
   * @return a list of {@link Attribute}s with additional information about the cast
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
   * Information necessary to understand how biological data were sampled. “Biological” data are defined as plankton biomass (weights or volumes) and taxa-specific observations.
   *
   * Common Codes:
   * 1 - Water volume filtered: total volume of water filtered by the sampling gear (m3)
   * 2 - Sampling duration: time over which the sampling gear was towed, in minutes
   * 3 - Mesh size: pore size of the sampling device, in micrometers
   * 4 - Type of tow: towing method used (e.g., horizontal, vertical, oblique)
   * 5 - Large removed volume: the minimum volume criteria for removing large plankters, in ml, see also code 12
   * 6 - Large plankters removed: if large plankters were specified as being removed (1) or not removed (2), this code is added
   * 7 - Gear code: type of gear used (e.g., plankton net, bottle, MOCNESS)
   * 8 - Sampler volume: internal volume of the sampling gear (e.g., Niskin bottle), in liters
   * 9 - Net mouth area: mouth or opening area of the sampling gear, in m2. If mouth diameter was provided, area was calculated as: area = π (0.5 diameter)^2
   * 10 - Preservative: type of preservative used to preserve the plankton sample
   * 11 - Weight method: method used for weighing the plankton sample
   * 12 - Large removed length: the minimum size/length criteria for removing large plankters, in cm, see also code 5
   * 13 - Count method: method used for counting the plankton sample
   * 14 - Tow distance: distance over which sampling gear was towed, in meters
   * 15 - Average tow speed: average speed used to tow the sampling gear, in knots
   * 16 - Sampling start time: GMT
   * 18 - Flowmeter type: the brand and/or model of the flowmeter used
   * 19 - Flowmeter calibration: the calibration frequency for the flowmeter
   * 20 - Counting Institution: the Institution responsible for identifying and counting the taxa-specific sample
   * 21 - Voucher Institution: the location (Institution) of the taxa-specific sample voucher
   * 22 - Wire angle start: wire angle of the towing apparatus at sampling start, in degrees
   * 23 - Wire angle end: wire angle of the towing apparatus at sampling end, in degrees
   * 24 - Depth determination method: a code indicating that depth was calculated from wire angle and length or a PI-specific “target depth”
   * 25 - Volume method: the method used for measuring the volume of the plankton sample
   * 30 - Accession number for biology: NCEI dataset identification for the biological component of the current cast
   *
   * @return a list of {@link Attribute}s with biological information
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
   * The typical plankton cast, as represented in WOD18, stores taxon specific and/or biomass data in individual sets of unique observations, called
   * “Taxa-Record”. Each “Taxa-Record” contains a taxonomic description, depth range (the upper and lower depth) of observation,
   * the original measurements (e.g., abundance, biomass or volume), and all provided qualifiers (e.g., lifestage, sex, size, etc.)
   * required to represent that plankton observation.
   *
   *
   * @return a list of {@link TaxonomicDataset}
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
   * A list of {@link Depth}s representing variable values recorded at different depths on the cast.
   *
   * @return a list of {@link Depth}s representing variable values recorded at different depths on the cast
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
    private int cruiseNumber;
    private String originatorsCruise;
    private long timestamp;
    private int year;
    private int month;
    private int day;
    private Double time;
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
    public Builder withCruiseNumber(int cruiseNumber) {
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
    public Builder withTime(Double time) {
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










