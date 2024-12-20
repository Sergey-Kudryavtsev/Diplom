'use strict';

const selectColor = document.getElementById('select_color');
const selectSize = document.getElementById('select_size');
const inputProductId = document.getElementById('input_productId');

const updateCartCount = () => {
    $.get('/cart/count', (count) => {
        document.getElementById('cart-count').innerText = count; // Обновляем текст счетчика
    });
};

const addToCartRequest = () => $.ajax({
    url: '/cart/add',
    method: 'POST',
    dataType: 'html',
    data: {
        color: selectColor.value,
        memorySize: selectSize.value,
        productId: inputProductId.value
    },
    success: (response) => {
        updateCartCount(); // Обновляем счетчик после добавления
    },
    error: (xhr, status, error) => {
        alert('Ошибка при добавлении товара в корзину. Пожалуйста, попробуйте снова');
    }
});

// Добавляем обработчик события для загрузки страницы
document.addEventListener('DOMContentLoaded', () => {
    updateCartCount(); // Обновляем счетчик при загрузке страницы
});

// Привязываем событие нажатия к кнопке добавления товара
const button = document.querySelector('button'); // Замените селектор на свой, если нужно
if(button) {
    button.addEventListener('click', addToCartRequest);
}









