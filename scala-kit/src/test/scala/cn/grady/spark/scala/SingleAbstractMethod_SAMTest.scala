package cn.grady.spark.scala

import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.JButton

/**
 * @author rociss 
 * @version 1.0, on 1:48 2022/7/4.
 */
object SingleAbstractMethod_SAMTest {

  def main(args: Array[String]): Unit = {

    val button = new JButton("Click")
    button.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        println("click Me !!!")
      }
    })
    implicit def getActionListener(actionProcessFunc:(ActionEvent)=>Unit)=new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit ={
        actionProcessFunc(e)
      }
    }
    button.addActionListener((event:ActionEvent)=>println("click me!!"))
  }
}
