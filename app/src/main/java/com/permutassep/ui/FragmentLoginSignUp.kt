package com.permutassep.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
import com.permutassep.ui.compose.WelcomeScreen

class FragmentLoginSignUp : BaseFragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WelcomeScreen()
            }
        }
    }

//    @OnClick(R.id.btnRegister)
//    fun onBtnRegisterClick() {
//        navigationListener.onNextFragment(FragmentSignUp::class.java)
//    }
//
//    @OnClick(R.id.btnLogin)
//    fun onBtnLoginClick() {
//        navigationListener.onNextFragment(FragmentLogin::class.java)
//    }
//
//    @OnClick(R.id.tvContinue)
//    fun onTvContinueClick() {
//        navigationListener.onNextFragment(FragmentPagedNewsFeed::class.java)
//    }

    companion object {
        @JvmStatic
        fun newInstance(): FragmentLoginSignUp {
            return FragmentLoginSignUp()
        }
    }
}