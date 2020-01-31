package org.example.mpp.auth

import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.liveBlock
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc

class InputPhoneViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>
): ViewModel(), EventsDispatcherOwner<InputPhoneViewModel.EventsListener> {
   interface EventsListener {
       fun routeToInputCode(token: String)
   }

    val phoneField: FormField<String, StringDesc> = FormField(
        initialValue = "",
        validation = liveBlock { null }
    )

    fun didTapSubmitButton() {
        val phone: String = this.phoneField.data.value
        val token: String = "token:" + phone

        println("phone: $phone")

        eventsDispatcher.dispatchEvent {
            routeToInputCode(token)
        }
    }
}