package com.jax.movies.presentation.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jax.movies.domain.entity.films.Actor


@Composable
fun ActorItem(
    onActorClick: (Actor) -> Unit,
    actor: Actor,
    modifierForParent: Modifier = Modifier,
    modifierForImage: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifierForParent
    ) {
        FetchedImage(
            linkToImage = actor.posterUrl,
            modifierForParent = modifierForImage
                .width(49.dp)
                .height(68.dp)
                .clickable {
                    onActorClick(actor)
                    Log.d("dsaadas", actor.actorId.toString())
                }
        )
        Column {
            Text(
                text = actor.nameRu,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                color = Color(0xFF272727),
                overflow = TextOverflow.Ellipsis,
                lineHeight = 13.2.sp,
                modifier = Modifier.sizeIn(maxWidth = 126.dp)
            )
            Text(
                text = actor.nameEn,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = Color(0xFFB5B5C9),
                overflow = TextOverflow.Ellipsis,
                lineHeight = 13.2.sp,
                modifier = Modifier.sizeIn(maxWidth = 126.dp)
            )
        }
    }
}