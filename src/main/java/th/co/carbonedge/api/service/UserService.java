package th.co.carbonedge.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.carbonedge.api.constant.CustomerStatus;
import th.co.carbonedge.api.constant.UserStatus;
import th.co.carbonedge.api.domain.Customer;
import th.co.carbonedge.api.domain.User;
import th.co.carbonedge.api.exception.DataConflictException;
import th.co.carbonedge.api.repository.UserRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUserForCustomer(Customer customer, String rawPassword) {
        if (userRepository.existsByCustomerId(customer.getId())) {
            throw new DataConflictException("User already exists for customer: " + customer.getId());
        }
        if (userRepository.existsByEmail(customer.getEmail())) {
            throw new DataConflictException("User email already exists: " + customer.getEmail());
        }

        User user = User.builder()
            .customerId(customer.getId())
            .email(customer.getEmail())
            .passwordHash(passwordEncoder.encode(rawPassword))
            .status(toUserStatus(customer.getStatus()))
            .build();

        return userRepository.save(user);
    }

    public void syncUserForCustomer(Customer customer) {
        userRepository.findByCustomerId(customer.getId()).ifPresentOrElse(user -> {
            if (userRepository.existsByEmailAndCustomerIdNot(customer.getEmail(), customer.getId())) {
                throw new DataConflictException("User email already exists: " + customer.getEmail());
            }

            user.setEmail(customer.getEmail());
            user.setStatus(toUserStatus(customer.getStatus()));
            userRepository.save(user);
        }, () -> log.warn("User not found for customer {}, skipping sync", customer.getId()));
    }

    private UserStatus toUserStatus(CustomerStatus customerStatus) {
        if (customerStatus == null) {
            return UserStatus.PENDING_VERIFICATION;
        }
        return switch (customerStatus) {
            case ACTIVE -> UserStatus.ACTIVE;
            case INACTIVE -> UserStatus.INACTIVE;
            case SUSPENDED -> UserStatus.SUSPENDED;
            case PENDING_VERIFICATION -> UserStatus.PENDING_VERIFICATION;
        };
    }
}
