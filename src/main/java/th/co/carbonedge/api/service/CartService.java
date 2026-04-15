package th.co.carbonedge.api.service;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.carbonedge.api.constant.CartStatus;
import th.co.carbonedge.api.constant.CustomerStatus;
import th.co.carbonedge.api.constant.ProductStatus;
import th.co.carbonedge.api.domain.Cart;
import th.co.carbonedge.api.domain.CartItem;
import th.co.carbonedge.api.domain.Product;
import th.co.carbonedge.api.dto.cart.AddCartItemRequest;
import th.co.carbonedge.api.dto.cart.CartResponse;
import th.co.carbonedge.api.dto.cart.CreateCartRequest;
import th.co.carbonedge.api.dto.cart.UpdateCartItemRequest;
import th.co.carbonedge.api.exception.DataConflictException;
import th.co.carbonedge.api.exception.DataNotFoundException;
import th.co.carbonedge.api.repository.CartRepository;
import th.co.carbonedge.api.repository.CustomerRepository;
import th.co.carbonedge.api.repository.ProductRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public CartResponse createCart(CreateCartRequest request) throws DataNotFoundException {
        Cart cart = new Cart();
        if (request != null) {
            cart.setCustomerId(request.getCustomerId());
        }
        validateCustomerForCart(cart.getCustomerId());
        ensureNoActiveCart(cart.getCustomerId());
        cart.setItems(new ArrayList<>());
        cart.setDiscountAmount(BigDecimal.ZERO);
        cart.setStatus(CartStatus.ACTIVE);
        return new CartResponse().setData(toDto(cartRepository.save(cart)));
    }

    @Transactional(readOnly = true)
    public CartResponse getCartById(Long cartId) throws DataNotFoundException {
        return new CartResponse().setData(toDto(findActiveCart(cartId)));
    }

    @Transactional(readOnly = true)
    public CartResponse getActiveCartByCustomerId(String customerId) throws DataNotFoundException {
        validateCustomerForCart(customerId);
        Cart cart = cartRepository.findByCustomerIdAndStatus(customerId, CartStatus.ACTIVE)
            .orElseThrow(() -> new DataNotFoundException("Active cart for customer " + customerId + " not found"));
        return new CartResponse().setData(toDto(cart));
    }

    public void deleteCart(Long cartId) throws DataNotFoundException {
        Cart cart = findActiveCart(cartId);
        cart.setItems(new ArrayList<>());
        cart.setDiscountAmount(BigDecimal.ZERO);
        cart.setStatus(CartStatus.ABANDONED);
        cartRepository.save(cart);
    }

    public CartResponse addCartItem(Long cartId, AddCartItemRequest request) throws DataNotFoundException {
        Cart cart = findActiveCart(cartId);
        Product product = findAvailableProduct(request.getProductId());

        CartItem existingItem = cart.getItems().stream()
            .filter(item -> item.getProductId().equals(request.getProductId()))
            .findFirst()
            .orElse(null);

        if (existingItem != null) {
            int quantity = existingItem.getQuantity() + request.getQuantity();
            existingItem.setQuantity(quantity);
            existingItem.setUnitPrice(product.getPrice());
            existingItem.setLineTotal(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        } else {
            cart.getItems().add(CartItem.builder()
                .id(nextCartItemId(cart.getItems()))
                .productId(product.getId())
                .productName(product.getName())
                .sku(product.getSku())
                .thumbnailUrl(product.getThumbnailUrl())
                .quantity(request.getQuantity())
                .unitPrice(product.getPrice())
                .lineTotal(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())))
                .build());
        }

        return new CartResponse().setData(toDto(cartRepository.save(cart)));
    }

    public CartResponse updateCartItem(Long cartId, Long cartItemId, UpdateCartItemRequest request) throws DataNotFoundException {
        Cart cart = findActiveCart(cartId);
        CartItem cartItem = findCartItem(cart, cartItemId);

        cartItem.setQuantity(request.getQuantity());
        cartItem.setLineTotal(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(request.getQuantity())));

        return new CartResponse().setData(toDto(cartRepository.save(cart)));
    }

    public CartResponse removeCartItem(Long cartId, Long cartItemId) throws DataNotFoundException {
        Cart cart = findActiveCart(cartId);
        CartItem cartItem = findCartItem(cart, cartItemId);
        cart.getItems().remove(cartItem);
        return new CartResponse().setData(toDto(cartRepository.save(cart)));
    }

    private Cart findActiveCart(Long cartId) throws DataNotFoundException {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new DataNotFoundException("Cart " + cartId + " not found"));
        if (cart.getStatus() == CartStatus.ABANDONED) {
            throw new DataNotFoundException("Cart " + cartId + " not found");
        }
        return cart;
    }

    private void validateCustomerForCart(String customerId) throws DataNotFoundException {
        if (customerId == null || customerId.isBlank()) {
            return;
        }
        customerRepository.findByIdAndStatusNot(customerId, CustomerStatus.INACTIVE)
            .orElseThrow(() -> new DataNotFoundException("Customer " + customerId + " not found"));
    }

    private void ensureNoActiveCart(String customerId) {
        if (customerId == null || customerId.isBlank()) {
            return;
        }
        if (cartRepository.findByCustomerIdAndStatus(customerId, CartStatus.ACTIVE).isPresent()) {
            throw new DataConflictException("Customer " + customerId + " already has an active cart");
        }
    }

    private Product findAvailableProduct(Long productId) throws DataNotFoundException {
        return productRepository.findByIdAndStatusNot(productId, ProductStatus.ARCHIVED)
            .orElseThrow(() -> new DataNotFoundException("Product " + productId + " not found"));
    }

    private CartItem findCartItem(Cart cart, Long cartItemId) throws DataNotFoundException {
        return cart.getItems().stream()
            .filter(item -> item.getId().equals(cartItemId))
            .findFirst()
            .orElseThrow(() -> new DataNotFoundException("Cart item " + cartItemId + " not found"));
    }

    private Long nextCartItemId(List<CartItem> items) {
        return items.stream()
            .map(CartItem::getId)
            .filter(id -> id != null)
            .max(Comparator.naturalOrder())
            .orElse(0L) + 1;
    }

    private th.co.carbonedge.api.dto.cart.Cart toDto(Cart cart) {
        return new th.co.carbonedge.api.dto.cart.Cart()
            .setId(cart.getId())
            .setCustomerId(cart.getCustomerId())
            .setStatus(cart.getStatus())
            .setItems(toDtoItems(cart.getItems()))
            .setSubtotal(toDouble(cart.getSubtotal()))
            .setDiscountAmount(toDouble(cart.getDiscountAmount()))
            .setTotalAmount(toDouble(cart.getTotalAmount()))
            .setTotalItems(cart.getTotalItems())
            .setCreatedAt(cart.getCreatedAt())
            .setUpdatedAt(cart.getUpdatedAt());
    }

    private List<th.co.carbonedge.api.dto.cart.CartItem> toDtoItems(List<CartItem> items) {
        if (items == null) {
            return new ArrayList<>();
        }
        return items.stream()
            .map(item -> new th.co.carbonedge.api.dto.cart.CartItem()
                .setId(item.getId())
                .setProductId(item.getProductId())
                .setProductName(item.getProductName())
                .setSku(item.getSku())
                .setThumbnailUrl(toUri(item.getThumbnailUrl()))
                .setQuantity(item.getQuantity())
                .setUnitPrice(toDouble(item.getUnitPrice()))
                .setLineTotal(toDouble(item.getLineTotal())))
            .toList();
    }

    private Double toDouble(BigDecimal value) {
        return value == null ? null : value.doubleValue();
    }

    private URI toUri(String value) {
        return value == null || value.isBlank() ? null : URI.create(value);
    }
}
