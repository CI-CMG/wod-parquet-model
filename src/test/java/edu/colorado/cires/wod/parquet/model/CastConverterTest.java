package edu.colorado.cires.wod.parquet.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import org.apache.commons.io.FileUtils;
import org.apache.sedona.spark.SedonaContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CastConverterTest {

  private static final String WCS84_PROJJSON = "{\"$schema\": \"https://proj.org/schemas/v0.7/projjson.schema.json\",\"type\": \"GeographicCRS\",\"name\": \"WGS 84\",\"datum_ensemble\": {\"name\": \"World Geodetic System 1984 ensemble\",\"members\": [{\"name\": \"World Geodetic System 1984 (Transit)\",\"id\": {\"authority\": \"EPSG\",\"code\": 1166}},{\"name\": \"World Geodetic System 1984 (G730)\",\"id\": {\"authority\": \"EPSG\",\"code\": 1152}},{\"name\": \"World Geodetic System 1984 (G873)\",\"id\": {\"authority\": \"EPSG\",\"code\": 1153}},{\"name\": \"World Geodetic System 1984 (G1150)\",\"id\": {\"authority\": \"EPSG\",\"code\": 1154}},{\"name\": \"World Geodetic System 1984 (G1674)\",\"id\": {\"authority\": \"EPSG\",\"code\": 1155}},{\"name\": \"World Geodetic System 1984 (G1762)\",\"id\": {\"authority\": \"EPSG\",\"code\": 1156}},{\"name\": \"World Geodetic System 1984 (G2139)\",\"id\": {\"authority\": \"EPSG\",\"code\": 1309}}],\"ellipsoid\": {\"name\": \"WGS 84\",\"semi_major_axis\": 6378137,\"inverse_flattening\": 298.257223563},\"accuracy\": \"2.0\",\"id\": {\"authority\": \"EPSG\",\"code\": 6326}},\"coordinate_system\": {\"subtype\": \"ellipsoidal\",\"axis\": [{\"name\": \"Geodetic latitude\",\"abbreviation\": \"Lat\",\"direction\": \"north\",\"unit\": \"degree\"},{\"name\": \"Geodetic longitude\",\"abbreviation\": \"Lon\",\"direction\": \"east\",\"unit\": \"degree\"}]},\"scope\": \"Horizontal component of 3D system.\",\"area\": \"World.\",\"bbox\": {\"south_latitude\": -90,\"west_longitude\": -180,\"north_latitude\": 90,\"east_longitude\": 180},\"id\": {\"authority\": \"EPSG\",\"code\": 4326}}";
  private static final String GEOPARQUET_VERSION = "1.0.0";

  private SparkSession spark;

  @BeforeEach
  public void before() throws Exception {
    FileUtils.deleteQuietly(new File("target/dataset.parquet"));
    Files.createDirectories(Paths.get("target"));
    spark = SedonaContext.create(SedonaContext
        .builder()
        .appName("test")
        .master("local[*]")
        .getOrCreate());
  }

  @AfterEach
  public void after() throws IOException {
    spark.close();
    FileUtils.deleteQuietly(new File("target/dataset.parquet"));
  }

  @Test
  public void testConversion() throws Exception {
    Dataset<Cast> source = spark.createDataset(Collections.singletonList(Cast.builder()
        .withDataset("APB")
        .withCruiseNumber(5)
        .withCastNumber(1)
        .withYear(2006)
        .withMonth(6)
        .withDay(11)
        .withTime(0D)
        .withTimestamp(LocalDateTime.of(2006, 6, 11, 0, 0).atZone(ZoneId.of("UTC")).toInstant().toEpochMilli())
        .withLongitude(55.4D)
        .withLatitude(10.5)
        .withProfileType(1)
        .withOriginatorsStationCode("foo")
        .withVariables(Collections.singletonList(Variable.builder()
            .withCode(5)
            .withMetadata(Collections.singletonList(Metadata.builder().withCode(2).withValue(55.4).build()))
            .build()))
        .withPrincipalInvestigators(Collections.singletonList(PrincipalInvestigator.builder()
            .withVariableCode(2)
            .withPiCode(88)
            .build()))
        .withAttributes(Collections.singletonList(Attribute.builder()
            .withCode(9)
            .withValue(534.5)
            .build()))
        .withBiologicalAttributes(Collections.singletonList(Attribute.builder()
            .withCode(7)
            .withValue(41.2)
            .build()))
        .withTaxonomicDatasets(Collections.singletonList(TaxonomicDataset.builder()
            .withValues(Collections.singletonList(QcAttribute.builder()
                .withCode(3)
                .withValue(88.4)
                .withQcFlag(1)
                .withOriginatorsFlag(2)
                .build()))
            .build()))
        .withDepths(Collections.singletonList(Depth.builder()
            .withDepth(25.0)
            .withDepthErrorFlag(1)
            .withOriginatorsFlag(0)
            .withData(Collections.singletonList(ProfileData.builder()
                .withVariableCode(3)
                .withValue(446.3)
                .withOriginatorsFlag(1)
                .withQcFlag(3)
                .build()))
            .build()))
        .build()), Encoders.bean(Cast.class));

    Path out = Paths.get("target/dataset.parquet").toAbsolutePath();

    source.write()
        .format("geoparquet")
        .option("geoparquet.version", GEOPARQUET_VERSION)
        .option("geoparquet.crs", WCS84_PROJJSON)
        .save(String.format("file://" + out));

    Dataset<Row> dataset = spark.read().format("geoparquet").load("file://" + out);
    Dataset<Cast> castDataset = dataset.as(Encoders.bean(Cast.class));
    assertEquals(source.collectAsList(), castDataset.collectAsList());

  }

}
