/*
 * Copyright (c) 2023 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.element.android.x.features.logout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import io.element.android.libraries.architecture.Async
import io.element.android.libraries.architecture.Presenter
import io.element.android.libraries.architecture.execute
import io.element.android.libraries.matrix.MatrixClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogoutPreferencePresenter @Inject constructor(private val matrixClient: MatrixClient) :
    Presenter<LogoutPreferenceState> {

    @Composable
    override fun present(): LogoutPreferenceState {
        val localCoroutineScope = rememberCoroutineScope()
        val logoutAction: MutableState<Async<Unit>> = remember {
            mutableStateOf(Async.Uninitialized)
        }

        fun handleEvents(event: LogoutPreferenceEvents) {
            when (event) {
                LogoutPreferenceEvents.Logout -> localCoroutineScope.logout(logoutAction)
            }
        }

        return LogoutPreferenceState(
            logoutAction = logoutAction.value,
            eventSink = ::handleEvents
        )
    }

    private fun CoroutineScope.logout(logoutAction: MutableState<Async<Unit>>) = launch {
        suspend {
            matrixClient.logout()
        }.execute(logoutAction)
    }
}
