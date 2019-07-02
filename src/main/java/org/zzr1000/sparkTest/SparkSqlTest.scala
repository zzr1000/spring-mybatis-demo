package org.zzr1000.sparkTest

import org.apache.spark.sql.SparkSession

object SparkSqlTest {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .master("local")
      .enableHiveSupport()
      .getOrCreate()

    spark.sql("show databases")

  }
}
