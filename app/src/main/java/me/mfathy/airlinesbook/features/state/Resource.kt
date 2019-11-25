package me.mfathy.airlinesbook.features.state


/**
 * Resource callback to handle different resource states.
 */
class Resource<out T> constructor(val status: ResourceState,
                                  val data: T?,
                                  val message: String?,
                                  val error: Throwable?) {

    fun <T> success(data: T): Resource<T> {
        return Resource(ResourceState.SUCCESS, data, null, null)
    }

    fun <T> error(message: String?): Resource<T> {
        return Resource(ResourceState.ERROR, null, message, null)
    }

    fun <T> loading(): Resource<T> {
        return Resource(ResourceState.LOADING, null, null, null)
    }

}