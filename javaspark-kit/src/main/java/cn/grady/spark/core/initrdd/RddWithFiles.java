package cn.grady.spark.core.initrdd;

/**
 * @author rociss
 * @version 1.0, on 23:47 2022/7/28.
 */

/**
 * 基于文件创建RDD，
 * 本地文件：textFile() 支持目录，压缩文件以及通配符进行RDD 创建
 * hdfs 文件：默认为每个block创建一个partition，
 *      也可以通过textFile()第二个参数手动设置分区数量，只能比block数量多，不能比block 少
 *
 */
public class RddWithFiles {
}
