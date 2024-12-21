'use strict';

const selectColor = document.getElementById('select_color');
const selectSize = document.getElementById('select_size');
const inputProductId = document.getElementById('input_productId');

const updateCartCount = () => {
    $.get('/cart/count', (count) => {
        document.getElementById('cart-count').innerText = count;
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
        updateCartCount();
    },
    error: (xhr, status, error) => {
        alert('Ошибка при добавлении товара в корзину. Пожалуйста, попробуйте снова');
    }
});


document.addEventListener('DOMContentLoaded', () => {
    updateCartCount();
});


const button = document.querySelector('button');
if(button) {
    button.addEventListener('click', addToCartRequest);
}









