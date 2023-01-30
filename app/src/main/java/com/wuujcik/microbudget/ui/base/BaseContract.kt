//package com.wuujcik.microbudget.ui.base
//
//import com.haroldadmin.vector.VectorState
//
///**
// * A contract defining the base of MVI architecture components.
// */
//interface BaseContract {
//
//    /**
//     * Base state of a view.
//     */
//    interface State : VectorState
//
//    /**
//     * Base view with functionality every view should implement.
//     */
//    interface View<State : BaseContract.State> {
//
//        val eventProcessor: EventProcessor
//        val currentState: State
//
//        /**
//         * This method is used for sending events to [EventProcessor].
//         * These events can be handled by a view model.
//         */
//        fun sendEvent(event: Any) {
//            eventProcessor.process(event)
//        }
//    }
//}
//
//class EventProcessor(private val emitter: (Any) -> Unit) {
//
//    fun process(event: Any) {
//        emitter.invoke(event)
//    }
//}