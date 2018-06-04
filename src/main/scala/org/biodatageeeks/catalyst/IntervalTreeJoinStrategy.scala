package org.biodatageeeks.catalyst


import org.apache.spark.sql.catalyst.expressions.PredicateHelper
import org.apache.spark.sql.execution.SparkPlan
import org.apache.spark.sql.{SparkSession, Strategy}
import org.apache.spark.sql.catalyst.plans.logical.{Filter, LogicalPlan}

import scala.annotation.tailrec

/**
  * Created by marek on 27/01/2018.
  */
class IntervalTreeJoinStrategy(spark: SparkSession) extends Strategy with Serializable with  PredicateHelper {
  def apply(plan: LogicalPlan): Seq[SparkPlan] = plan match {
    case ExtractRangeJoinKeys(joinType, rangeJoinKeys, left, right) =>
      IntervalTreeJoinOptim(planLater(left), planLater(right), rangeJoinKeys, spark,left,right) :: Nil
    case _ =>
      Nil
  }


}