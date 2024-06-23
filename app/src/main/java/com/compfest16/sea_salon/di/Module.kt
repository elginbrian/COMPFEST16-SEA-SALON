package com.compfest16.sea_salon.di

import com.compfest16.sea_salon.features.data.repository.BranchRepositoryImpl
import com.compfest16.sea_salon.features.data.repository.ImageRepositoryImpl
import com.compfest16.sea_salon.features.data.repository.ReservationRepositoryImpl
import com.compfest16.sea_salon.features.data.repository.ReviewRepositoryImpl
import com.compfest16.sea_salon.features.data.repository.UserRepositoryImpl
import com.compfest16.sea_salon.features.domain.repository.BranchRepository
import com.compfest16.sea_salon.features.domain.repository.ImageRepository
import com.compfest16.sea_salon.features.domain.repository.ReservationRepository
import com.compfest16.sea_salon.features.domain.repository.ReviewRepository
import com.compfest16.sea_salon.features.domain.repository.UserRepository
import com.compfest16.sea_salon.features.presentation.screen.auth_section.AuthViewModel
import com.compfest16.sea_salon.features.presentation.screen.home_section.HomeViewModel
import com.compfest16.sea_salon.features.presentation.screen.nearby_section.NearbyViewModel
import com.compfest16.sea_salon.features.presentation.screen.reservation_section.ReservationViewModel
import com.compfest16.sea_salon.features.presentation.screen.test_section.TestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Module {
    val repository = module {
        single<UserRepository> { UserRepositoryImpl() }
        single<BranchRepository> { BranchRepositoryImpl() }
        single<ReviewRepository> { ReviewRepositoryImpl() }
        single<ReservationRepository> { ReservationRepositoryImpl() }
        single<ImageRepository> { ImageRepositoryImpl() }
    }

    val viewModel = module {
        viewModel { AuthViewModel(get(), get()) }
        viewModel { HomeViewModel(get(), get(), get()) }
        viewModel { NearbyViewModel(get()) }
        viewModel { ReservationViewModel(get()) }
        viewModel { TestViewModel(get()) }
    }
}