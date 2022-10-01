package com.masai.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exceptions.CustomerException;
import com.masai.exceptions.FeedbackException;
import com.masai.exceptions.PackageException;
import com.masai.models.Booking;
import com.masai.models.BookingDTO;
import com.masai.models.Customer;
import com.masai.models.CustomerDTO;
import com.masai.models.Feedback;
import com.masai.service.CBookingService;
import com.masai.service.CustomerService;
import com.masai.service.IFeedbackService;
import com.masai.service.IReportService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired CustomerService customerService;
	@Autowired CBookingService bookingService;
	@Autowired IFeedbackService feedbackService;
	@Autowired IReportService reportService;
	
	
	@PostMapping("/")
	public ResponseEntity<Customer> saveCustomer(@RequestBody CustomerDTO customer){
		return new ResponseEntity<Customer>(customerService.createCustomer(customer),HttpStatus.OK);
	}
	
	@PostMapping("/booking/{id}")
	public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingDTO,@PathVariable("id") Integer customerId) throws PackageException, CustomerException{
		Booking booking = bookingService.createBooking(bookingDTO, customerId);
		return new ResponseEntity<Booking>(booking,HttpStatus.CREATED);
	}
	
	@PostMapping("/feedback")
	public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) throws FeedbackException{
		Feedback savedFeedback = feedbackService.addFeedback(feedback);
		return new ResponseEntity<Feedback>(savedFeedback, HttpStatus.CREATED);	
	}
	
	@GetMapping("/feedback/{id}")
	public ResponseEntity<List<Feedback>> getFeedbacks(@PathVariable Integer id) throws FeedbackException, CustomerException{
		List<Feedback> feedbackList = feedbackService.findByCustomerId(id);
		return new ResponseEntity<List<Feedback>>(feedbackList, HttpStatus.OK);
	}
}
