package com.fm.easypay.rules.koin

import com.fm.easypay.networking.NetworkingModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

@ExperimentalCoroutinesApi
class KoinNetworkingModulesRule : TestWatcher() {
    override fun starting(description: Description) {
        super.starting(description)
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                modules(NetworkingModule.networkingModules)
            }
        }
    }

    override fun finished(description: Description) {
        super.finished(description)
        stopKoin()
    }
}