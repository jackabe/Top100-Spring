var timeLeft = 1800;
var myVar = setInterval(myTimer, 1000);

$(document).ready(function () {

    getBank();

    function getMarket() {

        $.ajax({//send the AJAX request
                type: "GET",
                url: "/api/market/all",
                dataType: "json",
                encode: true
        }).done(function (data) { //what to do if the request is a success.

        var changeClass;

        $.each(data, function (index, data) {

            if (data.hasShares == true) {
                if (data.transaction.company.priceChange.includes('+/')) {
                    changeClass = 'company-price-change-none';
                }
                else if (data.transaction.company.priceChange.includes('-')) {
                    changeClass = 'company-price-change-down';
                }
                else {
                    changeClass = 'company-price-change-up';
                }
            }
            else {
                if (data.company.priceChange.includes('+/')) {
                    changeClass = 'company-price-change-none';
            }
            else if (data.company.priceChange.includes('-')) {
                    changeClass = 'company-price-change-down';
            }
            else {
                    changeClass = 'company-price-change-up';
            }

            }

            if (data.hasShares == true) {

            console.log(data);

                 $("#market-table > tbody")
                    .append($('<tr class="row100 body">')
                        .append($('<td class="cell100 column1">')
                            .append(data.transaction.company.companyName)
                            .append('<span class='+changeClass+'>'+data.transaction.company.priceChange+'</span>')
                        )
                        .append($('<td class="cell100 column2">')
                            .append(data.transaction.company.marketType)
                        )
                        .append($('<td class="cell100 column3">')
                            .append('£'+data.transaction.company.sharePrice)
                        )
                        .append($('<td class="cell100 column-actions">')
                            .append('<a href="#" class="market-action-button buy" data-company-data="'+data.transaction.id+'">Buy</a>')
                            .append('<a href="#" class="market-action-button sell" data-company-data="'+data.transaction.id+'">Sell</a>')
                            .append('<span class="own-span">You own '+data.transaction.amount+'</span>')
                        )
                        .append($('<td class="cell100 column4">')
                            .append(data.transaction.company.sharesAvailable)
                        )
                    )
            }

            else {

                $("#market-table > tbody")
                    .append($('<tr class="row100 body">')
                        .append($('<td class="cell100 column1">')
                            .append(data.company.companyName)
                            .append('<span class='+changeClass+'>'+data.company.priceChange+'</span>')
                        )
                        .append($('<td class="cell100 column2">')
                            .append(data.company.marketType)
                        )
                        .append($('<td class="cell100 column3">')
                            .append('£'+data.company.sharePrice)
                        )
                        .append($('<td class="cell100 column-actions">')
                            .append('<a href="#" class="market-new-action-button buy" data-company-data="'+data.company.id+'">Buy</a>')
                        )
                        .append($('<td class="cell100 column4">')
                            .append(data.company.sharesAvailable)
                        )
                    )
            }

        });

        }).fail(function (jqXHR, textStatus, errorThrown) { // and what to do if it fails
            console.log(errorThrown);
        });
    }

    getMarket();

    var companyId = 0;

    $(document).on('click','.market-new-action-button.buy',function() {
        var marketRowId = $(this).data("company-data")

        $('#market-purchase').data('id-attr', marketRowId);
        $('#market-purchase').data('id-type', 'buy');

        $.ajax({//send the AJAX request
                        type: "GET",
                        url: "/api/companies/company/"+marketRowId ,
                        dataType: "json",
                        encode: true
                }).done(function (marketData) { //what to do if the request is a success.
                    $('.modal-company-name').html(marketData.companyName);
                    $('.modal-price').html('£'+marketData.sharePrice);
                    $('.modal-total').html(0);
                    companyId = marketData.id;
                    $('#market-modal').fadeIn();
                }).fail(function (jqXHR, textStatus, errorThrown) { // and what to do if it fails
                    console.log(errorThrown);
                });

    });

    $(document).on('click','.market-action-button.buy',function() {
            var marketRowId = $(this).data("company-data")
            $('#market-purchase').data('id-attr', marketRowId);
            $('#market-purchase').data('id-type', 'buy');

            $.ajax({//send the AJAX request
                            type: "GET",
                            url: "/api/transactions/transaction/"+marketRowId ,
                            dataType: "json",
                            encode: true
                    }).done(function (marketData) { //what to do if the request is a success.
                        $('.modal-company-name').html(marketData.company.companyName);
                        $('.modal-price').html('£'+marketData.company.sharePrice);
                        companyId = marketData.company.id;
                        $('#market-modal').fadeIn();
                    }).fail(function (jqXHR, textStatus, errorThrown) { // and what to do if it fails
                        console.log(errorThrown);
                    });

        });

    $(document).on('click','.market-action-button.sell',function() {
        var marketRowId = $(this).data("company-data")
        $('#market-purchase').data('id-type', 'sell');
        $('#market-purchase').data('id-attr', marketRowId);

        $.ajax({//send the AJAX request
                        type: "GET",
                        url: "/api/transactions/transaction/"+marketRowId ,
                        dataType: "json",
                        encode: true
                }).done(function (marketData) { //what to do if the request is a success.
                console.log(marketData);
                    $('.modal-company-name').html(marketData.company.companyName);
                    $('.modal-price').html('£'+marketData.company.sharePrice);
                    $('#market-transaction-qty').val(marketData.amount);
                    $('#market-transaction-qty').data('previous-qty', marketData.amount);
                    companyId = marketData.company.id;
                    $('#market-modal').fadeIn();
                }).fail(function (jqXHR, textStatus, errorThrown) { // and what to do if it fails
                    console.log(errorThrown);
                });
    });

    $('.market-modal-close').on('click',function(){
        $('#market-modal').hide();
    });

    $('#market-purchase').on('click',function(e){
        var purchaseId = $(this).data('id-attr');
        var type = $(this).data('id-type');
        var previousAmount = $('#market-transaction-qty').data('previous-qty');

        if (type === 'sell') {
            var qty = $('#market-transaction-qty').val();
            if (qty > previousAmount) {
                alert('You cannot sell more than you have!')
                $('#market-transaction-qty').val(previousAmount);
                return '';
            }
        }

        var formData = { //create a JSON object from the form field.
                        'companyId': companyId,
                        'transactionId': purchaseId,
                        'sharePrice': parseFloat($('.modal-price').html().slice(1)),
                        'amount': parseInt($('#market-transaction-qty').val()),
                        'type' : type
                    };

                    console.log(formData);

                    $.ajax({//send the AJAX request
                        type: "POST",
                        url: "/api/transactions/player/new",
                        data: formData,
                        dataType: "text",
                        encode: true
                    }).done(function (data) { //what to do if the request is a success.
                        console.log("success");
                        console.log("redirect to home");
                        location.reload();
                        // window.location.assign("/"); //redirect to home page.
                    }).fail(function (jqXHR, textStatus, errorThrown) { //and what to do if it fails

                    });
                    return false;
                    e.preventDefault(); //don't handle the event normally.
    });




    // SHOPPING CART PLUS OR MINUS
    $('a.qty-minus').on('click', function(e) {
        e.preventDefault();
        var $this = $(this);
        var $input = $this.closest('div').find('input');
        var value = parseInt($input.val());

        if (value > 1) {
            value = value - 1;
        } else {
            value = 0;
        }

        var price = $('.modal-price').html().slice(1);
        var total = calculateTotalPrice(price, value);
        $('.modal-total').html(total);


    $input.val(value);

    });

    $('a.qty-plus').on('click', function(e) {
        e.preventDefault();
        var $this = $(this);
        var $input = $this.closest('div').find('input');
        var value = parseInt($input.val());

        if (value < 100) {
        value = value + 1;
        } else {
            value =100;
        }

        $input.val(value);

        var price = $('.modal-price').html().slice(1);
        var total = calculateTotalPrice(price, value);
        $('.modal-total').html(total);
    });

    // RESTRICT INPUTS TO NUMBERS ONLY WITH A MIN OF 0 AND A MAX 100
    $('input').on('blur', function(){

    var input = $(this);
    var value = parseInt($(this).val());

        if (value < 0 || isNaN(value)) {
            input.val(0);
        } else if
            (value > 100) {
            input.val(100);
        }

        var price = $('.modal-price').html().slice(1);
        var total = calculateTotalPrice(price, value);
        $('.modal-total').html(total);

    });


    });


    function myTimer() {
    timeLeft --;
    var minutes = Math.floor(timeLeft / 60);
    var seconds = timeLeft - minutes * 60;

    document.getElementById("right").innerHTML = "Window opens: " +minutes + ":" + seconds;
    }

    // Connect to notification schedular
    var stompClient = null;

    function connect() {
    var socket = new SockJS('/data');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
    // setConnected(true);
    console.log('CONNECTED TO FRAME : ' + frame);
    stompClient.subscribe('/queue/data', function (message) {

        // get activity and set in notification container
        var activity = (JSON.parse(message.body));

        console.log(activity);
    });
    });
    }

    function disconnect() {
    if (stompClient !== null) {
    stompClient.disconnect();
    }
    // setConnected(false);
    console.log("Disconnected");
    }

    function calculateTotalPrice(number, sharePrice) {
        return '£'+(number*sharePrice).toFixed(2);
    }


    //connect();

    function getBank() {

            $.ajax({//send the AJAX request
                    type: "GET",
                    url: "/api/users/player/bank",
                    dataType: "json",
                    encode: true
            }).done(function (data) { //what to do if the request is a success.
                $('.player-bank').html('Money in bank: £'+data.toFixed(2))

            }).fail(function (jqXHR, textStatus, errorThrown) { // and what to do if it fails
                console.log(errorThrown);
            });
           }