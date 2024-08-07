package bg.softuni.parking.service;

import bg.softuni.parking.exception.VehicleAlreadyExists;
import bg.softuni.parking.exception.VehicleNotFoundException;
import bg.softuni.parking.model.dto.vehicle.VehicleCreateDto;
import bg.softuni.parking.model.dto.vehicle.VehicleEditDto;
import bg.softuni.parking.model.dto.vehicle.VehicleView;
import bg.softuni.parking.model.dto.vehicle.VehicleViewAdmin;
import bg.softuni.parking.model.entities.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

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




              public List<VehicleView> getUserVehicles(String uuid) {
            try {
              return restClient
                  .get()
                  .uri("http://localhost:8081/vehicles/user/{uuid}", uuid)
                  .accept(MediaType.APPLICATION_JSON)
                  .retrieve()
                  .body(new ParameterizedTypeReference<List<VehicleView>>() {});
            } catch (RestClientResponseException e) {
              if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new VehicleNotFoundException("Превозните средства за потребителя не са намерени!");
              }
              throw new RuntimeException("Failed to retrieve user vehicles", e);
            } catch (RestClientException e) {
              throw new RuntimeException("Failed to retrieve user vehicles", e);
            }
          }










            public VehicleView getVehicleById(Long id) {
          try {
            return restClient
                .get()
                .uri("http://localhost:8081/vehicles/{id}", id)
                .retrieve()
                .body(VehicleView.class);
          } catch (RestClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
              throw new VehicleNotFoundException("Превозното средство не е намерено!");
            }
            throw new RuntimeException("Failed to retrieve vehicle", e);
          } catch (RestClientException e) {
            throw new RuntimeException("Failed to retrieve vehicle", e);
          }
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
                    if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new VehicleNotFoundException("Превозното средство не е намерено!");
                    } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        throw new VehicleAlreadyExists("Вече имате превозно средство с този регистрационен номер!");
                    }
                    throw new RuntimeException("Failed to update vehicle", e);
                } catch (RestClientException e) {
                    throw new RuntimeException("Failed to update vehicle", e);
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
            } catch (RestClientResponseException e) {
              if (e.getStatusCode() == HttpStatus.CONFLICT) {
                throw new VehicleAlreadyExists("Вече имате превозно средство с този регистрационен номер!");
              }
              throw new RuntimeException("Неусешно създаване на превозно средство: " + e.getMessage(), e);
            } catch (RestClientException e) {
              throw new RuntimeException("Неусешно създаване на превозно средство: " + e.getMessage(), e);
            }
          }




             public void deleteVehicle(Long id) {
                try {


                    restClient
                        .delete()
                        .uri("http://localhost:8081/vehicles/{id}", id)
                        .retrieve()
                        .toBodilessEntity();



                } catch (RestClientResponseException e) {
                    if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new VehicleNotFoundException("Превозното средство не е намерено!");
                    }
                    throw new RuntimeException("Неуспешно изтриване на превозното средство", e);
                } catch (RestClientException e) {
                    throw new RuntimeException("Неуспешно изтриване на превозното средство", e);
                }
            }

    public List<VehicleViewAdmin> findAll() {
        return restClient.get()
                .uri("http://localhost:8081/vehicles/admin/all-vehicles")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<VehicleViewAdmin>>() {});
    }

}
