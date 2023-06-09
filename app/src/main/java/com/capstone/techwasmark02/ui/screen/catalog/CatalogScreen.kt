package com.capstone.techwasmark02.ui.screen.catalog

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.capstone.techwasmark02.data.remote.response.Component
import com.capstone.techwasmark02.data.remote.response.ComponentsResponse
import com.capstone.techwasmark02.ui.common.UiState
import com.capstone.techwasmark02.ui.component.CatalogCard
import com.capstone.techwasmark02.ui.component.InverseTopBar
import com.capstone.techwasmark02.ui.component.SearchBox
import com.capstone.techwasmark02.ui.navigation.Screen
import com.capstone.techwasmark02.ui.theme.TechwasMark02Theme
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@Composable
fun CatalogScreen(
    viewModel: CatalogScreenViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val componentsState by viewModel.componentState.collectAsState()
    val searchBoxValue by viewModel.searchBoxValue.collectAsState()

    CatalogContent(
        componentsState = componentsState,
        navigateToSingleComponent = { navController.navigate("${Screen.SingleCatalog.route}/$it") },
        searchBoxValue = searchBoxValue,
        onSearchBoxValueChange = { viewModel.updateSearchBoxValue(it) },
        navigateBackToMain = { navController.navigate("${Screen.Main.route}/0") }
    )
}

@Composable
fun CatalogContent(
    componentsState: UiState<ComponentsResponse>?,
    navigateToSingleComponent: (component: String) -> Unit,
    searchBoxValue: String,
    onSearchBoxValueChange: (String) -> Unit,
    navigateBackToMain: () -> Unit
) {

    BackHandler(true) {
        navigateBackToMain()
    }

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val adapter = moshi.adapter(Component::class.java)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 60.dp)
        ) {
            Text(
                text = "Catalog",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Find your e-waste component here",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            SearchBox(onValueChange = onSearchBoxValueChange, value = searchBoxValue)

            Spacer(modifier = Modifier.height(16.dp))

            if (componentsState != null) {
                when(componentsState) {
                    is UiState.Success -> {
                        val components = componentsState.data?.components

                        var filteredComponents by remember {
                            mutableStateOf(components)
                        }

                        LaunchedEffect(key1 = searchBoxValue) {
                            if (components != null) {
                                filteredComponents = searchComponent(
                                    componentList = components,
                                    searchBoxValue = searchBoxValue
                                )
                            }
                        }

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            filteredComponents?.size?.let {
                                items(
                                    count = it,
                                ) {index ->
                                    CatalogCard(
                                        component = filteredComponents!![index],
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                val componentJson =
                                                    Uri.encode(adapter.toJson(filteredComponents!![index]))
                                                navigateToSingleComponent(componentJson)
                                            },
                                    )
                                }
                            }
                        }
                    }
                    is UiState.Error -> {

                    }
                    is UiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }

        InverseTopBar(
            onClickNavigationIcon = navigateBackToMain,
            pageTitle = "Catalog",
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }
}

private fun searchComponent(componentList: List<Component>, searchBoxValue: String) : List<Component> {
    if (searchBoxValue == "") {
        return componentList
    }
    return componentList.filter { component ->
        component.name.contains(searchBoxValue, ignoreCase = true)
    }
}

@Preview (showBackground = true)
@Composable
fun CatalogScreenPreview() {
    TechwasMark02Theme {
        CatalogContent(
            componentsState = UiState.Loading(),
            navigateToSingleComponent = {},
            searchBoxValue = "",
            onSearchBoxValueChange = {},
            navigateBackToMain = {}
        )
    }
}