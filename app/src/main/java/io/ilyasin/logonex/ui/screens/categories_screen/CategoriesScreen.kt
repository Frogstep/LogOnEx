package io.ilyasin.logonex.ui.screens.categories_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import io.ilyasin.logonex.R
import io.ilyasin.logonex.data.LoadingState
import io.ilyasin.logonex.data.network.CategoryData
import io.ilyasin.logonex.ui.Screen
import io.ilyasin.logonex.ui.theme.BackgroundLightGray
import io.ilyasin.logonex.ui.theme.Dimens.cornerRadius
import io.ilyasin.logonex.ui.theme.Dimens.imageSize
import io.ilyasin.logonex.ui.theme.Dimens.itemHeight
import io.ilyasin.logonex.ui.theme.Dimens.listDividerHeight
import io.ilyasin.logonex.ui.theme.Dimens.padding
import io.ilyasin.logonex.ui.theme.Dimens.smallCornerRadius
import io.ilyasin.logonex.ui.theme.Dimens.smallPadding
import io.ilyasin.logonex.ui.theme.Dimens.subtitleTextSize
import io.ilyasin.logonex.ui.theme.Dimens.titleTextSize
import io.ilyasin.logonex.ui.theme.ErrorBackgroundColor
import io.ilyasin.logonex.ui.theme.ImageBackgroundColor


@Composable
fun CategoriesScreen(navController: NavController, viewModel: CategoriesViewModel = hiltViewModel()) {
    CategoriesScreenContent(navController, viewModel.categories, viewModel.state, viewModel::reDownloadProducts)
}

/**
 * Screen contains list of categories
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreenContent(
    navController: NavController,
    listState: State<List<CategoryData>>,
    loadingState: State<LoadingState>,
    downloadProducts: () -> Unit
) {
    Surface(color = BackgroundLightGray) {
        Scaffold(
            modifier = Modifier
                .padding(top = padding, bottom = padding, start = padding, end = padding),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Blue,
                    ),
                    title = {
                        Text(stringResource(R.string.categories))
                    },
                    actions = {
                        IconButton(onClick = { downloadProducts() }) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = stringResource(R.string.reload),
                                tint = Color.Black
                            )
                        }
                    }
                )
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(BackgroundLightGray), contentAlignment = Alignment.Center
            ) {
                when (loadingState.value) {
                    LoadingState.InProgress, LoadingState.Initialized -> Progress()
                    LoadingState.Success -> CategoriesList(listState, navController = navController)
                    is LoadingState.Failure -> ErrorView(listState, navController = navController)
                }
            }
        }
    }
}

@Composable
fun Progress() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color.Blue)
    }
}

@Composable
fun ErrorView(state: State<List<CategoryData>>, navController: NavController) {
    if (state.value.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.failed_to_load_data),
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Red, shape = RoundedCornerShape(5.dp))
                    .padding(padding)
            )
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.padding(top = padding)) {
                Text(
                    text = stringResource(R.string.failed_to_load_data_showing_cache),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = ErrorBackgroundColor, shape = RoundedCornerShape(5.dp))
                        .padding(top = smallPadding, bottom = smallPadding)
                )
            }
            Spacer(modifier = Modifier.height(listDividerHeight))
            CategoriesList(state, navController = navController)
        }
    }
}

@Composable
fun CategoriesList(state: State<List<CategoryData>>, navController: NavController) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState, modifier = Modifier
            .fillMaxSize()
            .padding(top = padding)
    ) {
        items(state.value.size, key = { index -> state.value[index].category }) { index ->
            CategoryItem(
                category = state.value[index], navController
            )
            Spacer(modifier = Modifier.height(listDividerHeight))
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CategoryItem(category: CategoryData, navController: NavController) {

    Card(
        modifier = Modifier.clickable {
            navController.navigate("${Screen.Products.route}/${category.category}")
        },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(cornerRadius)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .height(itemHeight)
                .padding(padding)
        ) {
            val (imageRef, categoryRef, distinctRef, totalRef) = createRefs()
            GlideImage(
                model = category.imageUrl,
                contentDescription = stringResource(R.string.thumbnail),
                failure = placeholder(R.drawable.ic_image_error),
                modifier = Modifier
                    .size(imageSize)
                    .padding(end = padding)
                    .clip(RoundedCornerShape(smallCornerRadius))
                    .background(ImageBackgroundColor)
                    .constrainAs(imageRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Text(text = category.category, maxLines = 1, overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = titleTextSize,
                modifier = Modifier.constrainAs(categoryRef) {
                    top.linkTo(parent.top)
                    start.linkTo(imageRef.end)
                    bottom.linkTo(distinctRef.top)
                })

            Text(text = stringResource(R.string.distinct_count, category.distinctProducts),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray,
                fontSize = subtitleTextSize,
                modifier = Modifier.constrainAs(distinctRef) {
                    top.linkTo(categoryRef.bottom)
                    start.linkTo(imageRef.end)
                    bottom.linkTo(parent.bottom)
                })

            Text(text = stringResource(R.string.total_count, category.totalSum),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Blue,
                fontSize = subtitleTextSize,
                modifier = Modifier.constrainAs(totalRef) {
                    top.linkTo(categoryRef.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                })
        }
    }
}


