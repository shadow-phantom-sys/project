package com.aman.vehicleManagement;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.aman.vehicleManagement.controller.VehicleController;
import com.aman.vehicleManagement.entity.Vehicle;
import com.aman.vehicleManagement.entity.VehicleType;
import com.aman.vehicleManagement.entity.dto.RegisterVehicleDto;
import com.aman.vehicleManagement.entity.dto.UpdateVehicleRegistrationDTO;
import com.aman.vehicleManagement.entity.dto.VehicleDto;
import com.aman.vehicleManagement.entity.dto.VehicleTypeDto;
import com.aman.vehicleManagement.service.VehicleService;
import com.aman.vehicleManagement.service.VehicleTypesService;
import com.aman.vehicleManagement.service.exceptions.NotFoundExcep;
import com.aman.vehicleManagement.service.exceptions.UserNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.Assert;

//class VehicleManagementApplicationTests {

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class VehicleManagementApplicationTests {

	@InjectMocks
	private VehicleController vehicleController;

	@Mock
	private VehicleTypesService vehicleTypesService;

	@Test
	public void testGetAllVehicleTypes() {
		// Mock data
		VehicleType vehicleType1 = new VehicleType();
		vehicleType1.setId(1);
		vehicleType1.setType("Car");
		vehicleType1.setMaxPassengersAllowed(4);
		vehicleType1.setFarePerKM(10);

		VehicleType vehicleType2 = new VehicleType();
		vehicleType2.setId(2);
		vehicleType2.setType("Bus");
		vehicleType2.setMaxPassengersAllowed(30);
		vehicleType2.setFarePerKM(5);

		List<VehicleType> vehicleTypes = Arrays.asList(vehicleType1, vehicleType2);
		List<VehicleTypeDto> expectedVehicleTypeDtos = Arrays.asList(new VehicleTypeDto(1, "Car", 4, 10),
				new VehicleTypeDto(2, "Bus", 30, 5));

		// Mock the behavior of the vehicleTypesService
		Mockito.when(vehicleTypesService.getAllVehicleTypes()).thenReturn(expectedVehicleTypeDtos);

		// Perform the GET request
		List<VehicleTypeDto> actualVehicleTypeDtos = vehicleController.getAllVehicleTypes();

		// Verify the result
		Assert.assertEquals(expectedVehicleTypeDtos.size(), actualVehicleTypeDtos.size());
		for (int i = 0; i < expectedVehicleTypeDtos.size(); i++) {
			VehicleTypeDto expectedDto = expectedVehicleTypeDtos.get(i);
			VehicleTypeDto actualDto = actualVehicleTypeDtos.get(i);
			Assert.assertEquals(expectedDto.getId(), actualDto.getId());
			Assert.assertEquals(expectedDto.getType(), actualDto.getType());
			Assert.assertEquals(expectedDto.getMaxPassengersAllowed(), actualDto.getMaxPassengersAllowed());
			Assert.assertEquals(expectedDto.getFarePerKM(), actualDto.getFarePerKM());
		}

		// Verify that the getAllVehicleTypes method of vehicleTypesService is called
		Mockito.verify(vehicleTypesService, Mockito.times(1)).getAllVehicleTypes();
	}

//////////////////////////////////////////////////////////////////////

	@Mock
	private VehicleService vehicleService;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build();
	}

	@Test
	public void testAddVehicle() throws Exception {
		RegisterVehicleDto registerVehicleDto = new RegisterVehicleDto();
		registerVehicleDto.setRegistrationNo("ABC123");
		// Set other properties of the registerVehicleDto as needed

		Vehicle vehicle = new Vehicle();
		// Set vehicle properties as needed

		Mockito.when(vehicleService.addVehicle(Mockito.any(RegisterVehicleDto.class))).thenReturn(vehicle);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/vehicles/addvehicle").contentType("application/json")
				.content(asJsonString(registerVehicleDto))).andExpect(MockMvcResultMatchers.status().isOk());
		Mockito.verify(vehicleService, Mockito.times(1)).addVehicle(Mockito.any(RegisterVehicleDto.class));
	}

	private static String asJsonString(Object obj) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

