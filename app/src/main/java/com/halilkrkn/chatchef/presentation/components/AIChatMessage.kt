package com.halilkrkn.chatchef.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.halilkrkn.chatchef.R
import com.halilkrkn.chatchef.data.mapper.toChatChefEntity
import com.halilkrkn.chatchef.data.remote.dto.Message
import com.halilkrkn.chatchef.presentation.ChatGptScreen.viewmodel.ChatGptViewModel
import com.halilkrkn.chatchef.ui.theme.TextPink

@Composable
fun AIChatMessage(
    modifier: Modifier = Modifier,
    message: Message? = null,
    viewModel: ChatGptViewModel = hiltViewModel(),
    fontWeight: FontWeight = FontWeight.SemiBold,
    fontSize: TextUnit = 13.sp,
    fontFamily: FontFamily = FontFamily.Default,
    loading: @Composable (() -> Unit)? = null,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
) {

    var isFavorite by rememberSaveable {
        mutableStateOf(
            message?.toChatChefEntity()?.isFavorite ?: false
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = horizontalAlignment,
    ) {
        Card(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(0.75f),
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 15.dp,
                bottomEnd = 15.dp,
                bottomStart = 15.dp
            ),
            colors = CardDefaults.cardColors(colorResource(id = R.color.ai_chat_bubble))
        ) {
            Column(
                modifier = modifier
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row {
                    if (loading != null) {
                        loading()
                    }else{
                        Text(
                            text = buildAnnotatedString {
                                append(message?.content ?: "")
                                /*withStyle(
                                    style = SpanStyle(
                                        color = TextPink
                                    )
                                ) {
                                    append("Emmanuel")
                                }
                                append(", I am ChatChef's AI. How can I help you?")*/
                            },
                            fontWeight = fontWeight,
                            fontSize = fontSize,
                            fontFamily = fontFamily,
                            textAlign = TextAlign.Start,
                            lineHeight = 20.sp,
                            modifier = Modifier
                                .weight(1f)

                        )
                        IconToggleButton(
                            checked = isFavorite,
                            onCheckedChange = {
                                isFavorite = !isFavorite
                            },
                            modifier = Modifier
                                .size(18.dp)
                                .align(Alignment.Bottom)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .clickable {
                                        if (message != null) {
                                            if (!isFavorite) {
                                                isFavorite = true
                                                viewModel.insertMessage(
                                                    message.toChatChefEntity().copy(isFavorite = true)
                                                )
                                            }
//                                        else {
//                                            viewModel.deleteMessage(message.toChatChefEntity().copy(isFavorite = false))
//                                        }
                                        }
                                    },
                                tint = TextPink,
                                imageVector = if (isFavorite) {
                                    Icons.Filled.Favorite
                                } else {
                                    Icons.Default.FavoriteBorder
                                },
                                contentDescription = null
                            )
                        }
                    }

                }
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun AiChatMessagePreview() {
    AIChatMessage()
}

