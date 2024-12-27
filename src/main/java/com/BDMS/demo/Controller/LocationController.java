//package com.BDMS.demo.Controller;
//
//import com.BDMS.demo.Service.LocationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.Map;
//
//
//@RestController
//public class LocationController {
//
//    @Autowired
//    private LocationService locationService;
//
//    @GetMapping("/api/countries")
//    public List<Map<String, Object>> getCountries() {
//        return locationService.getCountries();
//    }
//
//    @GetMapping("/api/cities")
//    public List<Map<String, Object>> getCities(@RequestParam String countryCode) {
//        return locationService.getCities(countryCode);
//    }
//
//    @GetMapping("/api/areas")
//    public List<Map<String, Object>> getAreas(@RequestParam String countryCode, @RequestParam String cityCode) {
//        return locationService.getAreas(countryCode, cityCode);
//    }
//}
