package edu.colorado.cires.wod.parquet.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;

public class CastTest {


  @Test
  public void testPointCreation() {
    Cast cast = Cast.builder()
        .withCruiseNumber(111)
        .withCastNumber(123)
        .withTimestamp(0)
        .withProfileType(0)
        .withLongitude(35)
        .withLatitude(-68)
        .build();

    assertEquals(35D, cast.getLongitude(), 0.001);
    assertEquals(-68D, cast.getLatitude(), 0.001);

    Point point = (Point) cast.getLocation();

    assertEquals(35D, point.getX(), 0.001);
    assertEquals(-68D, point.getY(), 0.001);

    Cast cast2 = Cast.builder(cast).build();

    assertEquals(35D, cast2.getLongitude(), 0.001);
    assertEquals(-68D, cast2.getLatitude(), 0.001);

    point = (Point) cast2.getLocation();

    assertEquals(35D, point.getX(), 0.001);
    assertEquals(-68D, point.getY(), 0.001);

    Cast cast3 = Cast.builder(cast.asRow()).build();

    assertEquals(35D, cast3.getLongitude(), 0.001);
    assertEquals(-68D, cast3.getLatitude(), 0.001);

    point = (Point) cast3.getLocation();

    assertEquals(35D, point.getX(), 0.001);
    assertEquals(-68D, point.getY(), 0.001);

  }

  @Test
  public void testGeohashCreation() {
    Cast cast = Cast.builder()
        .withCruiseNumber(111)
        .withCastNumber(123)
        .withTimestamp(0)
        .withProfileType(0)
        .withLongitude(35)
        .withLatitude(-68)
        .build();

    assertEquals("hgbvhkypr", cast.getGeohash());
    assertEquals("hgb", cast.getGeohash3());

    Cast cast2 = Cast.builder(cast).build();

    assertEquals("hgbvhkypr", cast2.getGeohash());

    Cast cast3 = Cast.builder(cast.asRow()).build();

    assertEquals("hgbvhkypr", cast3.getGeohash());
  }

  @Test
  public void testTimestampCreation() {
    Cast cast = Cast.builder()
        .withCruiseNumber(111)
        .withCastNumber(123)
        .withProfileType(0)
        .withLongitude(35)
        .withLatitude(-68)
        .withYear(2023)
        .withMonth(6)
        .withDay(11)
        .withTime(11.5)
        .build();

    assertEquals(1686483000000L, cast.getTimestamp());
    assertEquals(2023, cast.getYear());
    assertEquals(6, cast.getMonth());
    assertEquals(11, cast.getDay());
    assertEquals(11.5, cast.getTime(), 0.001);

    Cast cast2 = Cast.builder(cast).build();

    assertEquals(1686483000000L, cast2.getTimestamp());
    assertEquals(2023, cast2.getYear());
    assertEquals(6, cast2.getMonth());
    assertEquals(11, cast2.getDay());
    assertEquals(11.5, cast2.getTime(), 0.001);

    Cast cast3 = Cast.builder(cast.asRow()).build();

    assertEquals(1686483000000L, cast3.getTimestamp());
    assertEquals(2023, cast3.getYear());
    assertEquals(6, cast3.getMonth());
    assertEquals(11, cast3.getDay());
    assertEquals(11.5, cast3.getTime(), 0.001);
  }

  @Test
  public void testTimestampCreationNoTime() {
    Cast cast = Cast.builder()
        .withCruiseNumber(111)
        .withCastNumber(123)
        .withProfileType(0)
        .withLongitude(35)
        .withLatitude(-68)
        .withYear(2023)
        .withMonth(6)
        .withDay(11)
        .build();

    assertEquals(1686441600000L, cast.getTimestamp());
    assertEquals(2023, cast.getYear());
    assertEquals(6, cast.getMonth());
    assertEquals(11, cast.getDay());
    assertNull(cast.getTime());

    Cast cast2 = Cast.builder(cast).build();

    assertEquals(1686441600000L, cast2.getTimestamp());
    assertEquals(2023, cast2.getYear());
    assertEquals(6, cast2.getMonth());
    assertEquals(11, cast2.getDay());
    assertNull(cast2.getTime());

    Cast cast3 = Cast.builder(cast.asRow()).build();

    assertEquals(1686441600000L, cast3.getTimestamp());
    assertEquals(2023, cast3.getYear());
    assertEquals(6, cast3.getMonth());
    assertEquals(11, cast3.getDay());
    assertNull(cast3.getTime());
  }

  @Test
  public void testDateCreation() {
    Cast cast = Cast.builder()
        .withCruiseNumber(111)
        .withCastNumber(123)
        .withProfileType(0)
        .withLongitude(35)
        .withLatitude(-68)
        .withTimestamp(1686483000000L)
        .build();

    assertEquals(1686483000000L, cast.getTimestamp());
    assertEquals(2023, cast.getYear());
    assertEquals(6, cast.getMonth());
    assertEquals(11, cast.getDay());
    assertEquals(11.5, cast.getTime(), 0.001);

    Cast cast2 = Cast.builder(cast).build();

    assertEquals(1686483000000L, cast2.getTimestamp());
    assertEquals(2023, cast2.getYear());
    assertEquals(6, cast2.getMonth());
    assertEquals(11, cast2.getDay());
    assertEquals(11.5, cast2.getTime(), 0.001);

    Cast cast3 = Cast.builder(cast.asRow()).build();

    assertEquals(1686483000000L, cast3.getTimestamp());
    assertEquals(2023, cast3.getYear());
    assertEquals(6, cast3.getMonth());
    assertEquals(11, cast3.getDay());
    assertEquals(11.5, cast3.getTime(), 0.001);
  }

}