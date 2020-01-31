package org.example.mpp.LoginScreen

import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.liveBlock
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.*
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.core.Value
import dev.icerock.moko.widgets.core.Widget
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.style.view.SizeSpec
import dev.icerock.moko.widgets.style.view.WidgetSize


class LoginScreen(
    private val theme: Theme
): WidgetScreen<Args.Empty>() {

    object Ids {
        object Name: InputWidget.Id
    }

    override fun createContentWidget(): Widget<WidgetSize.Const<SizeSpec.AsParent, SizeSpec.AsParent>> = with(theme) {
        constraint(size = WidgetSize.AsParent) {
            val nameInputWidget: ConstraintItem.Child = +input(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                id = Ids.Name,
                label = const("Name"),
                field = FormField(initialValue = "", validation = liveBlock { null })
            )

            val button: ConstraintItem.Child = +button(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                content = ButtonWidget.Content.Text(Value.data("Submit".desc()))
            ) {
                println("I'm tapped")
            }

            constraints {
                nameInputWidget centerYToCenterY root
                nameInputWidget leftRightToLeftRight root offset 16

                button bottomToBottom root.safeArea offset 16
                button leftRightToLeftRight root offset 16
            }
        }
    }
}