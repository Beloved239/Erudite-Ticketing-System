package com.tobi.Erudite_Event_System.event.service.serviceImpl;

import com.google.zxing.WriterException;
import com.tobi.Erudite_Event_System.dto.*;
import com.tobi.Erudite_Event_System.email.service.EmailService;
import com.tobi.Erudite_Event_System.event.entity.Booking;
import com.tobi.Erudite_Event_System.event.entity.Events;
import com.tobi.Erudite_Event_System.event.repository.BookingRepository;
import com.tobi.Erudite_Event_System.event.repository.EventsRepository;
import com.tobi.Erudite_Event_System.event.service.EventService;
import com.tobi.Erudite_Event_System.payment.dto.InitializePaymentDto;
import com.tobi.Erudite_Event_System.payment.dto.InitializePaymentResponse;
import com.tobi.Erudite_Event_System.payment.service.PayStackService;
import com.tobi.Erudite_Event_System.users.entity.Users;
import com.tobi.Erudite_Event_System.users.repository.UserRepository;
import com.tobi.Erudite_Event_System.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingRequestValueException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventsRepository repository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final EventsRepository eventsRepository;
    private final BookingRepository bookingRepository;
    private final PayStackService payStackService;

    @Override
    public ResponseEntity<CustomResponse> createEvent(Long id, EventDto eventDto) {
        boolean existById = userRepository.existsById(id);
        if (!existById){
            return ResponseEntity.badRequest().body(CustomResponse.builder()
                    .responseCode(ResponseUtils.UN_SUCCESSFUL_CODE)
                    .responseBody(ResponseUtils.ORGANIZER_DOES_NOT_EXIST_MESSAGE)
                    .build());
        }else {
            Optional<Users> organizer = Optional.ofNullable(userRepository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("User with the provided id does not exist!")));
            Events event = Events.builder()
                    .eventName(eventDto.getEventTitle())
                    .eventDescription(eventDto.getDescription())
                    .eventLocation(eventDto.getLocation())
                    .startDate(eventDto.getStartDate())
                    .endDate(eventDto.getEndDate())
                    .startTime(eventDto.getStartTime())
                    .endTime(eventDto.getEndTime())
                    .ticketType(eventDto.getTicketType())
                    .ticketCapacity(eventDto.getTicketCapacity())
                    .ticketPrice(eventDto.getTicketPrice())
                    .ticketDescription(eventDto.getTicketDescription())
                    .users(organizer.get())
                    .uniqueId(ResponseUtils.generateUniqueAlphaNumericString(10))
                    .build();
            repository.save(event);

            EmailDetails message = EmailDetails.builder()
                    .subject("Event Created Successfully")
                    .messageBody("Event with Title: " + eventDto.getEventTitle() + " " + " Created successfully")
                    .recipient(organizer.get().getEmail())
                    .build();
            emailService.sendSimpleEmail(message);

            return ResponseEntity.ok().body(CustomResponse.builder()
                            .responseCode(ResponseUtils.SUCCESS_CODE)
                            .responseMessage(ResponseUtils.SUCCESS_MESSAGE)
                            .responseBody("Event created successfully")
                    .build());
        }


    }
    @Override
    public ResponseEntity<CustomEventResponse> getAllEventByName(int page, int size, String eventName) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("startDate").ascending());

        List<Events> eventsList = repository.findByEventName(eventName, pageRequest);

        boolean eventExist = repository.existsByEventName(eventName);
        log.info("exist: "+ eventExist);

        if (!eventExist){
            return ResponseEntity.badRequest().body(CustomEventResponse.builder()
                            .responseCode(ResponseUtils.UN_SUCCESSFUL_CODE)
                            .responseMessage(ResponseUtils.UN_SUCCESSFUL_MESSAGE)
                            .eventResponse(null)
                    .build());
        }else {
            List<EventResponse> responses = new ArrayList<>();
            for (Events element: eventsList){
                responses.add(EventResponse.builder()
                                .id(element.getId())
                                .eventName(element.getEventName())
                                .eventDescription(element.getEventDescription())
                                .eventLocation(element.getEventLocation())
                                .ticketDescription(element.getTicketDescription())
                                .startTime(element.getStartTime())
                                .endTime(element.getEndTime())
                                .ticketPrice(element.getTicketPrice())
                                .ticketType(element.getTicketType())
                                .startDate(element.getStartDate())
                                .endDate(element.getEndDate())
                        .build());
            }
            return ResponseEntity.ok().body(CustomEventResponse.builder()
                            .responseCode(ResponseUtils.SUCCESS_CODE)
                            .responseMessage(ResponseUtils.SUCCESS_MESSAGE)
                            .eventResponse(responses)
                    .build());
        }


    }

    @Override
    public ResponseEntity<DiscoverEventResponse> getAllUpcomingEvents(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("startDate").ascending());

        LocalTime now = LocalTime.now();
        boolean existsBeforeToday = repository.existsByStartTimeBefore(now);
        if (!existsBeforeToday) {
            return ResponseEntity.badRequest().body(DiscoverEventResponse
                    .builder()
                    .message(ResponseUtils.UN_SUCCESSFUL_MESSAGE)
                    .eventResponses(null)
                    .build());
        } else {
            LocalDate today = LocalDate.now();
            List<Events> eventsList = repository.findAllByStartDateAfter(today,pageRequest);

            List<EventResponse> responses = new ArrayList<>();
            for (Events element : eventsList) {
                responses.add(EventResponse.builder()
                        .id(element.getId())
                        .eventName(element.getEventName())
                        .eventDescription(element.getEventDescription())
                        .eventLocation(element.getEventLocation())
                        .ticketDescription(element.getTicketDescription())
                        .startTime(element.getStartTime())
                        .endTime(element.getEndTime())
                        .ticketPrice(element.getTicketPrice())
                        .ticketType(element.getTicketType())
                        .startDate(element.getStartDate())
                        .endDate(element.getEndDate())
                        .build());
            }
            return ResponseEntity.ok().body(DiscoverEventResponse.builder()
                    .message(ResponseUtils.SUCCESS_MESSAGE)
                    .eventResponses(responses)
                    .build());
        }

    }

    @Override
    public ResponseEntity<BookEventResponse> bookEvent(Long organizerId, Long eventId,
                                                       BookEventRequest request) throws IOException, MissingRequestValueException, WriterException {

        Optional<Events> events = eventsRepository.findById(eventId);
        if (events.isEmpty()) {
            return ResponseEntity.badRequest().body(BookEventResponse.builder()
                    .responseCode(HttpStatus.BAD_REQUEST.toString())
                    .responseMessage("Event with the provided Id does not exist")
                    .build());
        }
        Optional<Users> organizer = userRepository.findById(organizerId);
        if (organizer.isEmpty()){
            return ResponseEntity.badRequest().body(BookEventResponse.builder()
                    .responseCode(HttpStatus.BAD_REQUEST.toString())
                    .responseMessage("Organizer with the provided Id does not exist")
                    .build());
        } else {
            Booking eventBooking = Booking.builder()
                    .organizer(organizer.get())
                    .nameOfAttendee(request.getNameOfAttendee())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .bookingDate(LocalDate.now())
                    .eventName(request.getEventName())
                    .ticketType(request.getTicketType())
                    .event(events.get())
                    .quantity(request.getQuantity())
                    .price(request.getPrice())
                    .subTotal(request.getPrice()* request.getQuantity())
                    .build();
            bookingRepository.save(eventBooking);
            EventData data  = EventData.builder()
                    .ticketType(eventBooking.getTicketType())
                    .price(eventBooking.getPrice())
                    .subTotal(eventBooking.getSubTotal())
                    .build();

            ResponseUtils.createQrCode(data);
            log.info("QRCode Created");
            EmailDetails message = EmailDetails.builder()
                    .recipient(request.getEmail())
                    .subject("Event Booked Successfully")
                    .messageBody("Attached herewith is your event details")
                    .attachment(ResponseUtils.QR_CODE_IMAGE_PATH)
                    .build();

            emailService.sendEmailWithAttachment(message);
            log.info("Mail Sent Successfully");
            BigDecimal amount = new BigDecimal(String.valueOf(eventBooking.getSubTotal()));

            BigDecimal result = amount.multiply(BigDecimal.TEN.pow(2));
            log.info("amount: " +result);


            InitializePaymentDto paymentRequest = InitializePaymentDto.builder()
                    .amount(result)
                    .email(request.getEmail())
                    .build();

            InitializePaymentResponse response = payStackService.initializePayment(paymentRequest);
            log.info("Paystack Payment Processed");

            return ResponseEntity.ok().body(BookEventResponse.builder()
                    .responseCode(HttpStatus.OK.toString())
                    .responseMessage("Event Booked Successfully")
                    .data(EventData.builder()
                            .ticketType(eventBooking.getTicketType())
                            .price(eventBooking.getPrice())
                            .quantity(eventBooking.getQuantity())
                            .subTotal(eventBooking.getSubTotal())
                            .build())
                    .response(response)
                    .build());
        }


    }
}
