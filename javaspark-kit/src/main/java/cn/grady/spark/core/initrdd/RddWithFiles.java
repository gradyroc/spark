package cn.grady.spark.core.initrdd;

/**
 * @author rociss
 * @version 1.0, on 23:47 2022/7/28.
 */

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;

/**
 * 基于文件创建RDD，
 * 本地文件：textFile() 支持目录，压缩文件以及通配符进行RDD 创建
 * hdfs 文件：默认为每个block创建一个partition，
 *      也可以通过textFile()第二个参数手动设置分区数量，只能比block数量多，不能比block 少
 *
 *      sc.wholeTextFiles();
 *          针对一个目录中的大量小文件，返回<filename,fileContent> 组成的pair,pairRDD
 *      sc.sequenceFile[k,v]();
 *          针对SequenceFile创建RDD，k，v 泛型类型就是SequenceFile的key 和value的类型 ，k和v 必须是hadoop的序列化类型，比如IntWritable，text
 *
 *      sc.hadoopRDD();
 *          对于hadoop自定义输入类型，创建RDD，接收JobConf，InputFormatClass，key 和value的class
 *
 *      sc.objectFile();
 *          可以针对之前调用RDD.saveAsObjectFile()创建的对象序列化文件，反序列化文件中的数据，并创建RDD
 *
 *
 */
public class RddWithFiles {
    public static void main(String[] args) {

        SparkConf conf = new SparkConf()
                .setMaster("local")
                .setAppName("ParallelizeCollection");

        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> lines = sc.textFile("D:\\Projects\\github\\spark-kit\\javaspark-kit\\src\\main\\resources\\wordCountFile");

        //map 计算每一行的长度
        JavaRDD<Integer> lineLength = lines.map(new Function<String, Integer>() {
            @Override
            public Integer call(String v1) throws Exception {
                return v1.split(" ").length;
            }
        });

        //
        Integer count = lineLength.reduce(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        System.out.println("文件总字数："+count);
    }
}
