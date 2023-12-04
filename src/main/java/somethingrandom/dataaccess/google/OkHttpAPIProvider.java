package somethingrandom.dataaccess.google;

import okhttp3.*;
import org.json.JSONObject;
import somethingrandom.dataaccess.google.auth.AuthenticationException;
import somethingrandom.dataaccess.google.auth.Token;

import java.io.IOException;

/**
 * OkHttpAPIProvider is an API provider that uses the OkHttp3 library to make
 * its requests.
 * <p>
 * This is the primary one used in Brainsweep as of writing.
 */
class OkHttpAPIProvider implements APIProvider {
    private final OkHttpClient client;
    private final Token token;

    /**
     * Creates an API provider for the given HTTP client and token.
     *
     * @param client The client to execute requests with.
     * @param token The token to authenticate requests with.
     */
    public OkHttpAPIProvider(OkHttpClient client, Token token) {
        this.client = client;
        this.token = token;
    }

    @Override
    public JSONObject request(APIRequestBody body, String url) throws IOException, AuthenticationException {
        RequestBody requestBody;
        if (body.getContent() == null) {
            requestBody = null;
        } else {
            requestBody = RequestBody.create(body.getContent(), MediaType.get(body.getMimeType()));
        }

        Request request = new Request.Builder()
            .url(url)
            .method(body.getMethod(), requestBody)
            .addHeader("Authorization", "Bearer " + token.getToken())
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 404) {
                return null;
            }

            if (!response.isSuccessful()) {
                throw new IOException(response.message());
            }

            if (response.body() == null || response.body().contentLength() == 0) {
                return new JSONObject();
            }

            return new JSONObject(response.body().string());
        }
    }
}
