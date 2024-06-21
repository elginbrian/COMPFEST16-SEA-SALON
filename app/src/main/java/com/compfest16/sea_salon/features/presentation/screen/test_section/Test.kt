package com.compfest16.sea_salon.features.presentation.screen.test_section

import androidx.compose.runtime.Composable
import com.compfest16.sea_salon.features.domain.dummy.BranchDummy
import org.koin.androidx.compose.getViewModel

@Composable
fun Test(){
    val viewModel = getViewModel<TestViewModel>()
    viewModel.postBranch(BranchDummy.jakartaBranches[9])
}