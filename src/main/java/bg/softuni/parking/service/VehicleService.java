package bg.softuni.parking.service;

import bg.softuni.parking.model.dto.vehicle.VehicleCreateDto;
import bg.softuni.parking.model.dto.vehicle.VehicleEditDto;
import bg.softuni.parking.model.dto.vehicle.VehicleView;
import bg.softuni.parking.model.dto.vehicle.VehicleViewAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

@Service
public class VehicleService {

    private final RestClient restClient;

    public VehicleService(RestClient restClient) {
        this.restClient = restClient;
    }

// todo
//  public List<Vehicle> findAll() {
//    return vehicleRepository.findAll();
//  }

    //todo
    //  public boolean hasVehicles(String username) {
//    User user = userRepository.findByUsername(username)
//        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    return !user.getVehicles().isEmpty();
//  }

    public List<VehicleView> getUserVehicles(String uuid) {
        return restClient
                .get()
                .uri("http://localhost:8081/vehicles/user/{uuid}", uuid)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }


    public VehicleView getVehicleById(Long id) {
        return restClient
                .get()
                .uri("http://localhost:8081/vehicles/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(VehicleView.class);
    }
    private static final Logger logger = LoggerFactory.getLogger(VehicleService.class);
    public VehicleView updateVehicle(Long id, VehicleEditDto vehicleEditDto) {
        try {
            logger.info("Sending update request for vehicle id: {}, with data: {}", id, vehicleEditDto);
            return restClient.put()
                    .uri("http://localhost:8081/vehicles/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(vehicleEditDto)
                    .retrieve()
                    .body(VehicleView.class);
        } catch (RestClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new IllegalArgumentException("Неуспешна редакция на превозно средство: " + e.getResponseBodyAsString(), e);
            }
            throw new IllegalArgumentException("Неуспешна редакция на превозно средство: " + e.getMessage(), e);
        }
    }

    public void addVehicle(VehicleCreateDto vehicleCreateDto, String uuid) {
        try {
            restClient
                    .post()
                    .uri("http://localhost:8081/vehicles/user/{uuid}", uuid)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(vehicleCreateDto)
                    .retrieve()
                    .body(VehicleView.class);
        } catch (RestClientException ex) {
            throw new IllegalArgumentException("Неусешно създаване на превозно средство: " + ex.getMessage(), ex);
        }
    }
    public void deleteVehicle(Long id) {
        restClient
                .delete()
                .uri("http://localhost:8081/vehicles/{id}", id)
                .retrieve();
    }

    public List<VehicleViewAdmin> findAll() {
        return restClient.get()
                .uri("http://localhost:8081/vehicles/admin/all-vehicles")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<VehicleViewAdmin>>() {});
    }

}
