package th.co.carbonedge.api.controller.cart;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import th.co.carbonedge.api.dto.cart.AddCartItemRequest;
import th.co.carbonedge.api.dto.cart.CartResponse;
import th.co.carbonedge.api.dto.cart.CreateCartRequest;
import th.co.carbonedge.api.dto.cart.UpdateCartItemRequest;
import th.co.carbonedge.api.exception.DataNotFoundException;
import th.co.carbonedge.api.service.CartService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/carts")
@Tag(name = "Cart", description = "Shopping cart APIs")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponse> createCart(@Valid @RequestBody(required = false) CreateCartRequest request)
        throws DataNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.createCart(request));
    }

    @GetMapping("/{cartId}")
    public CartResponse getCartById(@PathVariable @Min(1) Long cartId) throws DataNotFoundException {
        return cartService.getCartById(cartId);
    }

    @GetMapping("/active")
    public CartResponse getActiveCartByCustomerId(@RequestParam @NotBlank String customerId) throws DataNotFoundException {
        return cartService.getActiveCartByCustomerId(customerId);
    }

    @DeleteMapping("/{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCart(@PathVariable @Min(1) Long cartId) throws DataNotFoundException {
        cartService.deleteCart(cartId);
    }

    @PostMapping("/{cartId}/items")
    public CartResponse addCartItem(
        @PathVariable @Min(1) Long cartId,
        @Valid @RequestBody AddCartItemRequest request
    ) throws DataNotFoundException {
        return cartService.addCartItem(cartId, request);
    }

    @PutMapping("/{cartId}/items/{cartItemId}")
    public CartResponse updateCartItem(
        @PathVariable @Min(1) Long cartId,
        @PathVariable @Min(1) Long cartItemId,
        @Valid @RequestBody UpdateCartItemRequest request
    ) throws DataNotFoundException {
        return cartService.updateCartItem(cartId, cartItemId, request);
    }

    @DeleteMapping("/{cartId}/items/{cartItemId}")
    public CartResponse removeCartItem(
        @PathVariable @Min(1) Long cartId,
        @PathVariable @Min(1) Long cartItemId
    ) throws DataNotFoundException {
        return cartService.removeCartItem(cartId, cartItemId);
    }
}
