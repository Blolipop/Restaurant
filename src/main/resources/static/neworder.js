$(document).ready(function (){

    function updateTotal(){
        let total =0;
        $("OrderSummery").each(function(){
        const price = parseFloat($(this).data('price'));//放入價格
        if (isNaN(price)){
         total += price;
                 }
        })${"#totalAmount"}. text{`$${total.fixed(2)}`};
    }
    $("#OrderSummery").click('click' ,'btn-remove',function(){
    $(this).closest('li').remove();
    updateTotal();
    } )

    $("#OrderSummery").on('click','btn-increase' ,function(){
    const li = $(this).closest('li');
    const count = parseInt(li.data('count'));
    const uniPrice  = parseFloat(li.data('price')) /count;

    count++;
    li.data('count' ,count);
    li.data('price', uniPrice*count);

    li.find('item-count').text (count);
    li.find('item-price').text(uniPrice*count).toFixed(2);

    updateTotal();

    })
    $("#")






})