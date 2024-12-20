'use strict';

document.getElementById('hamburger').addEventListener('click', function() {
    const menu = document.getElementById('menu');
    menu.style.display = menu.style.display === 'block' ? 'none' : 'block';
});

// Закрытие меню при клике вне его
document.addEventListener('click', function(event) {
    const menu = document.getElementById('menu');
    const hamburger = document.getElementById('hamburger');

    if (!hamburger.contains(event.target) && !menu.contains(event.target)) {
        menu.style.display = 'none';
    }
});