/////////////////////////////////////////////////////////////////////////////////////	    

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build();
	}

	@Test
	public void testGetAllVehicleDetailsByUserId() throws Exception {
		// Mock data
		int userId = 1;
		VehicleDto vehicleDto = new VehicleDto();
		vehicleDto.setRegistrationNo("ABC123");
		vehicleDto.setBelongsToUserId(userId);
		// Mock the service method
		Mockito.when(vehicleService.getVehicleDetailsByUserId(userId)).thenReturn(vehicleDto);

		// Perform the GET request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles/{id}", userId))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.registrationNo").value("ABC123"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.belongsToUserId").value(userId));

		// Verify the service method is called
		Mockito.verify(vehicleService).getVehicleDetailsByUserId(userId);
	}

	@Test
	public void testGetAllVehicleDetailsByUserId_UserNotFoundException() throws Exception {
		// Mock data
		int userId = 1;
		// Mock the service method to throw UserNotFoundException
		Mockito.when(vehicleService.getVehicleDetailsByUserId(userId))
				.thenThrow(new UserNotFoundException("User not found"));

		// Perform the GET request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles/{id}", userId))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string("User not found"));

		// Verify the service method is called
		Mockito.verify(vehicleService).getVehicleDetailsByUserId(userId);
	}

	@Test
	public void testGetAllVehicleDetailsByUserId_InternalServerError() throws Exception {
		// Mock data
		int userId = 1;
		// Mock the service method to throw an exception
		Mockito.when(vehicleService.getVehicleDetailsByUserId(userId))
				.thenThrow(new RuntimeException("Internal server error"));

		// Perform the GET request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles/{id}", userId))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError())
				.andExpect(MockMvcResultMatchers.content().string("An error occurred"));

		// Verify the service method is called
		Mockito.verify(vehicleService).getVehicleDetailsByUserId(userId);
	}

////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testDeleteVehicleDetailsByRegistrationNo_Success() {
		String registrationNo = "ABC123";

		// Mock the behavior of the vehicleService
		doNothing().when(vehicleService).deleteVehicleDetailByResigtrationNo(registrationNo);

		// Perform the API call
		ResponseEntity<?> response = vehicleController.deleteVehicleDetailsByRegistrationNo(registrationNo);

		// Assert the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNull(null, response.getBody());
	}

	@Test
	public void testDeleteVehicleDetailsByRegistrationNo_NotFound() {
		String registrationNo = "XYZ789";

		// Mock the behavior of the vehicleService to throw a NotFoundExcep
		doThrow(new NotFoundExcep("Vehicle details not found")).when(vehicleService)
				.deleteVehicleDetailByResigtrationNo(registrationNo);

		// Perform the API call
		ResponseEntity<?> response = vehicleController.deleteVehicleDetailsByRegistrationNo(registrationNo);

		// Assert the response
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Vehicle details not found", response.getBody());
	}

	@Test
	public void testDeleteVehicleDetailsByRegistrationNo_InternalServerError() {
		String registrationNo = "DEF456";

		// Mock the behavior of the vehicleService to throw an Exception
		doThrow(new RuntimeException("Failed to delete vehicle details.")).when(vehicleService)
				.deleteVehicleDetailByResigtrationNo(registrationNo);

		// Perform the API call
		ResponseEntity<?> response = vehicleController.deleteVehicleDetailsByRegistrationNo(registrationNo);

		// Assert the response
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Failed to delete vehicle details.", response.getBody());
	}

///////////////////////////////////////////////////////////////////////////////

	@Test
	public void testGetPendingVehicles() throws Exception {
		// Mock the response from the service
		List<VehicleDto> vehicleDtoList = new ArrayList<>();
		VehicleDto vehicleDto = new VehicleDto();
		// Set vehicleDto properties
		// ...

		vehicleDtoList.add(vehicleDto);
		when(vehicleService.getAllPendingVehicles()).thenReturn(vehicleDtoList);

		// Perform the GET request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles/pendingapprovals").contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].registrationNo").value(vehicleDto.getRegistrationNo()))
				.andExpect(
						MockMvcResultMatchers.jsonPath("$[0].belongsToUserId").value(vehicleDto.getBelongsToUserId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].inspectedByUserId")
						.value(vehicleDto.getInspectedByUserId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].inspectedOn").value(vehicleDto.getInspectedOn()))
				.andExpect(
						MockMvcResultMatchers.jsonPath("$[0].inspectionStatus").value(vehicleDto.getInspectionStatus()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].vehicleTypeId").value(vehicleDto.getVehicleTypeId()));

		// Verify the service method was called
		Mockito.verify(vehicleService).getAllPendingVehicles();
	}
////////////////////////////////////////////////////////////

	@Test
	public void testUpdateVehicleStatus() {
		UpdateVehicleRegistrationDTO updateVehicleRegistrationDTO = new UpdateVehicleRegistrationDTO();
		// Set properties of the updateVehicleRegistrationDTO object

		// Mock the behavior of the vehicleService
		when(vehicleService.updateVehicleStatus(updateVehicleRegistrationDTO)).thenReturn(true);

		// Call the updateVehicleStatus endpoint
		ResponseEntity<?> response = vehicleController.updateVehicleStatus(updateVehicleRegistrationDTO);

		// Verify the response
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(true, response.getBody());

		// Verify that the vehicleService's updateVehicleStatus method was called
		verify(vehicleService, times(1)).updateVehicleStatus(updateVehicleRegistrationDTO);
	}
}
