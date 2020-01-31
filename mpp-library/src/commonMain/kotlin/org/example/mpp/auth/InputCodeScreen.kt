package org.example.mpp.auth

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.*
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.core.Value
import dev.icerock.moko.widgets.core.Widget
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.screen.getArgument
import dev.icerock.moko.widgets.screen.getViewModel
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.screen.navigation.route
import dev.icerock.moko.widgets.style.view.SizeSpec
import dev.icerock.moko.widgets.style.view.WidgetSize

class InputCodeScreen(
    private val viewModelFactory: (
        eventsDispatcher: EventsDispatcher<InputCodeViewModel.EventsListener>,
        token: String
    ) -> InputCodeViewModel,
    private val routeMain: Route<Unit>,
    private val theme: Theme
): WidgetScreen<Args.Parcel<InputCodeScreen.Arg>>(), InputCodeViewModel.EventsListener, NavigationItem {

    @Parcelize
    data class Arg(val token: String) : Parcelable

    object Ids {
        object Name: InputWidget.Id
    }

    override val navigationBar: NavigationBar
        get() = NavigationBar.Normal("Input code".desc())

    override fun createContentWidget(): Widget<WidgetSize.Const<SizeSpec.AsParent, SizeSpec.AsParent>> = with(theme) {
        val viewModel: InputCodeViewModel = getViewModel {
            viewModelFactory(createEventsDispatcher(), getArgument().token)
        }

        constraint(size = WidgetSize.AsParent) {
            val codeInputWidget: ConstraintItem.Child = +input(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                id = InputCodeScreen.Ids.Name,
                label = const("Name"),
                field = viewModel.codeField
            )

            val button: ConstraintItem.Child = +button(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                content = ButtonWidget.Content.Text(Value.data("Submit".desc())),
                onTap = viewModel::didTapSubmitButton
            )

            constraints {
                codeInputWidget centerYToCenterY root
                codeInputWidget leftRightToLeftRight root offset 16

                button bottomToBottom root.safeArea offset 16
                button leftRightToLeftRight root offset 16
            }
        }
    }

    override fun routeToMain() {
        routeMain.route(this)
    }
}