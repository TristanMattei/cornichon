package com.github.agourlay.cornichon.steps

import com.github.agourlay.cornichon.core.Engine
import com.github.agourlay.cornichon.dsl.ProvidedInstances
import com.github.agourlay.cornichon.resolver.Resolver

import monix.execution.Scheduler

import scala.concurrent.ExecutionContext

trait StepUtilSpec extends ProvidedInstances {

  implicit val scheduler = Scheduler(ExecutionContext.global)
  val resolver = Resolver.withoutExtractor()
  val engine = Engine.withStepTitleResolver(resolver)

}
