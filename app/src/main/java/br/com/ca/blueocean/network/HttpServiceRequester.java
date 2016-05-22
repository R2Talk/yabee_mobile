package br.com.ca.blueocean.network;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HttpServiceRequester
 *
 * Implements a method that do an http request and returns a String response.
 *
 * This class is used for rest requests
 *
 * @author Rodrigo Carvalho
 */
public class HttpServiceRequester {

    Context context;

    /**
     * HttpServiceRequester
     *
     * Constructor receives app context object
     *
     * @param context
     */
    public HttpServiceRequester(Context context){
        this.context = context;
    }

    /**
     * executeHttpGetRequest
     *
     * Receives an URL and executes an http request returning a String
     *
     * @param urlString
     * @return
     */
    public String executeHttpGetRequest(String urlString) throws DeviceNotConnectedException {

        /*

        String returnString;

        List<MessageVo> messageVoList;
        MessageVo messageVo;

        Gson gson = new Gson();

        messageVoList = new ArrayList<>();

        messageVo = new MessageVo();
        messageVo.setText("Olá, não esquecer de enviar a apresentaçao das açoes incrmentais da Receita.");
        messageVo.setDatetime(new Date());
        messageVoList.add(messageVo);
        messageVo = new MessageVo();
        messageVo.setText("Rodrigo, já temos a estimativa de prazo para o desenvolvimento da automação ZB1? Precisamos avaliar alguma alterenativa paliativa caso passe de Junho.");
        messageVoList.add(messageVo);
        messageVo = new MessageVo();
        messageVo.setText("Recebida a avaliação das alternativas de abastecimento de aparelhos. Por favor atualizar pontos críticos.");
        messageVoList.add(messageVo);
        messageVo = new MessageVo();
        messageVo.setText("Atualizar a tarefa 4663.");
        messageVoList.add(messageVo);
        messageVo = new MessageVo();
        messageVo.setText("Olá, Precisamos revisar a forma de apresentar as mensagens. Por favor avalie se a nova interface Cards não é a mais adequada. As listas já criadas terão que passar por refactoring.");
        messageVoList.add(messageVo);

        //serialize generic type for List of MessageVo
        Type messagesListType = new TypeToken<List<MessageVo>>() {}.getType(); //this is necessary because we are deserializing a generic class type

        returnString = gson.toJson(messageVoList, messagesListType);

        return returnString;

        */


        HttpURLConnection conn = null;
        InputStream inputStream = null;
        BufferedReader reader = null;
        StringBuffer stringBuffer = null;
        String returnString = null;

        //class that can check if the device has a valid internet connection
        CheckInternetConnection checkInternetConnection = new CheckInternetConnection(context);

        // if the device is not connected to Internet trow an custom exception
        if (!checkInternetConnection.deviceIsConnected()) {
            throw new DeviceNotConnectedException();
        }

        Log.d("HttpServiceRequester", "ok, device has an network connection");

        try {

            //String urlString = "http://192.168.0.8:8080/AsapServer/sendMessage?msg=" + URLEncoder.encode(msg, "UTF-8");

            //URL encoded text
            URL url = new URL(urlString);

            //open connection
            conn = (HttpURLConnection) url.openConnection();

            //prepare request parameters
            conn.setReadTimeout(4000);// milliseconds
            conn.setConnectTimeout(4000);// milliseconds
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("GET");

            //starts the http request
            Log.d("HttpServiceRequester", "trying to connect using created HttpURLConnection");
            int responseCode = conn.getResponseCode();
            Log.d("HttpServiceRequester", "The response code is: " + responseCode);

            //read input stream
            Log.d("HttpServiceRequester", "trying to get input stream using conn.getInputStream");
            inputStream = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            stringBuffer = new StringBuffer();
            // Convert the InputStream into a String
            String line = "";
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
            returnString = stringBuffer.toString();
            Log.d("HttpServiceRequester", "read from http connection: " + returnString);

        } catch (Exception e) {
            Log.d("HttpServiceRequester", e.getMessage());
        } finally {
            // makes sure that the InputStream is closed after the app is
            // finished using it.
            try {
                if (conn != null) {
                    conn.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (java.io.IOException e) {
                Log.d("HttpServiceRequester", e.getMessage());
            }
        }

        return returnString;

    }
}
