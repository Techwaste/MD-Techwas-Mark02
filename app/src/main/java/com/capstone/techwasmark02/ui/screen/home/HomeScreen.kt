package com.capstone.techwasmark02.ui.screen.home

import android.widget.Space
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavHostController
import com.capstone.techwasmark02.R
import com.capstone.techwasmark02.ui.component.AboutUsBox
import com.capstone.techwasmark02.ui.component.ArticleCardBig
import com.capstone.techwasmark02.ui.component.DefaultBottomBar
import com.capstone.techwasmark02.ui.component.FeatureBox
import com.capstone.techwasmark02.ui.componentType.BottomBarItemType
import com.capstone.techwasmark02.ui.componentType.FeatureBoxType
import com.capstone.techwasmark02.ui.navigation.Screen
import com.capstone.techwasmark02.ui.theme.TechwasMark02Theme
import com.capstone.techwasmark02.ui.theme.yellow
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    HomeContent(
        navigateToCamera = { navController.navigate(Screen.Camera.route) }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    navigateToCamera: () -> Unit
) {
    Scaffold(
    ) { innerPadding ->
        val scrollState = rememberScrollState()

        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(top = 20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Image(
                            modifier = Modifier.size(24.dp, 27.dp),
                            painter = painterResource(id = R.drawable.logo_techwaste),
                            contentDescription = null
                        )
                        Text(
                            text = "Techwas",
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .offset(y = 5.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = { },
//                        modifier = Modifier.size(21.dp, 24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_nofitications),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp
                            )
                        )
                        .background(Color.White)
                        .padding(bottom = 72.dp)
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 18.dp)
                        ) {
                            Text(
                                text = "Features",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            FeatureBox(featureBoxType = FeatureBoxType.Detection, onClick = navigateToCamera)
                            Spacer(modifier = Modifier.weight(3f))
                            FeatureBox(featureBoxType = FeatureBoxType.Catalog, onClick = {})
                            Spacer(modifier = Modifier.weight(1f))
//                        DetectBox1()
//                        DetectBox2()
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            FeatureBox(featureBoxType = FeatureBoxType.DropPoint, onClick = {})
                            Spacer(modifier = Modifier.weight(3f))
                            AboutUsBox()
                            Spacer(modifier = Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Articles",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = "See all",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = yellow
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    val pagerState = rememberPagerState()
                    HorizontalPager(
                        pageCount = 10,
                        state = pagerState,
                        contentPadding = PaddingValues(horizontal = 70.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) { page ->
                        ArticleCardBig(
                            modifier = Modifier
                                .width(272.dp)
                                .height(139.dp)
                                .graphicsLayer {
                                    // Calculate the absolute offset for the current page from the
                                    // scroll position. We use the absolute value which allows us to mirror
                                    // any effects for both directions
                                    val pageOffset = (
                                            (pagerState.currentPage - page) + pagerState
                                                .currentPageOffsetFraction
                                            ).absoluteValue

                                    lerp(
                                        start = 0.85f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    ).also { scale ->
                                        scaleX = scale
                                        scaleY = scale
                                    }
                                    alpha = lerp(
                                        start = 0.5f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )
                                }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Forums",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = "See all",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = yellow
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ArticleCardBig(
                                modifier = Modifier
                                    .width(320.dp)
                                    .height(161.dp)
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                DefaultBottomBar(selectedType = BottomBarItemType.Home)
            }
        }
    }
}

@Preview
@Composable
fun HomeContentPreview() {
    TechwasMark02Theme {
        HomeContent(
            navigateToCamera = {}
        )
    }
}