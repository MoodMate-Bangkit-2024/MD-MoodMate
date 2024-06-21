package com.dicoding.moodmate.data.response

import com.google.gson.annotations.SerializedName

data class MusicResponse(

	@field:SerializedName("data")
	val data: List<MusicItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("category")
	val category: String? = null
)

data class MusicItem(

	@field:SerializedName("singer")
	val singer: String? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
