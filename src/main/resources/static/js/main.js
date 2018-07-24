$(document).ready(function () {

    function getMarket() {

      $.ajax({//send the AJAX request
                type: "GET",
                url: "/api/market/all",
                dataType: "json",
                encode: true
            }).done(function (data) { //what to do if the request is a success.

                $.each(data, function (index, data) {

                if (data.hasShares = true) {
                 $("#market-table")
                    .append($('<tr>')
                        .append($('<td>')
                          .append(data.transaction.company.companyName)
                        )
                        .append($('<td>')
                          .append(data.transaction.company.sharesAvailable)
                        )
                        .append($('<td>')
                          .append(data.transaction.company.sharePrice)
                        )
                        .append($('<td>')
                          .append('<button>Buy</button>')
                        )
                        .append($('<td>')
                          .append('<button>Sell your shares</button>')
                        )
                    );
                  }

                  else {
                    $("#market-table")
                        .append($('<tr>')
                            .append($('<td>')
                              .append(data.transaction.company.companyName)
                            )
                            .append($('<td>')
                              .append(data.transaction.company.sharesAvailable)
                            )
                            .append($('<td>')
                              .append(data.transaction.company.sharePrice)
                            )
                            .append($('<td>')
                              .append('<button>Buy</button>')
                            )
                        );
                    }
            });

            }).fail(function (jqXHR, textStatus, errorThrown) { // and what to do if it fails
                console.log(errorThrown);
            });
    }




    getMarket();

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


