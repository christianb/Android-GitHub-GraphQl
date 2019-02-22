package de.bunk.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ScalarType
import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.response.CustomTypeValue
import okhttp3.OkHttpClient
import type.CustomType
import java.net.URI
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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
        val uriAdapter = object : CustomTypeAdapter<URI> {
            override fun decode(value: CustomTypeValue<*>): URI {
                return URI.create(value.value.toString())
            }

            override fun encode(value: URI): CustomTypeValue<*> {
                return CustomTypeValue.GraphQLString(value.toString())
            }
        }

//        val dateTimeAdapter = object : CustomTypeAdapter<ZonedDateTime> {
//            override fun decode(value: CustomTypeValue<*>): ZonedDateTime {
//                val dateFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
//                return ZonedDateTime.parse(value.value.toString(), dateFormatter)
//            }
//
//            override fun encode(value: ZonedDateTime): CustomTypeValue<*> {
//                val dateFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
//                return CustomTypeValue.GraphQLString(dateFormatter.format(value))
//            }
//        }

        return ApolloClient.builder()
            .serverUrl(BASE_URL)
            .addCustomTypeAdapter(CustomType.URI, uriAdapter)
//            .addCustomTypeAdapter(CustomType.DATETIME, dateTimeAdapter)
            .okHttpClient(createGraphQlOkHttpClient(apiKey))
            .build()
    }
}