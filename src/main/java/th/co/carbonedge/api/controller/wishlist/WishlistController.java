package th.co.carbonedge.api.controller.wishlist;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import th.co.carbonedge.api.dto.wishlist.AddWishlistItemRequest;
import th.co.carbonedge.api.dto.wishlist.CreateWishlistRequest;
import th.co.carbonedge.api.dto.wishlist.MoveWishlistItemToCartRequest;
import th.co.carbonedge.api.dto.wishlist.WishlistStatusResponse;
import th.co.carbonedge.api.dto.wishlist.WishlistResponse;
import th.co.carbonedge.api.exception.DataNotFoundException;
import th.co.carbonedge.api.service.WishlistService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/wishlists")
@Tag(name = "Wishlist", description = "Wishlist APIs")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<WishlistResponse> createWishlist(@Valid @RequestBody CreateWishlistRequest request)
        throws DataNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(wishlistService.createWishlist(request));
    }

    @GetMapping("/{wishlistId}")
    public WishlistResponse getWishlistById(@PathVariable @Min(1) Long wishlistId) throws DataNotFoundException {
        return wishlistService.getWishlistById(wishlistId);
    }

    @GetMapping
    public WishlistResponse getWishlistByCustomerId(@RequestParam @NotBlank String customerId) throws DataNotFoundException {
        return wishlistService.getWishlistByCustomerId(customerId);
    }

    @GetMapping("/check")
    public WishlistStatusResponse checkWishlisted(
        @RequestParam @NotBlank String customerId,
        @RequestParam @NotNull @Min(1) Long productId
    ) throws DataNotFoundException {
        return wishlistService.checkWishlisted(customerId, productId);
    }

    @DeleteMapping("/{wishlistId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWishlist(@PathVariable @Min(1) Long wishlistId) throws DataNotFoundException {
        wishlistService.deleteWishlist(wishlistId);
    }

    @PostMapping("/{wishlistId}/items")
    public WishlistResponse addWishlistItem(
        @PathVariable @Min(1) Long wishlistId,
        @Valid @RequestBody AddWishlistItemRequest request
    ) throws DataNotFoundException {
        return wishlistService.addWishlistItem(wishlistId, request);
    }

    @DeleteMapping("/{wishlistId}/items/{wishlistItemId}")
    public WishlistResponse removeWishlistItem(
        @PathVariable @Min(1) Long wishlistId,
        @PathVariable @Min(1) Long wishlistItemId
    ) throws DataNotFoundException {
        return wishlistService.removeWishlistItem(wishlistId, wishlistItemId);
    }

    @PostMapping("/{wishlistId}/items/{wishlistItemId}/move-to-cart")
    public WishlistResponse moveWishlistItemToCart(
        @PathVariable @Min(1) Long wishlistId,
        @PathVariable @Min(1) Long wishlistItemId,
        @Valid @RequestBody MoveWishlistItemToCartRequest request
    ) throws DataNotFoundException {
        return wishlistService.moveWishlistItemToCart(wishlistId, wishlistItemId, request);
    }
}
