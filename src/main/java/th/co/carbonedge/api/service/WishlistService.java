package th.co.carbonedge.api.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.carbonedge.api.constant.CustomerStatus;
import th.co.carbonedge.api.constant.CartStatus;
import th.co.carbonedge.api.constant.ProductStatus;
import th.co.carbonedge.api.domain.Cart;
import th.co.carbonedge.api.domain.CartItem;
import th.co.carbonedge.api.domain.Customer;
import th.co.carbonedge.api.domain.Product;
import th.co.carbonedge.api.domain.Wishlist;
import th.co.carbonedge.api.dto.wishlist.AddWishlistItemRequest;
import th.co.carbonedge.api.dto.wishlist.CreateWishlistRequest;
import th.co.carbonedge.api.dto.wishlist.MoveWishlistItemToCartRequest;
import th.co.carbonedge.api.dto.wishlist.WishlistStatusResponse;
import th.co.carbonedge.api.dto.wishlist.WishlistResponse;
import th.co.carbonedge.api.exception.DataConflictException;
import th.co.carbonedge.api.exception.DataNotFoundException;
import th.co.carbonedge.api.repository.CartRepository;
import th.co.carbonedge.api.repository.CustomerRepository;
import th.co.carbonedge.api.repository.ProductRepository;
import th.co.carbonedge.api.repository.WishlistRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public WishlistResponse createWishlist(CreateWishlistRequest request) throws DataNotFoundException {
        findActiveCustomer(request.getCustomerId());
        if (wishlistRepository.existsByCustomerId(request.getCustomerId())) {
            throw new DataConflictException("Wishlist already exists for customer: " + request.getCustomerId());
        }

        Wishlist wishlist = Wishlist.builder()
            .customerId(request.getCustomerId())
            .items(new ArrayList<>())
            .build();

        return new WishlistResponse().setData(toDto(wishlistRepository.save(wishlist)));
    }

    @Transactional(readOnly = true)
    public WishlistResponse getWishlistById(Long wishlistId) throws DataNotFoundException {
        return new WishlistResponse().setData(toDto(findWishlist(wishlistId)));
    }

    @Transactional(readOnly = true)
    public WishlistResponse getWishlistByCustomerId(String customerId) throws DataNotFoundException {
        findActiveCustomer(customerId);
        Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
            .orElseThrow(() -> new DataNotFoundException("Wishlist for customer " + customerId + " not found"));
        return new WishlistResponse().setData(toDto(wishlist));
    }

    public void deleteWishlist(Long wishlistId) throws DataNotFoundException {
        wishlistRepository.delete(findWishlist(wishlistId));
    }

    public WishlistResponse addWishlistItem(Long wishlistId, AddWishlistItemRequest request)
        throws DataNotFoundException {
        Wishlist wishlist = findWishlist(wishlistId);
        Product product = findWishlistableProduct(request.getProductId());

        boolean alreadyExists = wishlist.getItems().stream()
            .anyMatch(item -> item.getProductId().equals(request.getProductId()));
        if (alreadyExists) {
            throw new DataConflictException("Product already exists in wishlist: " + request.getProductId());
        }

        wishlist.getItems().add(th.co.carbonedge.api.domain.WishlistItem.builder()
            .id(nextWishlistItemId(wishlist.getItems()))
            .productId(product.getId())
            .productName(product.getName())
            .sku(product.getSku())
            .thumbnailUrl(product.getThumbnailUrl())
            .price(product.getPrice())
            .currency(product.getCurrency())
            .addedAt(OffsetDateTime.now())
            .build());

        return new WishlistResponse().setData(toDto(wishlistRepository.save(wishlist)));
    }

    public WishlistResponse removeWishlistItem(Long wishlistId, Long wishlistItemId) throws DataNotFoundException {
        Wishlist wishlist = findWishlist(wishlistId);
        th.co.carbonedge.api.domain.WishlistItem item = wishlist.getItems().stream()
            .filter(currentItem -> wishlistItemId.equals(currentItem.getId()))
            .findFirst()
            .orElseThrow(() -> new DataNotFoundException("Wishlist item " + wishlistItemId + " not found"));

        wishlist.getItems().remove(item);
        return new WishlistResponse().setData(toDto(wishlistRepository.save(wishlist)));
    }

    public WishlistResponse moveWishlistItemToCart(
        Long wishlistId,
        Long wishlistItemId,
        MoveWishlistItemToCartRequest request
    ) throws DataNotFoundException {
        Wishlist wishlist = findWishlist(wishlistId);
        th.co.carbonedge.api.domain.WishlistItem wishlistItem = wishlist.getItems().stream()
            .filter(currentItem -> wishlistItemId.equals(currentItem.getId()))
            .findFirst()
            .orElseThrow(() -> new DataNotFoundException("Wishlist item " + wishlistItemId + " not found"));

        Cart cart = findActiveCart(request.getCartId());
        Product product = findWishlistableProduct(wishlistItem.getProductId());

        CartItem existingItem = cart.getItems().stream()
            .filter(item -> item.getProductId().equals(product.getId()))
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

        cartRepository.save(cart);
        wishlist.getItems().remove(wishlistItem);
        return new WishlistResponse().setData(toDto(wishlistRepository.save(wishlist)));
    }

    @Transactional(readOnly = true)
    public WishlistStatusResponse checkWishlisted(String customerId, Long productId) throws DataNotFoundException {
        findActiveCustomer(customerId);
        var wishlist = wishlistRepository.findByCustomerId(customerId).orElse(null);
        var wishlistItem = wishlist == null ? null : wishlist.getItems().stream()
            .filter(item -> productId.equals(item.getProductId()))
            .findFirst()
            .orElse(null);

        return new WishlistStatusResponse().setData(
            new th.co.carbonedge.api.dto.wishlist.WishlistStatus()
                .setCustomerId(customerId)
                .setProductId(productId)
                .setWishlisted(wishlistItem != null)
                .setWishlistId(wishlist == null ? null : wishlist.getId())
                .setWishlistItemId(wishlistItem == null ? null : wishlistItem.getId())
        );
    }

    private Wishlist findWishlist(Long wishlistId) throws DataNotFoundException {
        return wishlistRepository.findById(wishlistId)
            .orElseThrow(() -> new DataNotFoundException("Wishlist " + wishlistId + " not found"));
    }

    private Customer findActiveCustomer(String customerId) throws DataNotFoundException {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new DataNotFoundException("Customer " + customerId + " not found"));
        if (customer.getStatus() == CustomerStatus.INACTIVE) {
            throw new DataNotFoundException("Customer " + customerId + " not found");
        }
        return customer;
    }

    private Cart findActiveCart(Long cartId) throws DataNotFoundException {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new DataNotFoundException("Cart " + cartId + " not found"));
        if (cart.getStatus() == CartStatus.ABANDONED) {
            throw new DataNotFoundException("Cart " + cartId + " not found");
        }
        return cart;
    }

    private Product findWishlistableProduct(Long productId) throws DataNotFoundException {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new DataNotFoundException("Product " + productId + " not found"));
        if (product.getStatus() == ProductStatus.ARCHIVED) {
            throw new DataNotFoundException("Product " + productId + " not found");
        }
        return product;
    }

    private Long nextWishlistItemId(List<th.co.carbonedge.api.domain.WishlistItem> items) {
        return items.stream()
            .map(th.co.carbonedge.api.domain.WishlistItem::getId)
            .filter(id -> id != null)
            .max(Comparator.naturalOrder())
            .orElse(0L) + 1;
    }

    private Long nextCartItemId(List<CartItem> items) {
        return items.stream()
            .map(CartItem::getId)
            .filter(id -> id != null)
            .max(Comparator.naturalOrder())
            .orElse(0L) + 1;
    }

    private th.co.carbonedge.api.dto.wishlist.Wishlist toDto(Wishlist wishlist) {
        return new th.co.carbonedge.api.dto.wishlist.Wishlist()
            .setId(wishlist.getId())
            .setCustomerId(wishlist.getCustomerId())
            .setTotalItems(wishlist.getItems() == null ? 0 : wishlist.getItems().size())
            .setItems(toDtoItems(wishlist.getItems()))
            .setCreatedAt(wishlist.getCreatedAt())
            .setUpdatedAt(wishlist.getUpdatedAt());
    }

    private List<th.co.carbonedge.api.dto.wishlist.WishlistItem> toDtoItems(List<th.co.carbonedge.api.domain.WishlistItem> items) {
        if (items == null) {
            return new ArrayList<>();
        }
        return items.stream()
            .map(item -> new th.co.carbonedge.api.dto.wishlist.WishlistItem()
                .setId(item.getId())
                .setProductId(item.getProductId())
                .setProductName(item.getProductName())
                .setSku(item.getSku())
                .setThumbnailUrl(item.getThumbnailUrl())
                .setPrice(toDouble(item.getPrice()))
                .setCurrency(item.getCurrency())
                .setAddedAt(item.getAddedAt()))
            .toList();
    }

    private Double toDouble(BigDecimal value) {
        return value == null ? null : value.doubleValue();
    }
}
