$(document).ready(function () {

    function updateTotal() { //先定義一個function 更新價格
        let total = 0;
        $('#orderSummary li').each(function () {
            const price = parseFloat($(this).data('price'));
            if (!isNaN(price)) {
                total += price;
            }
        });
        $('#totalAmount').text(`$${total.toFixed(2)}`);
    }
    // 刪除項目
    $('#orderSummary').on('click', '.btn-remove', function () { //刪除
        $(this).closest('li').remove();
        updateTotal();
    });

    // 增加數量
    $('#orderSummary').on('click', '.btn-increase', function () {//增加
        const li = $(this).closest('li');
        let count = parseInt(li.data('count')) || 1;//防呆處理 為了怕說未寫入 還是寫入了錯誤資料型態
        const unitPrice = parseFloat(li.data('price')) / count;

        count++;
        li.data('count', count);
        li.data('price', unitPrice * count);

        li.find('.item-count').text(count);
        li.find('.item-total').text((unitPrice * count).toFixed(2));

        updateTotal();
    });

    // 減少數量（最小為 1）
    $('#orderSummary').on('click', '.btn-decrease', function () {
        const li = $(this).closest('li');
        let count = parseInt(li.data('count')) || 1;
        if (count <= 1) return;

        const unitPrice = parseFloat(li.data('price')) / count;

        count--;
        li.data('count', count);
        li.data('price', unitPrice * count);

        li.find('.item-count').text(count);
        li.find('.item-total').text((unitPrice * count).toFixed(2));

        updateTotal();
    });


    // 點擊菜單項目時，加入到 Order Summary
    $('.list-group-item').on('click', function () {
        const dishId = $(this).find('div').data('dish-id'); // 讀取 data-dish-id
        const name = $(this).find('span:first').text();     // 名稱
        const unitPrice = parseFloat($(this).find('span:last').text()); // 單價

        // 檢查該 dish 是否已存在右側清單
        const existingItem = $(`#orderSummary li[data-dish-id="${dishId}"]`);

        if (existingItem.length > 0) {
            // 若已存在，更新數量與總價
            let count = parseInt(existingItem.data('count')) || 1;
            count++;
            existingItem.data('count', count);
            existingItem.data('price', unitPrice * count);

            existingItem.find('.item-count').text(count);
            existingItem.find('.item-total').text((unitPrice * count).toFixed(2));
        } else {
            // 若不存在，新增一筆
            $('#orderSummary').append(`
                <li class="list-group-item d-flex justify-content-between align-items-center"
                    data-dish-id="${dishId}"
                    data-price="${unitPrice}"
                    data-count="1">
                    <div>
                        <span class="item-name">${name}</span>
                        x<span class="item-count">1</span>
                        - $<span class="item-total">${unitPrice.toFixed(2)}</span>
                    </div>
                    <div>
                        <button class="btn btn-sm btn-outline-secondary btn-decrease">-</button>
                        <button class="btn btn-sm btn-outline-secondary btn-increase">+</button>
                        <button class="btn btn-sm btn-outline-danger btn-remove">🗑️</button>
                    </div>
                </li>
            `);
        }

        updateTotal();
    });


    // 點擊「送出訂單」按鈕，顯示 modal 並複製資料
    $('#placeOrderBtn').on('click', function () {
        $('#modalOrderItems').html('');
        $('#orderSummary li').each(function () {
            const name = $(this).text();
            $('#modalOrderItems').append(`<li class="list-group-item">${name}</li>`);
        });

        $('#modalTotalAmount').text($('#totalAmount').text());
        $('#createOrderModal').modal('show');
    });

    // 點擊「確認送出」，組成資料並送出 API
    $('#confirmPlaceOrder').on('click', function () {
        const orderItems = [    ];//所有物件

        $('#orderSummary li').each(function () {
            const dishId = parseInt($(this).data('dish-id'));
            const price = parseInt($(this).data('price'));
            const count = 1; // 如果想支援多份，這裡可加上數量功能

            if (dishId && price) {
                orderItems.push({
                    dishId: dishId,
                    count: count,
                    price: price
                });
            }
        });

        fetch('/orders/place', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(orderItems)
        })
        .then(response => {
            if (!response.ok) throw new Error('送出失敗');
            return response.json();
        })
        .then(data => {
            alert('訂單已送出！訂單編號：' + data.orderId);
            $('#createOrderModal').modal('hide');
        })
        .catch(error => {
            console.error('錯誤:', error);
            alert('送出失敗，請稍後再試。');
        });
    });
});
//// order.js 中某個按鈕事件：
//console.log("✅ order.js 載入成功");
//
//$('#placeOrderBtn').on('click', function () {
//    console.log("送出訂單按鈕被點擊");
//});
//console.log("order.js 載入成功");
//
//$('#placeOrderBtn').on('click', function () {
//    // 假設你已有資料結構可以 render 購物清單
//    $('#modalOrderItems').html(''); // 清空舊資料
//    $('#orderSummary li').each(function () {
//        const itemText = $(this).text();
//        $('#modalOrderItems').append(`<li class="list-group-item">${itemText}</li>`);
//    });
//
//    $('#modalTotalAmount').text($('#totalAmount').text());
//
//    // 顯示 modal
//    $('#createOrderModal').modal('show');
//});
//
//$('#confirmPlaceOrder').on('click', function () {
//    // 呼叫下單 API 或提交表單邏輯
//    alert('訂單已送出！');
//    $('#createOrderModal').modal('hide');
//});