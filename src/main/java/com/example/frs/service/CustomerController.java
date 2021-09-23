package com.example.frs.service;

import com.example.frs.bean.*;
import com.example.frs.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.Date;
import java.text.SimpleDateFormat;
@Controller
public class CustomerController {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private FlightDao flightDao;
    @Autowired
    private ScheduleDao scheduleDao;
    @Autowired
    private CreditCardDao creditCardDao;
    @Autowired
    private RouteDao routeDao;
    @Autowired
    CustomerProfileDao customerProfileDao;

    @GetMapping("/searchFlight")
    public String get_search_flight(Model model, HttpServletRequest request){
        String user_id = (String)request.getSession().getAttribute("SESSION_UID");
        ProfileBean profile = customerProfileDao.getByUserId(user_id);
        model.addAttribute("profile_firstName", profile.getFirst_name());
        model.addAttribute("profile_lastName", profile.getLast_name());
        model.addAttribute("route", new RouteBean());
        List<RouteBean> routes = routeDao.list();
        model.addAttribute("routes", routes);
        return "searchFlight";
    }

    @PostMapping("/searchFlight")
    public String post_search_flight(Model model, @RequestParam("source")String source, @RequestParam("destination")String destination, @RequestParam("date")String date, HttpServletRequest request) throws ParseException {
        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
        Date today = new Date();
        if (date1.compareTo(today)<=0){
            model.addAttribute("msg_date", "Date is already over. Please enter a later date.");
            return "searchFlight";
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String bookingDate = dtf.format(now);

        List<RSFBean> rWSF =  customerDao.searchFlight(source, destination, date);
        model.addAttribute("rWSF", rWSF);
        model.addAttribute("journeyDate", date);
        model.addAttribute("bookingDate", bookingDate);
        return "availableFlights";
    }


    @GetMapping("/bookTicket")
    public String get_tickets(Model model, HttpServletRequest request,
                                   @RequestParam("journeyDate")String journeyDate,
                                   @RequestParam("bookingDate")String bookingDate,
                                   @RequestParam("fare")double fare,
                                   @RequestParam("schedule_id")String schedule_id)
    {
        String user_id = (String)request.getSession().getAttribute("SESSION_UID");
        ProfileBean profile = customerProfileDao.getByUserId(user_id);
        model.addAttribute("profile_firstName", profile.getFirst_name());
        model.addAttribute("profile_lastName", profile.getLast_name());
        model.addAttribute("reservation", new ReservationBean());
        model.addAttribute("schedule_id", schedule_id);
        model.addAttribute("journeyDate", journeyDate);
        model.addAttribute("bookingDate", bookingDate);
        model.addAttribute("fare", fare);
        return "bookTicket";
    }

    @PostMapping("/bookTicket")
    public String post_tickets(Model model, @RequestParam("scheduleID")String scheduleID,
                      @RequestParam("reservationType")String reservationType,
                      @RequestParam("journeyDate")String journeyDate,
                      @RequestParam("noSeats")int noSeats,
                      @RequestParam("bookingDate")String bookingDate,
                      @RequestParam("fare")double fare,
                      HttpServletRequest request
    ) throws ParseException {

        String user_ID = (String) request.getSession().getAttribute("SESSION_UID");
        ProfileBean profile = customerProfileDao.getByUserId(user_ID);
        model.addAttribute("profile_firstName", profile.getFirst_name());
        model.addAttribute("profile_lastName", profile.getLast_name());
        String id = UUID.randomUUID().toString();
        ScheduleBean schedule= scheduleDao.get(scheduleID);
        FlightBean flight = flightDao.get(schedule.getFlight_id());
        if (flight.getSeating_capacity() < flight.getReservation_capacity() + noSeats){
            model.addAttribute("msg_seats", "More seats booked than total capacity");
        }
        else{
            flight.setReservation_capacity(flight.getReservation_capacity() + noSeats);
            flightDao.update(flight);
        }

        double fares = fare * noSeats;
        String schedule_id = scheduleID;
        String reservation_type = reservationType;
        ReservationBean reservation  = new ReservationBean(id, user_ID, schedule_id, reservationType, bookingDate, journeyDate, noSeats, fares, 1);
        customerDao.reserveTicket(reservation);

        AddPassengerBean pass = new AddPassengerBean();
        for(int i = 0; i < noSeats; ++i)
            pass.addPassenger(new PassengerBean("","", "", "", 1, i));
        model.addAttribute("form", pass);
        model.addAttribute("uid", id);

        return "addPassengerDetails";
    }
    @PostMapping("/addPassengerDetails")
    public String post_add_passenger(Model model, @ModelAttribute("form") AddPassengerBean pass, HttpServletRequest request, @RequestParam("id") String id){
        String user_id = (String)request.getSession().getAttribute("SESSION_UID");
        ProfileBean profile = customerProfileDao.getByUserId(user_id);
        model.addAttribute("profile_firstName", profile.getFirst_name());
        model.addAttribute("profile_lastName", profile.getLast_name());
        List<PassengerBean> passengers = pass.getPassengers();
        String s = id;
        String str[] = s.split(",");
        String reservation_ID = str[0];
        for (PassengerBean p : passengers) {
            String rand_id = UUID.randomUUID().toString();
            p.setReservation_id(reservation_ID);
            p.setUnique_id(rand_id);
        }
        customerDao.addPassenger(passengers);
        String userID = (String)request.getSession().getAttribute("SESSION_UID");
        CreditCardBean card = creditCardDao.getCardNo(userID);
        model.addAttribute("card_no", card.getCard_no());
        model.addAttribute("cvv", card.getCvv());
        model.addAttribute("valid_to", card.getValid_to());
        model.addAttribute("credit_balance", card.getCredit_balance());
        model.addAttribute("user_id",card.getUser_id());
        return "paymentPage";
    }

    @GetMapping("/viewBookedTicket")
    public String get_view_ticket(Model model, HttpServletRequest request){
        String user_id = (String)request.getSession().getAttribute("SESSION_UID");
        ProfileBean profile = customerProfileDao.getByUserId(user_id);
        model.addAttribute("profile_firstName", profile.getFirst_name());
        model.addAttribute("profile_lastName", profile.getLast_name());
        List<ScheduleReservationBean> SWR =  customerDao.viewBookedTicket(profile.getUser_id());
        model.addAttribute("SWR", SWR);
        return "viewBookedTicket";
    }

    @GetMapping("/paymentPage")
    public String get_view_payment(Model model, HttpServletRequest request){
        String user_id = (String)request.getSession().getAttribute("SESSION_UID");
        ProfileBean profile = customerProfileDao.getByUserId(user_id);
        model.addAttribute("profile_firstName", profile.getFirst_name());
        model.addAttribute("profile_lastName", profile.getLast_name());
        return "paymentPage";
    }

    @PostMapping("/paymentPage")
    public String post_view_payment(Model model,HttpServletRequest request){
        String user_id = (String)request.getSession().getAttribute("SESSION_UID");
        ProfileBean profile = customerProfileDao.getByUserId(user_id);
        List<ScheduleReservationBean> SWR =  customerDao.viewBookedTicket(profile.getUser_id());
        model.addAttribute("SWR", SWR);
        return "viewBookedTicket";
    }

    @GetMapping("/deleteBooking")
    public String get_delete_ticket(Model model,HttpServletRequest request,@RequestParam("reservationID")String reservationID
            ,@RequestParam("journey_date") String journey_date,@RequestParam("flightID")String flightID,
                                @RequestParam("noSeats")int seats) throws ParseException {
        String user_id = (String)request.getSession().getAttribute("SESSION_UID");
        ProfileBean profile = customerProfileDao.getByUserId(user_id);
        model.addAttribute("profile_firstName", profile.getFirst_name());
        model.addAttribute("profile_lastName", profile.getLast_name());
        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(journey_date);
        Date today = new Date();

        if (date1.compareTo(today)<=0){
            model.addAttribute("msg_date2", "Cannot delete booking! Date doesn't exist anymore.");
            List<ScheduleReservationBean> SWR =  customerDao.viewBookedTicket(profile.getUser_id());
            model.addAttribute("SWR", SWR);
            return "viewBookedTicket";
        }
        FlightBean fl = flightDao.get(flightID);
        fl.setReservation_capacity(fl.getReservation_capacity() - seats);
        flightDao.update(fl);
        customerDao.deleteBookedTicket(reservationID);
        List<ScheduleReservationBean> SWR =  customerDao.viewBookedTicket(profile.getUser_id());
        model.addAttribute("SWR", SWR);
        return "viewBookedTicket";
    }
    @GetMapping("/printTicket")
    public String get_print_ticket(Model model,HttpServletRequest request,@RequestParam("reservationID")String reservationID ){
        String user_id = (String)request.getSession().getAttribute("SESSION_UID");
        ProfileBean profile = customerProfileDao.getByUserId(user_id);
        model.addAttribute("profile_firstName", profile.getFirst_name());
        model.addAttribute("profile_lastName", profile.getLast_name());
        List<ScheduleReservationBean> swr= customerDao.printBookedTicket(reservationID);
        model.addAttribute("swr",swr);
        return "printTicket";
    }
}
