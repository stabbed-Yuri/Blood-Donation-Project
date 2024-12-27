//package com.BDMS.demo.Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class LocationService {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Value("${countrystatecity.api.key}")
//    private String apiKey;
//
//    public List<Map<String,Object>> getCountries() {
//        String url = "https://api.countrystatecity.in/v1/countries?key=" + apiKey;
//        return restTemplate.getForObject(url, List.class);
//    }
//
//    public List<Map<String, Object>> getCities(String countryCode) {
//        String url = "https://api.countrystatecity.in/v1/countries/" + countryCode + "/cities?key=" + apiKey;
//        return restTemplate.getForObject(url, List.class);
//    }
//
//    public List<Map<String, Object>> getAreas(String countryCode, String cityCode) {
//        String url = "https://api.countrystatecity.in/v1/countries/" + countryCode + "/cities/" + cityCode + "/areas?key=" + apiKey;
//        return restTemplate.getForObject(url, List.class);
//    }
//
//
//}
