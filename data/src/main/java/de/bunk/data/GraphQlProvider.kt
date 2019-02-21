package de.bunk.view

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient

object GraphQlProvider {
    fun provideApolloClient(): ApolloClient =
        createApolloClient(createGraphQlOkHttpClient(), "https://api.github.com/graphql")

    private fun createGraphQlOkHttpClient(): OkHttpClient {
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
                    "Bearer " + "4461fa5ea6e8823ee7a328c84d38b641156955fb"
                )
                chain.proceed(builder.build())
            }
            .build()
    }

    private fun createApolloClient(okHttpClient: OkHttpClient, baseUrl: String): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(baseUrl)
            .okHttpClient(okHttpClient)
            .build()
    }
}