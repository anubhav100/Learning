package spark

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object RddExample extends App {
  val spark = SparkSession.builder().appName("RddExample").master("local[*]").getOrCreate()
  val sc = spark.sparkContext

  /**
    * whole text files read all the file inside a folder and returns back the file name with its content
    */

  val pairRdd: RDD[(String, String)] = spark.sparkContext.wholeTextFiles("/home/anubhav/Documents/spark_file_formats")
  pairRdd.collect().foreach(println)

  // reduce() applies the reduce function to all the elements in the RDD and sends it to the Driver
  List(1, 23, 4, 7).reduceLeft((a, b) => a + b)
  pairRdd.reduce((a, b) => (a._1, a._2 + b._2))

  /** difference between Dag and lineage
    * Dag represents the operation of an rdd with vertices as rdd and operations as edge
    * where as lineage specify about parent dependencies of a rdd for example we can use toDebug string to see it
    * and dag can be seen in spark ui
    */


  /** Co group rdd combines the rdd with same key to its values
    * rdd1.coGroup(rdd2)
    */

  val rdd1 = sc.makeRDD(Array(("A", "1"), ("B", "2"), ("C", "3")), 2)
  val rdd2 = sc.makeRDD(Array(("A", "a"), ("C", "c"), ("D", "d")), 2)

  rdd1.cogroup(rdd2).collect.foreach(println)

  /** ShuffledRDD
    * ShuffledRDD shuffles the RDD elements by key so as to accumulate values for the same key on the same executor to allow an aggregation or combining logic. A very good example is to look at what happens when reduceByKey() is called on a PairRDD
    */


  /**
    * this example will simply add the rdd pairs which have the same key
    */

  pairRdd.reduceByKey(_ + _).collect.foreach(println)

  /** Union Rdd
    * it simply add two rdd
    */
  val a = sc.parallelize(Seq(1, 2.3))
  val b = sc.parallelize(Seq(4, 5.6))

  a.union(b).collect.foreach(println)

  /** Hadoop rdd
    * it simply use map reduce api to read data
    * example is sc.textFile(path of file)
    */

  /** GroupByKey
    * it will group the data based on key and will involve more shuffling because it don't have a local combiner
    * because more data will shuffle from one node to another
    */

  val rdd = sc.parallelize(Seq(("a", 1), ("b", 2), ("a", 3), ("b", 4), ("a", 8)))
  rdd.groupByKey().collect.foreach(println)

  /** Classic Word count example using groupBYKey and reduceByKey task
    * when using word count(i.e on pair rdd) group by key returns a iterator of combined elements
    * reduce by key as opposed to group by key use a local combiner to aggreagte keys
    * reduceByKey will aggregate y key before shuffling, and groupByKey will shuffle all the value key pairs as the diagrams show
    */
  val words = sc.textFile("/home/anubhav/Documents/spark_file_formats/comparison between orc and apache carbondata")
  val splittedWords = words.flatMap { value => value.split(" ") }
  val pairedWords = splittedWords.map(word => (word, 1))
  pairedWords.reduceByKey(_ + _).collect.foreach(println)
  pairedWords.groupByKey().map { case (key, value) => (key, value.toList.sum) }.collect().foreach(println)


  /** AggregateByKey
    * aggregateByKey provides the way to how to aggregate within the partitions and across partitions
    */

  val dummyString = "Hi Hello Hi Hello How are You"

  sc.parallelize(Seq(dummyString).flatMap(value => value.split(" "))
    .map{word => (word,1)})
    .aggregateByKey(0)((a,b) => a + b ,(x,y) => x - y)
    .collect()
    .foreach(println)

  /** CombineByKey
    * difference between combine by key and aggregate by key is that combine by key we can provide the function as initial valur
    */

  val inputrdd = sc.parallelize(Seq(
                        ("maths", 50), ("maths", 60),
                        ("english", 65),
                        ("physics", 66), ("physics", 61), ("physics", 87)),
    5)

  /** Partitioner
    * 1.Hash Partitioner:
    * HashPartitioner is the default partitioner in Spark and works by calculating a hash value for each key of the RDD elements. All the elements with the same hashcode end up in the same partition as shown in the following code snippet
    * partitionIndex = hashcode(key) % numPartitions
    */

}
