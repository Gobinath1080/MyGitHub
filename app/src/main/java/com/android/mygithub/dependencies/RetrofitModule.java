package com.android.mygithub.dependencies;

import android.util.Base64;

import com.android.mygithub.utilities.Constants;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    @Provides
    Retrofit getRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    OkHttpClient getOkHttpClient(Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                // Add auth token by default to all the request.
                Request request = chain.request().newBuilder()
                        .header("Authorization", getAuthToken())
                        .build();
                return chain.proceed(request);
            }
        };
    }

    /**
     * @return Base64 encoded version of Git hub personal token.
     */
    String getAuthToken() {
        byte[] token = new byte[0];
        try {
            token = (Constants.GITHUB_PERSONAL_TOKEN + ":").getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "Basic " + Base64.encodeToString(token, Base64.NO_WRAP);
    }
}
