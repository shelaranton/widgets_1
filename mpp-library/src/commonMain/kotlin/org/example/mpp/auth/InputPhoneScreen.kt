package org.example.mpp.auth

import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.liveBlock
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.*
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.core.Value
import dev.icerock.moko.widgets.core.Widget
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.screen.getViewModel
import dev.icerock.moko.widgets.screen.listen
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.style.view.SizeSpec
import dev.icerock.moko.widgets.style.view.WidgetSize


class InputPhoneScreen(
    private val theme: Theme,
    private val viewModelFactory: (
        eventsDispatcher: EventsDispatcher<InputPhoneViewModel.EventsListener>
    ) -> InputPhoneViewModel,
    private val routeInputCode: Route<String>
): WidgetScreen<Args.Empty>(), InputPhoneViewModel.EventsListener, NavigationItem {

    object Ids {
        object Name: InputWidget.Id
    }

    override val navigationBar: NavigationBar
        get() = NavigationBar.Normal("Input phone number".desc())

    override fun createContentWidget(): Widget<WidgetSize.Const<SizeSpec.AsParent, SizeSpec.AsParent>> = with(theme) {
        val viewModel: InputPhoneViewModel = getViewModel {
            viewModelFactory(createEventsDispatcher())
        }

        viewModel.eventsDispatcher.listen(this@InputPhoneScreen, this@InputPhoneScreen)

        constraint(size = WidgetSize.AsParent) {
            val nameInputWidget: ConstraintItem.Child = +input(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                id = Ids.Name,
                label = const("Name"),
                field = viewModel.phoneField
            )

            val button: ConstraintItem.Child = +button(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                content = ButtonWidget.Content.Text(Value.data("Submit".desc())),
                onTap = viewModel::didTapSubmitButton
            )

            constraints {
                nameInputWidget centerYToCenterY root
                nameInputWidget leftRightToLeftRight root offset 16

                button bottomToBottom root.safeArea offset 16
                button leftRightToLeftRight root offset 16
            }
        }
    }

    override fun routeToInputCode(token: String) {
        routeInputCode.route(this, token)
    }
}