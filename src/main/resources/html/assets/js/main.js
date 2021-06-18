var loaded = false;

$(document).ready(function(){

    setTimeout(function(){ 

        $('#conn-pending').fadeOut(500, function(){
            $('#conn-success').fadeIn(500);
            $('#home').fadeIn(250);
            loaded = true;
        });
        
    }, 500);

});

function page(pgNum) {

    if (loaded == true) {
        switch(pgNum) {
            case 0:
                $('#guide').fadeOut(250, function(){
                    $('#home').fadeIn(250);
                });
                break;
            case 1:
                $('#home').fadeOut(250, function(){
                    $('#guide').fadeIn(250);
                });
                break;
            case 999:
                $("body").css("overflow", "hidden");

                AnimateRotate(45, '.c-icon');
                $(".c-icon").animate({
                    fontSize: '20vw',
                    opacity: '0.0'
                }, 1000);

                AnimateRotate(25, '.main h1');
                $(".main p").animate({
                    fontSize: '1.5vw'
                }, 1000);

                AnimateRotate(25, '.main p');
                $(".main p").animate({
                    fontSize: '1.5vw'
                }, 1000);

                AnimateRotate(-1000, '#header');

                setTimeout(function(){ 
                
                    AnimateRotate(45, '.main');
                    $(".main").animate({
                        marginTop: '3000px',
                        marginLeft: '-1000px'
                    }, 300);

                    AnimateRotate(45, '#header');
                    $("#header").animate({
                        marginTop: '-1000px',
                        marginRight: '1000px'
                    }, 300);

                    $("#animate-complete").fadeIn();

                }, 800);
                break;
            default:
              break;
        }
    }

}

function AnimateRotate(angle, div) {
    var $elem = $(div);

    $({deg: 0}).animate({deg: angle}, {
        duration: 800,
        step: function(now) {
            $elem.css({
                transform: 'rotate(' + now + 'deg)'
            });
        }
    });
}