//package com.wuujcik.microbudget.ui.base
//
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.lifecycleScope
//import com.haroldadmin.vector.VectorViewModel
//import kotlinx.coroutines.CoroutineScope
//
//
//abstract class BaseFragment<State : BaseContract.State> : Fragment(), BaseContract.View<State> {
//
//    protected abstract val viewModel: VectorViewModel<State>
//
//    override val currentState: State
//        get() = viewModel.currentState
//
//}
