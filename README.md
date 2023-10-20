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
 |-- originatorsCruise: string (nullable = false)
 |-- timestamp: long (nullable = false)
 |-- year: integer (nullable = false)
 |-- month: integer (nullable = false)
 |-- day: integer (nullable = false)
 |-- time: double (nullable = false)
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
 |    |    |-- variable: integer (nullable = false)
 |    |    |-- code: integer (nullable = false)
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
 |    |    |-- attributes: array (nullable = false)
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
 |    |    |    |    |-- variable: integer (nullable = false)
 |    |    |    |    |-- value: double (nullable = false)
 |    |    |    |    |-- qcFlag: integer (nullable = false)
 |    |    |    |    |-- originatorsFlag: integer (nullable = false)
```

### root (cast)

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


## Java WOD Parquet Model

TODO




Additional project information, javadocs, and test coverage is located at https://ci-cmg.github.io/project-documentation/wod-parquet-model/

## Adding To Your Project

Add the following dependency to your Maven pom.xml

```xml
    <dependency>
      <groupId>io.github.ci-cmg.wod</groupId>
      <artifactId>wod-parquet-model</artifactId>
      <version>0.0.1</version>
    </dependency>
```

## Usage
TODO








