package io.ilyasin.logonex.ui.screens.products_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import io.ilyasin.logonex.R
import io.ilyasin.logonex.data.network.ProductData
import io.ilyasin.logonex.ui.theme.BackgroundLightGray
import io.ilyasin.logonex.ui.theme.DarkGreenLabel
import io.ilyasin.logonex.ui.theme.Dimens
import io.ilyasin.logonex.ui.theme.Dimens.imageSize
import io.ilyasin.logonex.ui.theme.Dimens.itemHeight
import io.ilyasin.logonex.ui.theme.Dimens.padding
import io.ilyasin.logonex.ui.theme.ImageBackgroundColor

/**
 * Products screen. It shows the list of products in the selected category
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    category: String, navController: NavController,
    viewModel: ProductsViewModel = hiltViewModel()
) {

    LaunchedEffect(category) {
        viewModel.getProductsByCategory(category)
    }
    Surface(color = BackgroundLightGray) {
        Scaffold(
            modifier = Modifier
                .padding(padding),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Blue,
                    ),
                    title = {
                        Text(stringResource(R.string.category, category))
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_button),
                                tint = Color.Black
                            )
                        }
                    }
                )
            },
        ) { innerPadding ->
            ProductList(innerPadding, viewModel)
        }
    }
}

@Composable
fun ProductList(innerPadding: PaddingValues, viewModel: ProductsViewModel) {
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .background(BackgroundLightGray), contentAlignment = Alignment.Center
    ) {

        val listState = rememberLazyListState()
        LazyColumn(
            state = listState, modifier = Modifier
                .fillMaxSize()
                .padding(top = padding)
        ) {
            items(viewModel.products.value.size) { index ->
                ProductItem(
                    product = viewModel.products.value[index]
                )
                Spacer(modifier = Modifier.height(Dimens.listDividerHeight))
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductItem(product: ProductData) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(Dimens.cornerRadius)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .height(itemHeight)
                .padding(padding)

        ) {
            val (imageRef, titleRef, distinctRef, totalRef) = createRefs()
            GlideImage(
                model = product.thumbnail,
                contentDescription = stringResource(R.string.thumbnail),
                failure = placeholder(R.drawable.ic_image_error),
                modifier = Modifier
                    .size(imageSize)
                    .padding(end = padding)
                    .clip(RoundedCornerShape(Dimens.smallCornerRadius))
                    .background(ImageBackgroundColor)
                    .constrainAs(imageRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    },

                )

            Text(text = product.title, maxLines = 1, overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = Dimens.titleTextSize,
                modifier = Modifier.constrainAs(titleRef) {
                    top.linkTo(parent.top)
                    start.linkTo(imageRef.end)
                    end.linkTo(parent.end)
                    bottom.linkTo(distinctRef.top)
                    width = Dimension.fillToConstraints
                })

            Text(text = stringResource(R.string.price, product.formattedPrice()),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray,
                fontSize = Dimens.subtitleTextSize,
                modifier = Modifier.constrainAs(distinctRef) {
                    top.linkTo(titleRef.bottom)
                    start.linkTo(imageRef.end)
                    bottom.linkTo(parent.bottom)
                })

            Text(text = stringResource(R.string.in_stock, product.stock), maxLines = 1, overflow = TextOverflow.Ellipsis,
                color = DarkGreenLabel,
                fontSize = Dimens.subtitleTextSize,
                modifier = Modifier.constrainAs(totalRef) {
                    top.linkTo(titleRef.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                })
        }
    }
}