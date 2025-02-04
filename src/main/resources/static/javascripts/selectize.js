$(document).ready(function(){
    $('.js-selectize').selectize({closeAfterSelect: true});
});

    $(document).ready(function(){
        $(".add").selectize({
        plugins: ['autofill_disable'],
        closeAfterSelect: true,
        delimiter: " ",
        persist: false,
        create: function (input) {
            return {
                value: input,
                text: input
            };
        },
        });
    });