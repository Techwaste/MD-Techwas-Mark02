package com.capstone.techwasmark02.ui.screen.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.techwasmark02.common.Resource
import com.capstone.techwasmark02.data.mappers.toUserSession
import com.capstone.techwasmark02.data.model.UserLoginInfo
import com.capstone.techwasmark02.data.remote.response.UserLoginResponse
import com.capstone.techwasmark02.repository.PreferencesRepository
import com.capstone.techwasmark02.repository.TechwasUserApiRepository
import com.capstone.techwasmark02.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInScreenViewModel @Inject constructor(
    private val userApiRepository: TechwasUserApiRepository,
    private val preferencesRepository: PreferencesRepository
): ViewModel() {

    private val _userToSignInState: MutableStateFlow<UiState<UserLoginResponse>?> = MutableStateFlow(null)
    val userToSignInState = _userToSignInState.asStateFlow()

    private val _userToSignInInfo: MutableStateFlow<UserLoginInfo> =
        MutableStateFlow(UserLoginInfo(
            email = "",
            password = ""
        ))
    val userToSignInInfo = _userToSignInInfo.asStateFlow()

    private val _savedUsername: MutableStateFlow<String?> = MutableStateFlow(null)
    val savedUsername = _savedUsername.asStateFlow()

    fun signInUser() {
        _userToSignInState.value = UiState.Loading()
        viewModelScope.launch {
            _userToSignInState.value = userApiRepository.userLogin(_userToSignInInfo.value)
        }
    }

    fun saveUserSession() {
        viewModelScope.launch {
            val userSession = _userToSignInState.value?.data?.loginResult?.toUserSession()
            if (userSession != null) {
                val result = preferencesRepository.saveSession(userSession)
                when(result) {
                    is Resource.Error -> {
                        _savedUsername.value = ""
                    }
                    is Resource.Success -> {
                        _savedUsername.value = result.data?.userNameId?.username
                    }
                }
            }
        }
    }

    fun updateUserSignInInfo(userToSignInInfo: UserLoginInfo) {
        _userToSignInInfo.value = userToSignInInfo
    }
}