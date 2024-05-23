package com.jimbo.clicker.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jimbo.clicker.R
import com.jimbo.clicker.util.pxToDp

/**
 * Animation [LottieAnimation]
 * @param offset set the animation at the click's offset
 * @param alphaAnimation 'alpha' parameter progress
 */
@Composable
fun AnimClick(offset: Offset, alphaAnimation: Animatable<Float, AnimationVector1D>) {
    Box(
        modifier = Modifier
            .absoluteOffset(
                offset.x.pxToDp() - 15.dp,
                offset.y.pxToDp() - 25.dp
            )
            .alpha(alphaAnimation.value)
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.clic))
        val progress by animateLottieCompositionAsState(composition)
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(86.dp)
        )
    }
}