package hu.me.iit.tankopedia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tank(
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "nation") val nation: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "year") val year: Int?,
    @ColumnInfo(name = "gun") val gun: String?,
    @ColumnInfo(name = "engine") val engine: String?,
    @ColumnInfo(name = "frontArmour") val frontArmour: Int?,
    @ColumnInfo(name = "sideArmour") val sideArmour: Int?,
    @ColumnInfo(name = "rearArmour") val rearArmour: Int?,
    @ColumnInfo(name = "crew") val crew: Int?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "description") val description: String?,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)
