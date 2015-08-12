package com.github.agourlay.cornichon.core

import spray.json._

trait CornichonError extends Exception {
  val msg: String
}

case class StepExecutionError[A](title: String, exception: Throwable) extends CornichonError {
  val msg = s"step '$title' failed by throwing exception ${exception.printStackTrace()}"
}

case class StepAssertionError[A](expected: A, actual: A) extends CornichonError {
  private val baseMsg =
    s"""|expected result was:
        |'$expected'
        |but actual result is:
        |'$actual'
        |""".stripMargin.trim

  // TODO offer better diff
  val msg = actual match {
    case s: String   ⇒ s"$baseMsg \n String diff is '${s.diff(expected.asInstanceOf[String])}'"
    case j: JsArray  ⇒ s"$baseMsg \n JsArray diff is '${j.elements.diff(expected.asInstanceOf[JsArray].elements)}'"
    case j: JsObject ⇒ s"$baseMsg \n JsObject diff is '${j.fields.toSet.diff(expected.asInstanceOf[JsValue].asJsObject.fields.toSet)}'"
    case j: JsValue  ⇒ s"$baseMsg \n JsValue diff is '${j.prettyPrint.diff(expected.asInstanceOf[JsValue].prettyPrint)}'"
    case _           ⇒ baseMsg
  }

}

case class ResolverError(key: String) extends CornichonError {
  val msg = s"key '<$key>' can not be resolved"
}

case class KeyNotFoundInSession(key: String) extends CornichonError {
  val msg = s"key '$key' can not be found in session"
}

case class WhileListError(msg: String) extends CornichonError

case class NotAnArrayError(msg: String) extends CornichonError