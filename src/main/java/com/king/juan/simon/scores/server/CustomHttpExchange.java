package com.king.juan.simon.scores.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.net.httpserver.HttpExchange;

import com.king.juan.simon.scores.exceptions.BadRequestException;

public class CustomHttpExchange {

    private HttpExchange httpExchange;
    private Map<String, String> queryParams;

    public CustomHttpExchange(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
        this.queryParams = getQueryParams();
    }

    private Map<String, String> getQueryParams() {
        HashMap<String, String> map = new HashMap<>();

        String query = httpExchange.getRequestURI().getQuery();
        if (query == null) {
            return map;
        }
        for (String parameter : query.split("&")) {
            String[] keyValue = parameter.split("=");

            if (keyValue.length != 2)
                return null;

            map.put(keyValue[0], keyValue[1]);
        }

        return map;
    }

    public String getRequestMethod() {
        return httpExchange.getRequestMethod();
    }

    public String getRequestPath() {
        return httpExchange.getRequestURI().getPath();
    }

    public void sendResponse(int statusCode, String message) throws IOException {
        byte[] response = message.getBytes();
        httpExchange.sendResponseHeaders(statusCode, response.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response);
        os.close();
    }

    public String getParameter(String param) {
        return queryParams.get(param);
    }

    public Integer getRequestBodyAsInt() throws IOException {
        try (InputStream inputStream = httpExchange.getRequestBody()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            return Integer.parseInt(sb.toString());
        } catch (NumberFormatException e) {
            throw new BadRequestException("Bad Request: Number format error");
        }
    }

    public Integer getPathVariable(String regex, int order) {
        try {
            Matcher matcher = Pattern.compile(regex).matcher(httpExchange.getRequestURI().getPath());
            if(matcher.find()) {
                return Integer.parseInt(matcher.group(order));
            }
            throw new BadRequestException("Bad Request: missing required parameter");
        } catch (NumberFormatException e) {
            throw new BadRequestException("Bad Request: Number format error ");
        }
    }
}
