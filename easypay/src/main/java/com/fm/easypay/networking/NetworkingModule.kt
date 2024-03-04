package com.fm.easypay.networking

import com.fm.easypay.BuildConfig
import com.fm.easypay.EasyPayConfiguration
import com.fm.easypay.networking.interceptors.NetworkInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object NetworkingModule {

    val networkingModules = module {
        single { provideGson() }
        single { provideCertificatePinner() }
        single { provideApiClient(get(), get()) }
        single { provideRetrofit(get(), get(), get()) }
        single { provideNetworkDataSource() }
        single { provideNetworkInterceptor() }
        single { provideApiUrl() }
    }

    private fun provideGson(): Gson = GsonBuilder().create()

    // TODO: Implement proper certificate pinning
    private fun provideCertificatePinner(): CertificatePinner = CertificatePinner
        .Builder()
        .build()

    private fun provideApiClient(
        certificatePinner: CertificatePinner,
        networkInterceptor: NetworkInterceptor,
    ): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addNetworkInterceptor(networkInterceptor)
            // TODO: Uncomment the following line to enable certificate pinning
//        .certificatePinner(certificatePinner)
            .build()

    private fun provideApiUrl(): String =
        BuildConfig.API_URL + EasyPayConfiguration.API_VERSION + "/"

    private fun provideRetrofit(apiClient: OkHttpClient, gson: Gson, apiUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(apiClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    private fun provideNetworkDataSource(): NetworkDataSource = DefaultNetworkDataSource()

    private fun provideNetworkInterceptor() = NetworkInterceptor()
}