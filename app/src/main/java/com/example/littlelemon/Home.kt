package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun Home(navController: NavController, database: AppDatabase) {
    val databaseMenuItems by database.menuItemDao().getAll().observeAsState(initial = emptyList())

    Column {
        HeroSection(databaseMenuItems)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroSection(menuItemsLocal: List<MenuItemRoom>) {
    var menuItems = menuItemsLocal
    var selectedCategory by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.hero_image),
            contentDescription = "Hero Image",
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(200.dp)
        )
        Text(
            stringResource(id = R.string.restaurant_name),
            style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold)
        )
        Text(
            stringResource(id = R.string.restaurant_city),
            style = TextStyle(fontSize = 18.sp)
        )
        Text(
            stringResource(id = R.string.restaurant_desc),
            style = TextStyle(fontSize = 16.sp)
        )
        var searchPhrase by remember { mutableStateOf("") }

        OutlinedTextField(
            label = { Text(text = "Enter search phrase") },
            value = searchPhrase,
            onValueChange = { searchPhrase = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp),
            leadingIcon = {
                Icon(
                    Icons.Default.Search, contentDescription = "Search", tint = Color.Black
                )
            }
        )
        if (searchPhrase.isNotEmpty()) {
            menuItems =
                menuItems.filter { it.title.contains(searchPhrase, ignoreCase = true) }
        }

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "ORDER FOR DELIVERY!",
                modifier = Modifier.padding(top = 30.dp),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp
            )
            val scrollState = rememberScrollState()

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp)
                    .horizontalScroll(scrollState)
            ) {
                Button(
                    onClick = {
                        selectedCategory = "starters"
                    }, modifier = Modifier.height(40.dp)
                ) {
                    Text(text = "Starters", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {
                        selectedCategory = "mains"
                    }, modifier = Modifier.height(40.dp)
                ) {
                    Text(text = "Mains", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {
                        selectedCategory = "desserts"
                    }, modifier = Modifier.height(40.dp)
                ) {
                    Text(text = "Desserts", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {
                        selectedCategory = "drinks"
                    }, modifier = Modifier.height(40.dp)
                ) {
                    Text(text = "Drinks", fontWeight = FontWeight.Bold)
                }
            }
        }
        if (selectedCategory.isNotEmpty()) {
            menuItems = menuItems.filter { it.category.contains(selectedCategory) }
        }
        MenuItems(menuItems)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MenuItems(items: List<MenuItemRoom>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 20.dp)
    ) {
        items(
            items = items,
            itemContent = { menuItem ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    GlideImage(
                        model = menuItem.image,
                        contentDescription = "Menu Item Image",
                        modifier = Modifier.size(50.dp)
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                    ) {
                        Text(text = menuItem.title, fontWeight = FontWeight.Bold)
                        Text(text = menuItem.desc)
                    }

                    Text(
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.CenterVertically),
                        textAlign = TextAlign.Right,
                        text = "%.2f".format(menuItem.price)
                    )
                }
            }
        )
    }
}
