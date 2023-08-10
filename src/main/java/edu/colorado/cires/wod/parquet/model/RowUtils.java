package edu.colorado.cires.wod.parquet.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.apache.spark.sql.Row;
import scala.collection.JavaConverters;
import scala.collection.Seq;

final class RowUtils {

  static <T> List<T> getAs(Row row, String field, Function<Row, T> transform) {
    List<Row> list = row.getList(row.fieldIndex(field));
    if(list == null) {
      return null;
    }
    List<T> typedList = new ArrayList<>();
    for (Row r : list) {
      typedList.add(transform.apply(r));
    }
    return typedList;
  }

  static <T> List<List<T>> getAsList(Row row, String field, Function<Row, T> transform) {
    List<Seq<Row>> listList = row.getList(row.fieldIndex(field));
    if(listList == null) {
      return null;
    }
    List<List<T>> typedListList = new ArrayList<>();
    for (Seq<Row> seq : listList) {
      List<T> innerList = new ArrayList<>();
      for (Row r : JavaConverters.asJava(seq)) {
        innerList.add(transform.apply(r));
      }
      typedListList.add(innerList);
    }
    return typedListList;
  }

  private RowUtils() {

  }

}
