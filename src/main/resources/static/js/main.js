var timeLeft = 1800;
var myVar = setInterval(myTimer, 1000);

$(document).ready(function () {

    function getMarket() {

      $.ajax({//send the AJAX request
                type: "GET",
                url: "/api/market/all",
                dataType: "json",
                encode: true
            }).done(function (data) { //what to do if the request is a success.

                $.each(data, function (index, data) {

                  if (data.hasShares == true) {

                    var changeClass;
                    if (data.transaction.company.priceChange.includes('+/')) {
                        changeClass = 'company-price-change-none';
                    }
                    else if (data.transaction.company.priceChange.includes('-')) {
                        changeClass = 'company-price-change-down';
                    }
                    else {
                        changeClass = 'company-price-change-up';
                    }

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
                              .append(data.transaction.company.sharePrice)
                            )
                             .append($('<td class="cell100 column-actions">')
                              .append('<a href="#" class="market-action-button buy">Buy</a>')
                              .append('<a href="#" class="market-action-button sell">Sell</a>')
                                .append('<span class="own-span">You own '+data.transaction.amount+'</span>')
                              )
                            .append($('<td class="cell100 column4">')
                              .append(data.transaction.company.sharesAvailable)
                            )
                        )
                        .find('.market-action-button').data("company-data", data);
                      }

                      else {

                      var changeClass;
                      if (data.company.priceChange.includes('+/')) {
                          changeClass = 'company-price-change-none';
                      }
                      else if (data.company.priceChange.includes('-')) {
                          changeClass = 'company-price-change-down';
                      }
                      else {
                          changeClass = 'company-price-change-up';
                      }
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
                                          .append(data.company.sharePrice)
                                        )
                                        .append($('<td class="cell100 column-actions">')
                                         .append('<a href="#" class="market-action-button buy">Buy</a>')
                                         )
                                        .append($('<td class="cell100 column4">')
                                          .append(data.company.sharesAvailable)
                                        )
                                    )
                                    .find('.market-action-button').data("company-data", data);
                     }
                });

                }).fail(function (jqXHR, textStatus, errorThrown) { // and what to do if it fails
                    console.log(errorThrown);
                });
    }

    getMarket();

    $(document).on('click','.market-action-button.buy',function() {
        hello = ($(this).data("company-data"));
        $.each(hello, function(key, element) {
            console.log('key: ' + key + '\n' + 'value: ' + element);
        });
    });

    $(document).on('click','.market-action-button.sell',function() {
            hello = ($(this).data("company-data"));
            $.each(hello, function(key, element) {
                console.log('key: ' + key + '\n' + 'value: ' + element);
            });
        });



     $('.visibility-cart').on('click',function(){

      var $btn =  $(this);
      var $cart = $('.cart');
      console.log($btn);

      if ($btn.hasClass('is-open')) {
         $btn.removeClass('is-open');
         $btn.text('O')
         $cart.removeClass('is-open');
         $cart.addClass('is-closed');
         $btn.addClass('is-closed');
      } else {
         $btn.addClass('is-open');
         $btn.text('X')
         $cart.addClass('is-open');
         $cart.removeClass('is-closed');
         $btn.removeClass('is-closed');
      }


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


//connect();