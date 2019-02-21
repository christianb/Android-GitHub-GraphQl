package de.bunk.data

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient

private const val BASE_URL: String = "https://api.github.com/graphql"

object GraphQlProvider {
    fun provideApolloClient(apiKey: ApiKey): ApolloClient = createApolloClient(apiKey)

    private fun createGraphQlOkHttpClient(apiKey: ApiKey): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(
                    original.method(),
                    original.body()
                )
                builder.addHeader(
                    "Authorization",
                    "Bearer ${apiKey.value}"
                )
                chain.proceed(builder.build())
            }
            .build()
    }

    private fun createApolloClient(apiKey: ApiKey): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(createGraphQlOkHttpClient(apiKey))
            .build()
    }
}