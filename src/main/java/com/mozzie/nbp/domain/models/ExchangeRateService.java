package com.mozzie.nbp.domain.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mozzie.nbp.NbpChildDto;
import com.mozzie.nbp.NbpParentDto;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class ExchangeRateService {

    @Value("${nbp.api.url}")
    private String nbpApiUrl;

    @Transactional
    @SneakyThrows
    public String getUSDRateFromNbp() {
        URL url = new URL(nbpApiUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");

        List<NbpParentDto> jsonList;

        try (BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            JSONArray jsonArray = new JSONArray(response.toString());
            String jason = String.valueOf(jsonArray);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            jsonList = mapper.readValue(jason, new TypeReference<>() {
            });
        }

        Optional<String> usdRate = jsonList.stream()
            .flatMap(parentDto -> parentDto.getRates().stream())
            .filter(childDto -> "USD".equals(childDto.getCode()))
            .map(NbpChildDto::getMid)
            .findFirst();

        return usdRate.orElseThrow(() -> new RuntimeException("Nie znaleziono kursu dla waluty USD."));
    }
}