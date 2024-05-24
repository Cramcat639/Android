package com.cramcat.platform.CRUDapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.*;

public class Api {

    static String ip= "http://192.168.2.53:8000";

    public void loginAsync(String mail, String password, ApiCallback callback) throws JSONException {
        try {
            String loginUrl = ip + "/user/login";

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("mail", mail);
            jsonBody.put("password", password);

            String jsonString = jsonBody.toString();
            Log.e("json", jsonString);

            RequestBody body = RequestBody.create(jsonString, mediaType);

            Request request = new Request.Builder()
                    .url(loginUrl)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null) {
                        try {
                            callback.onApiResult(null);
                        } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    // Agrega un mensaje de log para identificar el error
                    Log.e("Api", "Error en la llamada a la API: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful() || responseBody == null) {
                            // Log para identificar si la respuesta no es exitosa o es nula
                            Log.e("Api", "Error en la respuesta del servidor o respuesta nula");
                            if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                                Log.e("Api", "Error de autenticación: " + response.code());

                                if (callback != null) {
                                    callback.onApiResult(null);
                                }
                                return;
                            }
                            if (callback != null) {
                                try {
                                    callback.onApiResult(null);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            return;
                        }

                        String result = responseBody.string();

                        if (callback != null) {
                            try {
                                callback.onApiResult(result);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

            });
        } catch (Exception e) {
            if (callback != null) {
                callback.onApiResult(null);
            }
        }
    }

    public interface ApiCallback {

        void onApiResult(String result) throws JSONException;

    }



    public static class CredentialsById extends AsyncTask<String, Void, List<String>> {

        private OnTaskCompleted listener;

        public CredentialsById(OnTaskCompleted listener) {
            this.listener = listener;
        }

        @Override
        protected List<String> doInBackground(String... params) {
            String id = params[0];

            try {
                List<String> userInfo = new ArrayList<>();
                URL url = new URL(ip + "/user/" + id);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    JSONObject user = new JSONObject(response.toString());
                    String nick = user.getString("username");
                    String pass = user.getString("password");
                    String email = user.getString("mail");

                    userInfo.add(nick);
                    userInfo.add(email);
                    userInfo.add(pass);

                    return userInfo;

                } else {
                    System.out.println("Error al realizar la solicitud. Código de respuesta: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            if (listener != null) {
                listener.onTaskCompleted(result);
            }
        }

        public interface OnTaskCompleted {
            void onTaskCompleted(List<String> result);
        }
    }
    public static void eliminarUsuarioAsync(int userId, ApiCallback callback) throws JSONException {
        try {
            String deleteUrl = ip + "/delete/" + userId;

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(deleteUrl)
                    .delete()
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null) {
                        try {
                            callback.onApiResult(null);
                        } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    Log.e("Api", "Error en la llamada a la API: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful() || responseBody == null) {
                            Log.e("Api", "Error en la respuesta del servidor o respuesta nula");
                            if (callback != null) {
                                callback.onApiResult(null);
                            }
                            return;
                        }

                        String result = responseBody.string();

                        if (callback != null) {
                            callback.onApiResult(result);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (Exception e) {
            if (callback != null) {
                callback.onApiResult(null);
            }
        }
    }
    public void registerUserAsync(Integer id, String username, String email, String password, Integer isadmin, Integer isblock, ApiCallback callback) throws JSONException {
        try {
            String registerUrl = ip + "/user/new";

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("id", id);
            jsonBody.put("username", username);
            jsonBody.put("mail", email);
            jsonBody.put("password", password);
            jsonBody.put("isadmin", isadmin);
            jsonBody.put("isblock", isblock);

            String jsonString = jsonBody.toString();
            Log.e("json", jsonString);

            RequestBody body = RequestBody.create(jsonString, mediaType);

            Request request = new Request.Builder()
                    .url(registerUrl)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null) {
                        try {
                            callback.onApiResult(null);
                        } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    Log.e("Api", "Error en la llamada a la API: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful() || responseBody == null) {
                            Log.e("Api", "Error en la respuesta del servidor o respuesta nula");
                            if (callback != null) {
                                callback.onApiResult(null);
                            }
                            return;
                        }

                        String result = responseBody.string();

                        if (callback != null) {
                            try {
                                callback.onApiResult(result);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (Exception e) {
            if (callback != null) {
                callback.onApiResult(null);
            }
        }
    }
    public static void turnOnRelayAsync(ApiCallback callback) throws JSONException {
        try {
            String relayOnUrl = ip + "/rele/encender";

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(relayOnUrl)
                    .post(RequestBody.create(null, new byte[0]))
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null) {
                        try {
                            callback.onApiResult(null);
                        } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    Log.e("Api", "Error en la llamada a la API: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful() || responseBody == null) {
                            Log.e("Api", "Error en la respuesta del servidor o respuesta nula");
                            if (callback != null) {
                                callback.onApiResult(null);
                            }
                            return;
                        }

                        String result = responseBody.string();

                        if (callback != null) {
                            callback.onApiResult(result);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (Exception e) {
            if (callback != null) {
                callback.onApiResult(null);
            }
        }
    }

    public static void turnOffRelayAsync(ApiCallback callback) throws JSONException {
        try {
            String relayOffUrl = ip + "/rele/apagar";

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(relayOffUrl)
                    .post(RequestBody.create(null, new byte[0]))
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null) {
                        try {
                            callback.onApiResult(null);
                        } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    Log.e("Api", "Error en la llamada a la API: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful() || responseBody == null) {
                            Log.e("Api", "Error en la respuesta del servidor o respuesta nula");
                            if (callback != null) {
                                callback.onApiResult(null);
                            }
                            return;
                        }

                        String result = responseBody.string();

                        if (callback != null) {
                            callback.onApiResult(result);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (Exception e) {
            if (callback != null) {
                callback.onApiResult(null);
            }
        }
    }
}
