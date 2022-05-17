package com.sliidepoc.ui.features.users

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sliidepoc.common.utils.formater.DateTimeUtils
import com.sliidepoc.common.utils.formater.StringUtils
import com.sliidepoc.domain.api.data.mapper.dto.model.UserDto
import com.sliidepoc.ui.R
import com.sliidepoc.ui.components.DrawContentScreenState
import com.sliidepoc.ui.components.LoadingComponent
import com.sliidepoc.ui.features.users.models.UsersScreenStateModel
import com.sliidepoc.ui.navigation.NavPathRouteItem
import com.sliidepoc.ui.themes.Shapes
import com.sliidepoc.ui.utils.Constants.TEST_TAG_USERS_LIST
import com.sliidepoc.ui.utils.DataLoadingStates
import com.sliidepoc.ui.utils.ScreenLifeCycle

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.01.21
 */
@Suppress("UNUSED_PARAMETER")
@Composable
fun DisplayNewsScreen(
    usersViewModel: UsersViewModel,
    navController: NavHostController,
    drawContentState: DrawContentScreenState
) {
    ScreenLifeCycle(usersViewModel, LocalLifecycleOwner.current).run {

        setScreenProfile(drawContentState)

        var openAddDialog by remember {
            mutableStateOf(false)
        }
        var deleteDialogUserId by remember {
            mutableStateOf(-1)
        }

        val listState = rememberLazyListState()
        val usersScreenState: UsersScreenStateModel = rememberNewsScreenStateHolder(
            posts = usersViewModel.users.collectAsState(emptyList()),
            dataState = usersViewModel.dataState.collectAsState(DataLoadingStates.START)
        )
        Init(usersViewModel, usersScreenState, listState)
        Content(
            usersScreenState,
            listState,
            { openAddDialog = true },
            { id -> deleteDialogUserId = id })

        if (openAddDialog) {
            OpenAddDialog({ name, email ->
                usersViewModel.addUser(name, email)
                openAddDialog = false
            }, {
                openAddDialog = false
            })
        }
        if (deleteDialogUserId != -1) {
            OpenDeleteDialog(deleteDialogUserId, {
                usersViewModel.deleteUser(it)
                deleteDialogUserId = -1
            }, { deleteDialogUserId = -1 })
        }
    }
}

@Composable
private fun OpenAddDialog(onSave: (String, String) -> Unit, onCancel: () -> Unit) {
    UserAddDialog(onCancel, onSave)
}

@Composable
private fun OpenDeleteDialog(userId: Int, onExecute: (Int) -> Unit, onCancel: () -> Unit) {
    UserRemoveDialog(userId, onExecute, onCancel)
}

@Composable
private fun Init(
    usersViewModel: UsersViewModel,
    usersScreenState: UsersScreenStateModel,
    listState: LazyListState
) {
    if (usersScreenState.getDataState() == DataLoadingStates.START) {
        usersViewModel.loadUsers()
    }
    LaunchedEffect(key1 = listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            if (listState.firstVisibleItemScrollOffset > 0) {
                usersScreenState.setIndex(listState.firstVisibleItemIndex)
                usersScreenState.setOffset(listState.firstVisibleItemScrollOffset)
            }
        }
    }
    LaunchedEffect(usersScreenState.listIndexItem.value) {
        listState.animateScrollToItem(usersScreenState.getIndex(), usersScreenState.getOffset())
    }
}

@Composable
private fun Content(
    usersScreenState: UsersScreenStateModel,
    listState: LazyListState,
    onClick: () -> Unit,
    onLongClick: (id: Int) -> Unit
) {
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.onPrimary)) {

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            // Display the users list
            LoadingComponent(usersScreenState.getDataState()) {
                GridData(listState, usersScreenState.getPosts(), onLongClick)
            }

            // Floating button to add
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .align(Alignment.BottomEnd)
                    .offset((-20).dp, (-20).dp)
            ) {
                if (usersScreenState.getDataState() == DataLoadingStates.SUCCESS) {
                    FloatingActionButton(
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .clip(RoundedCornerShape(50)),
                        onClick = {
                            onClick()
                        }
                    ) {
                        Icon(
                            Icons.Filled.Add, StringUtils.EMPTY_STRING,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridData(
    listState: LazyListState,
    users: List<UserDto>,
    onLongClick: (id: Int) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 300.dp),
        state = listState,
        contentPadding = PaddingValues(5.dp),
        modifier = Modifier.testTag(TEST_TAG_USERS_LIST)
    ) {
        items(users.size) { index ->
            val user = users[index]
            UserCard(
                user.extId,
                user.name,
                user.email,
                user.creationDateTime,
                onLongClick
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun UserCard(
    id: Int,
    name: String,
    email: String?,
    creationDate: String?,
    onLongClick: (id: Int) -> Unit
) {
    Card(
        modifier = Modifier
            .combinedClickable(
                onClick = {},
                onLongClick = {
                    onLongClick(id)
                }
            )
            .padding(5.dp)
            .heightIn(max = 100.dp)
            .fillMaxHeight(),
        shape = Shapes.small,
        elevation = 5.dp,
        backgroundColor =
        if (creationDate != null)
            MaterialTheme.colorScheme.secondaryContainer
        else MaterialTheme.colorScheme.surface
    ) {
        Column(
            Modifier
                .padding(8.dp)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                color = if (creationDate != null)
                    MaterialTheme.colorScheme.onSecondaryContainer
                else MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text =
                if (creationDate != null) DateTimeUtils.getFormattedTime(creationDate.toLong())
                else stringResource(R.string.date_creation_unknown),
                style = MaterialTheme.typography.bodyMedium,
                color = if (creationDate != null)
                    MaterialTheme.colorScheme.onSecondaryContainer
                else MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = email ?: StringUtils.EMPTY_STRING,
                style = MaterialTheme.typography.bodyLarge,
                color = if (creationDate != null)
                    MaterialTheme.colorScheme.onSecondaryContainer
                else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun rememberNewsScreenStateHolder(
    posts: State<List<UserDto>>,
    dataState: State<DataLoadingStates>
) = rememberSaveable(
    saver = listSaver<UsersScreenStateModel, Any>(save = {
        listOf(it.users, it.dataState, it.listIndexItem, it.listOffsetItem)
    }, restore = {
        @Suppress("UNCHECKED_CAST") UsersScreenStateModel(
            it[0] as State<List<UserDto>>,
            it[1] as State<DataLoadingStates>,
            it[2] as MutableState<Int>,
            it[3] as MutableState<Int>
        )
    })
) {
    UsersScreenStateModel(
        users = posts,
        dataState = dataState,
        listIndexItem = mutableStateOf(0),
        listOffsetItem = mutableStateOf(0)
    )
}

private fun setScreenProfile(drawContentState: DrawContentScreenState) {
    drawContentState.setMenuItem(NavPathRouteItem.Users.route)
    drawContentState.setTopMenuTitle(NavPathRouteItem.Users.titleRes)
}
