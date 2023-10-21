# NOAA / NCEI World Ocean Database Parquet Format

## DISCLAIMER

This project is still in development and the schema presented is a draft version and subject to change.  Until this
project reaches a major version, 1.0.0 or greater, updates to this schema and library may introduce breaking changes.


## Introduction
This project defines the schema for the Parquet version of the World Ocean Database (https://www.ncei.noaa.gov/products/world-ocean-database).  It 
also provides a Java object model implementation of the schema.  However, the schema itself is language agnostic since the Parquet file 
format is language agnostic.

The first part of this document will describe the schema and the second will describe how to use this library in Java applications.

## WOD Parquet Schema

### Background
The World Ocean Database (WOD) is world's largest collection of uniformly formatted, quality controlled, publicly 
available ocean profile data. It is a powerful tool for oceanographic, climatic, and environmental research, and the end 
result of more than 20 years of coordinated efforts to incorporate data from institutions, agencies, individual 
researchers, and data recovery initiatives into a single database. WOD data spans from Captain Cook's 1772 voyage to the 
contemporary Argo period, making it a valuable resource for long term and historical ocean climate analysis. Original 
versions of the 20,000+ datasets in the WOD are available through the NCEI archives.

More information on the World Ocean Database can be found at https://www.ncei.noaa.gov/products/world-ocean-database.

The World Ocean Database can be represented in several file formats, including ASCII text, NetCDF, binary, and Parquet.
This document defines the Parquet format which was heavily influenced on the commonly used WOD ASCII format. Additional
information on the ASCII format can be found at:
* https://www.ncei.noaa.gov/sites/default/files/2020-04/wod_intro_0.pdf
* https://www.ncei.noaa.gov/sites/default/files/2020-04/wodreadme_0.pdf

### Schema

Below is the overall WOD Parquet schema.  Following documentation will provide details on each field presented here.
```
root
 |-- dataset: string (nullable = false)
 |-- castNumber: integer (nullable = false)
 |-- country: string (nullable = false)
 |-- cruiseNumber: integer (nullable = false)
 |-- originatorsCruise: string (nullable = true)
 |-- timestamp: long (nullable = false)
 |-- year: integer (nullable = false)
 |-- month: integer (nullable = false)
 |-- day: integer (nullable = false)
 |-- time: double (nullable = true)
 |-- longitude: double (nullable = false)
 |-- latitude: double (nullable = false)
 |-- profileType: integer (nullable = false)
 |-- originatorsStationCode: string (nullable = true)
 |-- geohash: string (nullable = false)
 |-- variables: array (nullable = false)
 |    |-- element: struct (containsNull = false)
 |    |    |-- code: integer (nullable = false)
 |    |    |-- metadata: array (nullable = false)
 |    |    |    |-- element: struct (containsNull = false)
 |    |    |    |    |-- code: integer (nullable = false)
 |    |    |    |    |-- value: double (nullable = false)
 |-- principalInvestigators: array (nullable = false)
 |    |-- element: struct (containsNull = false)
 |    |    |-- variableCode: integer (nullable = false)
 |    |    |-- piCode: integer (nullable = false)
 |-- attributes: array (nullable = false)
 |    |-- element: struct (containsNull = false)
 |    |    |-- code: integer (nullable = false)
 |    |    |-- value: double (nullable = false)
 |-- biologicalAttributes: array (nullable = false)
 |    |-- element: struct (containsNull = false)
 |    |    |-- code: integer (nullable = false)
 |    |    |-- value: double (nullable = false)
 |-- taxonomicDatasets: array (nullable = false)
 |    |-- element: struct (containsNull = false)
 |    |    |-- values: array (nullable = false)
 |    |    |    |-- element: struct (containsNull = false)
 |    |    |    |    |-- code: integer (nullable = false)
 |    |    |    |    |-- value: double (nullable = false)
 |    |    |    |    |-- qcFlag: integer (nullable = false)
 |    |    |    |    |-- originatorsFlag: integer (nullable = false)
 |-- depths: array (nullable = false)
 |    |-- element: struct (containsNull = false)
 |    |    |-- depth: double (nullable = false)
 |    |    |-- depthErrorFlag: integer (nullable = false)
 |    |    |-- originatorsFlag: integer (nullable = false)
 |    |    |-- data: array (nullable = false)
 |    |    |    |-- element: struct (containsNull = false)
 |    |    |    |    |-- variableCode: integer (nullable = false)
 |    |    |    |    |-- value: double (nullable = false)
 |    |    |    |    |-- qcFlag: integer (nullable = false)
 |    |    |    |    |-- originatorsFlag: integer (nullable = false)
```

### Cast (root)

A set of profiles (or a single profile) taken concurrently.

#### dataset: string (nullable = false)
A three-character code representing a data collection from similar instruments with similar resolution.

Common Values (see WOD documentation for updates)
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


#### castNumber: integer (nullable = false)
Each cast in the WOD18 is assigned a unique cast number.


#### country: string (nullable = false)
A two-character code assigned to each country. Each code is unique to a country and is assigned by NCEI.

Common Values (see WOD documentation for updates)
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

#### cruiseNumber: integer (nullable = false)
In WOD18, a cruise identifier consists of two parts, the ISO 3166-1 country code and the unique cruise number. The 
unique cruise number is only unique with respect to the country code. For data for which there is no way to 
identify a specific cruise, a cruise number of zero (0) is used.

#### originatorsCruise: string (nullable = true)
The alphanumeric cruise identification provided by the originator. 
If the originator’s code is purely numeric, it will be found in the [cast attributes](#attributes-array-nullable--false) with code 7.

#### timestamp: long (nullable = false)
A representative timestamp of when the cast data were recorded as the number of milliseconds from 1970/1/1 UTC. If the 
day value was not recorded, this timestamp assumes the first day of the month. Likewise, if a time value
was not recorded, this timestamp assumes midnight UTC.

#### year: integer (nullable = false)
The year the cast data were recorded.

#### month: integer (nullable = false)
The month the cast data were recorded. Allowed values are 1-12.

#### day: integer (nullable = false)
The day the cast data were recorded. Please note that some data have been submitted with a day of zero (0) and we have 
kept these in the database as such.  Allowed values are 0-31.

#### time: double (nullable = true)
A floating point representation of the time the data were recorded as 24 based hours with fractional hours.

Ex.  13:30 clock time = 13.5 in this representation

#### longitude: double (nullable = false)
E / W degrees where the cast was recorded. Allowed values are -180.0 - 180.0.

#### latitude: double (nullable = false)
N / S degrees where the cast was recorded. Allowed values are -90.0 - 90.0.

#### profileType: integer (nullable = false)
A flag indicating if the cast contains profiles at the observed depths or at standard depths.
Allowed Values:
* 0 = Observed depths
* 1 = Standard depths
* * A depth below the sea surface at which water properties should be measured and reported, either directly or by
interpolation, according to the proposal by the International Association of Physical Oceanography in 1936.

#### originatorsStationCode: string (nullable = true)
The alphanumeric station identification provided by the originator.
If the originator’s code is purely numeric, it will be found in the [cast attributes](#attributes-array-nullable--false) with code 7.


#### geohash: string (nullable = false)
A three-character [geohash](href="https://en.wikipedia.org/wiki/Geohash) derived from the cast's longitude and latitude.  This can be used to spatially group casts.

#### variables: array (nullable = false)
An array of elements containing metadata information specific to each individual measured variable in the profiles, such as originator’s units, scales, and methods.
See [Depth Variable](#depth-variable-root---variables) for details about each element.

#### principalInvestigators: array (nullable = false)
An array of elements containing lead scientists or engineers for a particular research cruise or project.
See [Principal Investigator](#principal-investigator-root---principalinvestigators) for details about each element.

#### attributes: array (nullable = false)
An array of elements containing additional information about the cast, such as meteorological data, sea floor depth, instrument, ship (platform), institute, and project.
See [Cast Attribute](#cast-attribute-root---attributes) for details about each element.

#### biologicalAttributes: array (nullable = false)
An array of elements containing information necessary to understand how biological data were sampled. “Biological” data 
are defined as plankton biomass (weights or volumes) and taxa-specific observations.
See [Biological Attribute](#biological-attribute-root---biologicalattributes) for details about each element.

#### taxonomicDatasets: array (nullable = false)
An array of elements containing taxonomic datasets. 
See [Taxonomic Datasets](#taxonomic-dataset-root---taxonomicdatasets) for details about each element.

#### depths: array (nullable = false)
An array of elements representing variable values recorded at different depths on the cast.
See [Depth](#depth-root---depths) for details about each element.


### Depth Variable (root -> variables)
A depth variable contains metadata information specific to each individual measured variable in the
profiles, such as originator’s units, scales, and methods.

#### code: integer (nullable = false)
Common Codes (see WOD documentation for updates):
* 1 Temperature in Degrees Celsius (°C)
* 2 Salinity in Dimensionless (unitless)
* 3 Oxygen in Micromole per kilogram (μmol kg^-1)
* 4 Phosphate in Micromole per kilogram (μmol kg^-1)
* 6 Silicate in Micromole per kilogram (μmol kg^-1)
* 8 Nitrate and Nitrate+Nitrite in Micromole per kilogram (μmol kg^-1)
* 9 pH in Dimensionless
* 11 Total Chlorophyll \[Chl\] unless specified in Microgram per liter (μg l^-1)
* 17 Alkalinity in Milli-equivalent per liter (meq l^-1)
* 20 Partial pressure of carbon dioxide \[pCO2\] in Microatmosphere (μatm)
* 21 Dissolved Inorganic carbon in Millimole per liter (mmol l^-1)
* 24 Transmissivity (Beam Attenuation Coefficient) in Per meter (m^-1)
* 25 Water pressure in Decibar
* 26 Air temperature in Degree Celsius (°C)
* 27 CO2warming in Degree Celsius (°C)
* 28 xCO2atmosphere in Parts per million (ppm)
* 29 Air pressure in Millibar (mbar)
* 30 Latitude in Degrees
* 31 Longitude in Degrees
* 32 Julian year-day in Day
* 33 Tritium \[3H\] in Tritium Unit (TU)
* 34 Helium \[He\] in Nanomol per kilogram (nmol kg^-1)
* 35 Delta Helium-3 \[∆3He\] in Percent (%)
* 36 Delta Carbon-14 \[∆14C\] in Per mille (‰)
* 37 Delta Carbon-13 \[∆13C\] in Per mille (‰)
* 38 Argon \[Ar\] in Nanomol per kilogram (nmol kg^-1)
* 39 Neon \[Ne\] in Nanomol per kilogram (nmol kg^-1)
* 40 Chlorofluorocarbon 11 in Picomole per kilogram (pmol kg^-1)
* 41 Chlorofluorocarbon 12 in Picomole per kilogram (pmol kg^-1)
* 42 Chlorofluorocarbon 113 in Picomole per kilogram (pmol kg^-1)
* 43 Delta Oxygen-18 \[∆18O\] in Per mille (‰)

#### metadata: array (nullable = false)
An array of elements representing variable metadata.
See [Variable Metadata](#variable-metadata-root---variables---metadata) for details about each element.

### Variable Metadata (root -> variables -> metadata)
These elements contain metadata information specific to each individual measured variable in the 
profiles, such as originator’s units, scales, and methods.

#### code: integer (nullable = false)
Common Codes (see WOD documentation for updates):
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

#### value: double (nullable = false)
Value of the variable metadata.

### Principal Investigator (root -> principalInvestigators)
The Principal Investigator (PI) is also identified by numeric code and by variable code. The PI is the person (or persons), 
responsible for data collection and this information is included whenever available. A list of the numeric codes associated 
with each PI can be found in the file: primary_investigator_list.pdf. For the purpose of assigning PI codes, plankton 
data are identified as variable 14 for all plankton, -5002 for zooplankton, and -5006 for phytoplankton.

#### variableCode: integer (nullable = false)
A [Depth Variable Code](#code-integer-nullable--false)

#### piCode: integer (nullable = false)
NCEI principal investigator code.  See WOD documentation for values.

### Cast Attribute (root -> attributes)
Additional information about the cast, such as meteorological data, sea floor depth, instrument, ship (platform), institute, and project.

#### code: integer (nullable = false)
Common Codes (see WOD documentation for updates):
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

#### value: double (nullable = false)
Value for the cast attribute.

### Biological Attribute (root -> biologicalAttributes)
Information necessary to understand how biological data were sampled. “Biological” data are defined as plankton biomass (weights or volumes) and taxa-specific observations.

#### code: integer (nullable = false)
Common Codes (see WOD documentation for updates):
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

#### value: double (nullable = false)
Value for the biological attribute.


### Taxonomic Dataset (root -> taxonomicDatasets)
The typical plankton cast, as represented in WOD18, stores taxon specific and/or biomass data in
individual sets of unique observations, called “Taxa-Record”. Each “Taxa-Record” contains a taxonomic description,
depth range (the upper and lower depth) of observation, the original measurements (e.g., abundance, biomass or volume),
and all provided qualifiers (e.g., lifestage, sex, size, etc.) required to represent that plankton observation.

#### values: array (nullable = false)
An array of elements representing taxonomic dataset values.
See [Taxonomic Dataset Value](#taxonomic-dataset-value-root---taxonomicdatasets---values) for details about each element.

### Taxonomic Dataset Value (root -> taxonomicDatasets -> values)
The typical plankton cast, as represented in WOD18, stores taxon specific and/or biomass data in individual sets of unique observations, called
“Taxa-Record”. Each “Taxa-Record” contains a taxonomic description, depth range (the upper and lower depth) of observation,
the original measurements (e.g., abundance, biomass or volume), and all provided qualifiers (e.g., lifestage, sex, size, etc.)
required to represent that plankton observation.

#### code: integer (nullable = false)
Common Codes (see WOD documentation for updates):
* 1 - Variable number (>0 ITIS taxon code, <0 WOD taxon or group code)
* 2 - Upper depth (meters)
* 3 - Lower depth (meters)
* 4 - Biomass value
* 5 - Taxon lifestage
* 6 - Taxon sex code
* 7 - Taxon present
* 8 - Taxon trophic mode
* 9 - Taxon realm
* 10 - Taxon count (count of taxon/UNIT)
* 11 - Sample-specific sample volume (m^3 or ml/UNIT)
* 12 - Taxon volume (ml or pl/UNIT)
* 13 - Taxon wet weight (g or μg/UNIT)
* 14 - Taxon dry weight (g or μg/UNIT)
* 15 - Taxon ash-free weight (mg or ng/UNIT)
* 16 - Taxon feature
* 17 - Taxon modifier
* 18 - Size min (mm, milli-mter)
* 19 - Size max (mm, milli-mter)
* 20 - Originator’s Unit
* 21 - Taxon radius (μm, micro-meter)
* 22 - Taxon length (μm, micro-meter)
* 23 - Taxon width (μm, micro-meter)
* 25 - Taxon carbon content (mg or ng/UNIT)
* 26 - Count method
* 27 - Common Base-unit Value (CBV)
* 28 - CBV calculation method
* 30 - Plankton Grouping Code (PGC)


#### value: double (nullable = false)
The value for the specified code.

#### qcFlag: integer (nullable = false)
Biological data flags (applied only to Comparable Biological Value - CBV Taxa code 27) 
* 0 - accepted value
* 1 - range outlier ( outside of broad range check )
* 2 - questionable value (“bullseye flag” )
* 3 - group was not reviewed
* 4 - failed annual standard deviation check


#### originatorsFlag: integer (nullable = false)
The error flag specified by the originator.


### Depth (root -> depths)
A container for all profile data at a given depth.

#### depth: double (nullable = false)
Measurement depth in meters.

#### depthErrorFlag: integer (nullable = false)
Common Values (see WOD documentation for updates):
* 0 - accepted value
* 1 - duplicates or inversions in recorded depth ( same or less than previous depth )
* 2 - density inversion

#### originatorsFlag: integer (nullable = false)
Error flag set by the originator.

#### data: array (nullable = false)
An array of elements representing variable values for the given depth.
See [Profile Data](#profile-data-root---depths---data) for details about each element.


### Profile Data (root -> depths -> data)
Contains values for variables at a given depth.

#### variableCode: integer (nullable = false)
A [Depth Variable Code](#code-integer-nullable--false)

#### value: double (nullable = false)
The value of the variable.

#### qcFlag: integer (nullable = false)
This value is different for standard levels and observed levels.

Common Observed Level Flags (see WOD documentation for updates):
* 0 - accepted value
* 1 - range outlier ( outside of broad range check )
* 2 - failed inversion check
* 3 - failed gradient check
* 4 - observed level “bullseye” flag and zero gradient check
* 5 - combined gradient and inversion checks
* 6 - failed range and inversion checks
* 7 - failed range and gradient checks
* 8 - failed range and questionable data checks
* 9 - failed range and combined gradient and inversion checks

Common Standard Level Flags (see WOD documentation for updates):
* 0 - accepted value
* 1 - bullseye marker
* 2 - density inversion
* 3 - failed annual standard deviation check
* 4 - failed seasonal standard deviation check
* 5 - failed monthly standard deviation check
* 6 - failed annual and seasonal standard deviation check
* 7 - failed annual and monthly standard deviation check
* 8 - failed seasonal and monthly standard deviation check
* 9 - failed annual, seasonal and monthly standard deviation check

#### originatorsFlag: integer (nullable = false)
Error flag specified by the originator.



## Java WOD Parquet Model

TODO




Additional project information, javadocs, and test coverage is located at https://ci-cmg.github.io/project-documentation/wod-parquet-model/

## Adding To Your Project

Add the following dependency to your Maven pom.xml

```xml
    <dependency>
      <groupId>io.github.ci-cmg.wod</groupId>
      <artifactId>wod-parquet-model</artifactId>
      <version>0.0.0</version>
    </dependency>
```

## Usage
TODO








