package com.softserve.edu.delivery.service.impl;

import com.softserve.edu.delivery.domain.Feedback;
import com.softserve.edu.delivery.domain.Order;
import com.softserve.edu.delivery.domain.User;
import com.softserve.edu.delivery.dto.FeedbackDTO;
import com.softserve.edu.delivery.repository.FeedbackRepository;
import com.softserve.edu.delivery.repository.OrderRepository;
import com.softserve.edu.delivery.repository.UserRepository;
import com.softserve.edu.delivery.repository.impl.FeedbackRepositoryCustomImpl;
import com.softserve.edu.delivery.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service("feedbackService")
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private FeedbackRepositoryCustomImpl feedbackRepositoryCustom;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    private long totalItemsNumber;
    private Boolean approved1;

    public FeedbackServiceImpl() {
    }

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, UserRepository userRepository,
                               OrderRepository orderRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    private String processRequestText(String text) {
        if (text.equals("undefined")) {
            return "%";
        } else {
            return "%" + text + "%";
        }
    }

    private Integer processRequestRate(String rate) {
        try {
            return Integer.parseInt(rate);
        } catch (IllegalArgumentException e) {
            return 0;
        }
    }

    private Timestamp processRequestCreatedOn(String createdOn) {
        try {
            return new Timestamp(Long.parseLong(createdOn));
        } catch (IllegalArgumentException e) {
            return Timestamp.valueOf(LocalDateTime.MIN);
        }
    }

    private Boolean processRequestApproved(String approved) {
        if (approved.toLowerCase().contains("tru")) {
            approved1 = true;
            return true;
        } else if (approved.toLowerCase().contains("fal")) {
            approved1 = false;
            return false;
        }
        approved1 = false;
        return true;
    }

    private String processRequestSortOrder(String sortOrder) {
        if (sortOrder.toLowerCase().contains("tru")) {
            return "desc";
        }
        return "asc";
    }

    public FeedbackDTO copyFeedbackToDTO(Feedback feedback) {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setFeedbackId(feedback.getFeedbackId());
        feedbackDTO.setOrderId(feedback.getOrder().getId());
        feedbackDTO.setText(feedback.getText());
        feedbackDTO.setUserName(feedback.getUser().getFirstName() + " " + feedback.getUser().getLastName());
        feedbackDTO.setUserEmail(feedback.getUser().getEmail());
        feedbackDTO.setRate(feedback.getRate());
        feedbackDTO.setApproved(feedback.getApproved());
        Optional<String> oApprovedDriverName = feedbackRepository.getApprovedDriverName(feedback.getOrder().getId());
        if (oApprovedDriverName.isPresent()) {
            feedbackDTO.setTransporterName(oApprovedDriverName.get());
        } else {
            throw new NoSuchElementException("Driver name for order with id " + feedbackDTO.getOrderId() + " not found");
        }
        Optional<String> oApprovedDriverEmail = feedbackRepository.getApprovedDriverEmail(feedback.getOrder().getId());
        if (oApprovedDriverEmail.isPresent()) {
            feedbackDTO.setTransporterEmail(oApprovedDriverEmail.get());
        } else {
            throw new NoSuchElementException("Driver email for order with id " + feedbackDTO.getOrderId() + " not found");
        }
        feedbackDTO.setCreatedOn(feedback.getCreatedOn());
        return feedbackDTO;
    }

    private List<FeedbackDTO> copyFeedbackListToDTOList(List<Feedback> feedbackList) {
        List<FeedbackDTO> listFeedbackDTO = new ArrayList<>();
        feedbackList.forEach(f ->
                listFeedbackDTO.add(copyFeedbackToDTO(f))
        );
        return listFeedbackDTO;
    }

    public Feedback copyDTOToFeedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setFeedbackId(feedbackDTO.getFeedbackId());
        Optional<User> oUser = userRepository.findOneOpt(feedbackDTO.getUserEmail());
        if (oUser.isPresent()) {
            feedback.setUser(oUser.get());
        } else {
            throw new NoSuchElementException("User with id " + feedbackDTO.getUserEmail() + " not found");
        }
        Optional<Order> oOrder = orderRepository.findOneOpt(feedbackDTO.getOrderId());
        if (oOrder.isPresent()) {
            feedback.setOrder(oOrder.get());
        } else {
            throw new NoSuchElementException("Order with id " + feedbackDTO.getOrderId() + " not found");
        }
        feedback.setText(feedbackDTO.getText());
        feedback.setRate(feedbackDTO.getRate());
        feedback.setApproved(feedbackDTO.getApproved());
        feedback.setCreatedOn(feedbackDTO.getCreatedOn());
        return feedback;
    }

    @Override
    @Transactional
    public List<FeedbackDTO> getAllFeedbacksInRange(Long idFrom, int number) {

        List<FeedbackDTO> feedbackDTOs = new ArrayList<>();

        return feedbackDTOs;

    }

    @Override
    @Transactional
    public void changeFeedbackStatus(Long id, boolean status) {

        Optional<Feedback> oFeedback = feedbackRepository.findOneOpt(id);

        if (oFeedback.isPresent()) {
            Feedback feedback = oFeedback.get();
            feedback.setApproved(status);
            feedbackRepository.save(feedback);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    @Transactional
    public void save(FeedbackDTO feedbackDTO) {
        Feedback feedback = copyDTOToFeedback(feedbackDTO);
        feedbackRepository.save(feedback);
    }

    @Override
    @Transactional
    public void update(FeedbackDTO feedbackDTO) {
        Feedback feedback = copyDTOToFeedback(feedbackDTO);
        if (feedbackRepository.exists(feedback.getFeedbackId())) {
            feedbackRepository.save(feedback);
        } else {
            throw new NoSuchElementException("The feedback doesn't exist");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Optional<Feedback> oFeedback = feedbackRepository.findOneOpt(id);
        if (oFeedback.isPresent()) {
            feedbackRepository.delete(oFeedback.get());
        } else {
            throw new NoSuchElementException("The feedback doesn't exist");
        }

    }

    @Override
    @Transactional
    public FeedbackDTO findOne(Long id) {

        Feedback feedback;

        Optional<Feedback> oFeedback = feedbackRepository.findOneOpt(id);

        if (oFeedback.isPresent()) {
            feedback = oFeedback.get();
        } else {
            throw new NoSuchElementException();
        }

        return copyFeedbackToDTO(feedback);
    }

    @Override
    @Transactional
    public User getUser(String email) {
        Optional<User> oUser = userRepository.findOneOpt(email);
        if (oUser.isPresent()) {
            return oUser.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    @Transactional
    public Order getOrder(Long id) {
        Optional<Order> oOrder = orderRepository.findOneOpt(id);
        if (oOrder.isPresent()) {
            return oOrder.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    @Transactional
    public FeedbackDTO findByFeedbackId(Long id) {
        Optional<Feedback> oFeedback = feedbackRepository.findOneOpt(id);
        if (oFeedback.isPresent()) {
            return copyFeedbackToDTO(oFeedback.get());
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    @Transactional
    public List<FeedbackDTO> findAll() {
        return copyFeedbackListToDTOList(feedbackRepository.findAll());
    }

    @Override
    @Transactional
    public List<FeedbackDTO> findFiltered(String text, String rateString, String userName, String transporterName,
                                          String createdOnString, String approvedString, String sortBy,
                                          String sortOrder, int currentPage, int itemsPerPage) {

        text = processRequestText(text);
        Integer rate = processRequestRate(rateString);
        userName = processRequestText(userName);
        transporterName = processRequestText(transporterName);
        Timestamp createdOn = processRequestCreatedOn(createdOnString);
        Boolean approved = processRequestApproved(approvedString);
        sortOrder = processRequestSortOrder(sortOrder);

        int sortType = 0;

        if (sortBy.equals("userName")) {
            sortType = 1;
        } else if (sortBy.equals("transporterName")) {
            sortType = 2;
        }

        List<Feedback> feedbackList = feedbackRepositoryCustom.findFiltered(text, rate, userName,
                transporterName, createdOn, approved, approved1, currentPage, itemsPerPage, sortType, sortBy, sortOrder);

        totalItemsNumber = feedbackRepositoryCustom.getTotalItemsNumber();

        return copyFeedbackListToDTOList(feedbackList);

    }

    @Override
    public long getTotalItemsNumber() {
        return totalItemsNumber;
    }
}