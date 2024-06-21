package com.dicoding.moodmate.data.response

import com.google.gson.annotations.SerializedName

data class PromptResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("moodmateResponse")
	val moodmateResponse: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("prompt")
	val prompt: String? = null
)
