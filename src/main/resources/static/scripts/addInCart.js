'use strict';
let cartItemCount = 0;

// Функция для обновления счетчика
function refreshCartCount() {
    const cartCounter = document.querySelector('.cart-counter');
    if (cartCounter) {
        cartCounter.innerText = cartItemCount;
        cartCounter.style.display = cartItemCount > 0 ? 'block' : 'none';
    }
}

// Пример функции добавления товара в корзину
function addToCart(productId) {
    cartItemCount++;
    refreshCartCount();
}

// Инициализация при загрузке страницы
document.addEventListener('DOMContentLoaded', () => {
    refreshCartCount();
});

