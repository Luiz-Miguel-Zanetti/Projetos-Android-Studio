package com.example.app_kotlincomroom.preferences.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


val Context.datastore : DataStore<Preferences> by preferencesDataStore(name ="settings")

val usuarioLogadoPreferences = stringPreferencesKey("usuarioLogado")