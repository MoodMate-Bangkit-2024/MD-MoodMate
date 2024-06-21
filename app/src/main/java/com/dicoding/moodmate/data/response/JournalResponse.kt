package com.dicoding.moodmate.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class JournalResponse(
	@field:SerializedName("data")
	val data: List<JournalData>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class JournalData(
	@field:SerializedName("createdAt")
	var createdAt: String? = null,

	@field:SerializedName("mood")
	val mood: String?,

	@field:SerializedName("author")
	var author: String?,

	@field:SerializedName("__v")
	var v: Int?,

	@field:SerializedName("_id")
	var id: String?,

	@field:SerializedName("text")
	var text: String? = null,

	@field:SerializedName("title")
	var title: String? = null,

	@field:SerializedName("updatedAt")
	var updatedAt: String? = null
) : Parcelable

@Parcelize
data class SingleJournalResponse(
	@field:SerializedName("data")
	val data: SingleJournalData? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class SingleJournalData(
	@field:SerializedName("createdAt")
	var createdAt: String,
	@field:SerializedName("updatedAt")
	var updatedAt: String?,
	@field:SerializedName("title")
	var title: String,
	@field:SerializedName("text")
	var text: String,
	@field:SerializedName("mood")
	var mood: String,
	@field:SerializedName("author")
	var author: String,
	@field:SerializedName("_id")
	var id: String,
	@field:SerializedName("__v")
	var v: Int,
) : Parcelable