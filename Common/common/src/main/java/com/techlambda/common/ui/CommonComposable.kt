package com.techlambda.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun CardText(
    text: String,
    isBold: Boolean = false
) {
    Text(
        text = text,
        fontWeight = if(isBold) FontWeight.Bold else null,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun InputField(
    label: String,
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(text = label, color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        TextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF7F8F9),
                unfocusedContainerColor = Color(0xFFF7F8F9),
                focusedIndicatorColor = Color(0xFFD9DDE3),
                unfocusedIndicatorColor = Color(0xFFD9DDE3)
            )
        )
    }
}

@Composable
fun OutlinedInputField(
    label: String, modifier: Modifier = Modifier, value: String,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(text = label, color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(top = 6.dp),
        )
    }
}

@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier, onClick = onClick, shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = text, color = Color.White, fontSize = 16.sp)
    }
}

@Composable
fun NetworkImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    Box(modifier = modifier) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build()
        )
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
        )
    }
}

@Composable
fun UploadImageField(modifier: Modifier = Modifier, label: String, imageUrl: String, onCLick: () -> Unit = {}) {
    Column(modifier = modifier) {
        Text(
            text = "Upload Image",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 6.dp)
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(6.dp))
                .padding(16.dp)
                .clickable {
                    onCLick()
                }, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = imageUrl,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Icon(
                modifier = Modifier,
                imageVector = Icons.Default.Upload,
                contentDescription = label,
                tint = Color.Blue
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    isListScreen: Boolean,
    isEdit: Boolean,
    isAdmin: Boolean,
    backgroundColor: Color,
    navigateToAddScreen: () -> Unit,
    navigateToBack: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(topBar = {
        Column {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 10.dp),
                title = {
                    Text(
                        if(isListScreen) "List View & Add" else if (isEdit) "Edit" else "Add",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    if (isAdmin && isListScreen) {
                        IconButton(modifier = Modifier
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .background(color = backgroundColor),
                            onClick = navigateToAddScreen
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.White
                            )
                        }
                    }else {
                        TextButton(onClick = { navigateToBack() }) {
                            Text(text = "Cancel", fontWeight = FontWeight.SemiBold)
                        }
                    }
                },
                navigationIcon = {
                    if(!isListScreen) {
                        IconButton(onClick = { navigateToBack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
            )
        }
    }) { paddingValues ->
        content(paddingValues)
    }
}

@Composable
fun CommonContentCard(
    modifier: Modifier,
    imageUrl: String?,
    isAdmin: Boolean,
    onDeleteClicked: () -> Unit,
    onEditClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .then(modifier),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 14.dp, horizontal = 14.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            if (imageUrl != null) {
                NetworkImage(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(50.dp),
                    imageUrl = imageUrl,
                    contentScale = ContentScale.FillBounds
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                content()
                if (isAdmin) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    ) {
                        Text(
                            text = "Delete",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Red,
                            modifier = Modifier.clickable { onDeleteClicked() }
                        )
                    }
                }
            }
            if (isAdmin) {
                Text(
                    text = "Edit",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable { onEditClicked() }
                )
            }
        }
    }
}