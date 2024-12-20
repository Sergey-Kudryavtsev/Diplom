'use strict';
function confirmDelivery() {
    alert("Товар доставлен!");
    return true;
}

function confirmDecline() {
    return confirm("Вы уверены, что хотите отказать в доставке?");
}

function confirmDelete() {
    return confirm("Вы уверены, что хотите удалить эту категорию?");
}