package uy.com.netlabs.esb.endpoint.stream

import java.nio.ByteBuffer

/**
 * Iteratee based concept of Consumer, though this one only feeds on chunks of bytes. No more
 * generalization needed.
 */
trait Consumer[State, Prod] {
  
  sealed trait ConsumerResult {
    def state: State
  }
  case class ByProduct(content: Seq[Prod], state: State) extends ConsumerResult
  case class NeedMore(state: State) extends ConsumerResult
  
  def initialState: State
  def consume(input: Input[State]): ConsumerResult
}

sealed trait Input[State] {
  def state: State
}
case class NoMoreInput[State](state: State) extends Input[State]
case class Content[State](state: State, buffer: ByteBuffer) extends Input[State]