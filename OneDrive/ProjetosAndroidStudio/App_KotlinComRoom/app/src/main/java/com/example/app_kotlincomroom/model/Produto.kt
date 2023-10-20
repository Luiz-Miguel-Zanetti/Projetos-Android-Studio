package com.example.app_kotlincomroom.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.app_kotlincomroom.model.Usuario
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal


@Entity
@Parcelize
data class Produto(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0L,
        val nome: String,
        val descricao: String,
        val valor: BigDecimal,
        val imagem: String? = null,
        val usuarioId: String? = null
) : Parcelable