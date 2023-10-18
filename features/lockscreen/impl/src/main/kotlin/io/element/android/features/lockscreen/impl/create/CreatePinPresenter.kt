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

package io.element.android.features.lockscreen.impl.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import io.element.android.features.lockscreen.impl.create.model.PinEntry
import io.element.android.libraries.architecture.Presenter
import javax.inject.Inject

class CreatePinPresenter @Inject constructor() : Presenter<CreatePinState> {

    @Composable
    override fun present(): CreatePinState {
        val pinEntry by remember {
            mutableStateOf(PinEntry.empty(4))
        }

        fun handleEvents(event: CreatePinEvents) {
            when (event) {
                is CreatePinEvents.OnPinEntryChanged -> {
                    pinEntry.fillWith(event.entryAsText)
                }
            }
        }

        return CreatePinState(
            pinEntry = pinEntry,
            eventSink = ::handleEvents
        )
    }
}
