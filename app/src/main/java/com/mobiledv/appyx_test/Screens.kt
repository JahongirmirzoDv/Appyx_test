package com.mobiledv.appyx_test

import android.os.Parcelable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.core.node.node
import com.bumble.appyx.routingsource.spotlight.Spotlight
import com.bumble.appyx.routingsource.spotlight.backpresshandler.GoToPrevious
import com.bumble.appyx.routingsource.spotlight.hasNext
import com.bumble.appyx.routingsource.spotlight.hasPrevious
import com.bumble.appyx.routingsource.spotlight.operation.next
import com.bumble.appyx.routingsource.spotlight.operation.previous
import com.bumble.appyx.routingsource.spotlight.transitionhandler.rememberSpotlightSlider
import com.mobiledv.Screen1
import kotlinx.parcelize.Parcelize

class Screens(
    buildContext: BuildContext,
    private val spotlight: Spotlight<Routing> = Spotlight(
        items = listOf(
            Routing.Child1,
            Routing.Child2,
            Routing.Child3,
        ),
        backPressHandler = GoToPrevious(),
        savedStateMap = buildContext.savedStateMap,
    )
) : ParentNode<Screens.Routing>(
    routingSource = spotlight,
    buildContext = buildContext
) {

    override fun resolve(routing: Routing, buildContext: BuildContext): Node {
        return when(routing){
            Routing.Child1 -> node(buildContext) { Screen1(buildContext) }
            Routing.Child2 -> node(buildContext) { Screen2() }
            Routing.Child3 -> node(buildContext) { Screen3() }
        }
    }

    sealed class Routing: Parcelable {
        @Parcelize
        object Child1 : Routing()

        @Parcelize
        object Child2 : Routing()

        @Parcelize
        object Child3 : Routing()
    }

    @Composable
    override fun View(modifier: Modifier) {
        val hasPrevious = spotlight.hasPrevious().collectAsState(initial = false)
        val hasNext = spotlight.hasNext().collectAsState(initial = false)
        val previousVisibility = animateFloatAsState(
            targetValue = if (hasPrevious.value) 1f else 0f
        )
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Children(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                transitionHandler = rememberSpotlightSlider(clipToBounds = true),
                routingSource = spotlight
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(
                            start = 24.dp,
                            end = 24.dp,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (hasNext.value) {
                        TextButton(
                            modifier = Modifier.alpha(previousVisibility.value),
                            enabled = hasPrevious.value,
                            onClick = { spotlight.previous() }
                        ) {
                            Text(
                                text = "Previous".toUpperCase(Locale.current),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        TextButton(
                            onClick = { spotlight.next() }
                        ) {
                            Text(
                                text = "Next".toUpperCase(Locale.current),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        Spacer(Modifier)
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { finish() }
                        ) {
                            Text(
                                text = "Check it out!",
                                color = MaterialTheme.colors.onPrimary,
                            )
                        }
                        Spacer(Modifier)
                    }
                }
            }
        }
    }
}