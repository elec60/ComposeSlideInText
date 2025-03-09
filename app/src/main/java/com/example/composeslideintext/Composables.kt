package com.example.composeslideintext

import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * Data class representing a single line of text with its index in the original text.
 *
 * @property index The zero-based index of this line in the original text.
 * @property text The content of this line as an AnnotatedString, preserving any styling from the original text.
 */
data class LineInfo(
    val index: Int,
    val text: AnnotatedString
)

@Composable
fun SlideInText(modifier: Modifier = Modifier) {
    var showAnimation by remember { mutableStateOf(false) }
    var delayPerLine by remember { mutableFloatStateOf(500f) }
    var animationDuration by remember { mutableFloatStateOf(500f) }

    val backgroundColor = Color(0xFFF8F9FA)
    val textColor = Color(0xFF212529)
    val accentColor = Color(0xFF6200EE)
    val buttonTextColor = Color.White
    val cardBackgroundColor = Color(0xFFFFFFFF)
    val sliderColor = Color(0xFF9575CD)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                if (showAnimation) {
                    val sampleText = buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = accentColor)) {
                            append("LineByLineSlideInText")
                        }
                        append(" makes your text look more interesting and fun to read in Jetpack Compose apps. Instead of showing all your text at once, it shows one line at a time, with each new line sliding up from the bottom of the screen. This makes people more likely to read your text because it catches their eye and feels more alive than regular, static text. It's easy to add to any app and works with any kind of text you want to show. The smooth way the lines move in, one after another, creates a nice flow that helps people follow along with what you're saying. It's especially good for welcome screens, instructions, or any time you want people to pay extra attention to your words.")
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        LineByLineSlideInText(
                            text = sampleText,
                            style = TextStyle(
                                fontSize = 20.sp,
                                lineHeight = 28.sp,
                                color = textColor
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp),
                            animationDelayPerLine = delayPerLine.toLong(),
                            animationDuration = animationDuration.toInt()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Animation Controls",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = accentColor,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Delay slider
                    Text(
                        text = "Delay between lines: ${delayPerLine.toInt()} ms",
                        fontSize = 14.sp,
                        color = textColor
                    )
                    Slider(
                        value = delayPerLine,
                        onValueChange = { delayPerLine = it },
                        valueRange = 100f..1000f,
                        steps = 9,
                        colors = SliderDefaults.colors(
                            thumbColor = sliderColor,
                            activeTrackColor = sliderColor,
                            inactiveTrackColor = sliderColor.copy(alpha = 0.3f)
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "Animation duration: ${animationDuration.toInt()} ms",
                        fontSize = 14.sp,
                        color = textColor
                    )
                    Slider(
                        value = animationDuration,
                        onValueChange = { animationDuration = it },
                        valueRange = 100f..1000f,
                        steps = 9,
                        colors = SliderDefaults.colors(
                            thumbColor = sliderColor,
                            activeTrackColor = sliderColor,
                            inactiveTrackColor = sliderColor.copy(alpha = 0.3f)
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Button(
                        onClick = { showAnimation = !showAnimation },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = accentColor
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text(
                            text = if (showAnimation) "Reset Animation" else "Start Animation",
                            fontSize = 16.sp,
                            color = buttonTextColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LineByLineSlideInText(
    text: AnnotatedString,
    style: TextStyle,
    textAlign: TextAlign,
    modifier: Modifier = Modifier,
    animationDelayPerLine: Long = 500L,
    animationDuration: Int = 500
) {
    val density = LocalDensity.current

    // Measure the text to split it into lines based on available width
    val textMeasurer = rememberTextMeasurer()
    val maxWidth = with(density) {
        (LocalConfiguration.current.screenWidthDp.dp - 32.dp).toPx()
    }

    // Calculate where line breaks occur in the text
    val textLayoutResult = textMeasurer.measure(
        text = text,
        style = style,
        maxLines = Int.MAX_VALUE,
        constraints = Constraints(maxWidth = maxWidth.toInt())
    )

    // Extract each line of text as a separate AnnotatedString
    val lineCount = textLayoutResult.lineCount
    val lineInfoList = (0 until lineCount).map { lineIndex ->
        val lineStart = textLayoutResult.getLineStart(lineIndex)
        val lineEnd = textLayoutResult.getLineEnd(lineIndex)
        val lineText = text.subSequence(lineStart, lineEnd)
        LineInfo(lineIndex, lineText)
    }

    // Get the line height from the text style
    val lineHeight: TextUnit = style.lineHeight

    // Create a column to hold all lines of text
    Column(
        modifier = modifier,
        horizontalAlignment = when (textAlign) {
            TextAlign.Center -> Alignment.CenterHorizontally
            TextAlign.End -> Alignment.End
            else -> Alignment.Start
        }
    ) {
        // For each line, create a clipped container and animate the text inside it
        lineInfoList.forEachIndexed { index, lineInfo ->
            // Calculate delay for this line based on its position
            val animationDelay = animationDelayPerLine * index

            // Track whether animation has started for this line
            var animationStarted by remember { mutableStateOf(false) }

            // Start animation after the calculated delay
            LaunchedEffect(key1 = "$animationDelayPerLine-$animationDuration") {
                animationStarted = false
                delay(animationDelay)
                animationStarted = true
            }

            // Animate the vertical offset of the text
            val animatedOffsetY by animateFloatAsState(
                // Start offscreen (at lineHeight) and animate to 0
                targetValue = if (animationStarted) 0f else lineHeight.value,
                animationSpec = tween(
                    durationMillis = animationDuration,
                    easing = EaseOutQuad  // Smooth deceleration at the end
                ),
                label = "offsetY"
            )

            // Clip box to hide text that hasn't fully translated into view
            Box(
                modifier = Modifier
                    // Add a small padding to prevent clipping issues
                    .height(with(density) { lineHeight.toDp() + 2.dp })
                    .fillMaxWidth()
                    // Clip to ensure text outside the box is not visible
                    .clip(RectangleShape)
            ) {
                // The actual text that will be animated
                Text(
                    text = lineInfo.text,
                    style = style,
                    textAlign = textAlign,
                    modifier = Modifier
                        .fillMaxWidth()
                        // Apply the animated translation
                        .graphicsLayer {
                            translationY = with(density) {
                                animatedOffsetY.dp.toPx()
                            }
                        }
                )
            }
        }
    }
}