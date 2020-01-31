package org.example.mpp.auth

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route

class AuthFactory(
    private val theme: Theme
) {

    fun createInputPhoneScreen(route: Route<String>): InputPhoneScreen {
        return InputPhoneScreen(
            theme = theme,
            viewModelFactory = {
                return@InputPhoneScreen InputPhoneViewModel(it)
            },
            routeInputCode = route
        )
    }

    fun createInputCodeScreen(route: Route<Unit>): InputCodeScreen {
        return InputCodeScreen(
            theme = theme,
            viewModelFactory = { eventsDispatcher: EventsDispatcher<InputCodeViewModel.EventsListener>, token: String ->
                return@InputCodeScreen InputCodeViewModel(eventsDispatcher, token)
            },
            routeMain = route
        )
    }
}