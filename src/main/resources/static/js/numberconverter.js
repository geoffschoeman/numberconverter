function convertToString() {
    var input = $('#numberInput').val();

    if (isValidNumberWithinRange(input)) {
        removeErrorClass();
        $.get('/convert/' + input)
                .done(function (data) {
                    $('#converted_number').html(data);
                    $('#converted_number').removeClass("text-danger"); 
                })
                .fail(function () {
                    $('#converted_number').html('Error');
                    addErrorClass(); 
                });
    } else {
        $('#converted_number').html('Must be a numerical value from -2147483648 to 2147483647');
        addErrorClass(); 
    }
}

function isValidNumberWithinRange(input) {
    return isNumeric(input) && isWithinRange(input, -2147483648, 2147483647);
}

function isNumeric(input) {
    return (input - 0) == input && (''+input).trim().length > 0;
}

function isWithinRange(input, low, high) {
    return input >= low && input <= high;
}

function addErrorClass() {
    $('#converted_number').removeClass('text-dark').addClass('text-danger');
}

function removeErrorClass() {
    $('#converted_number').removeClass('text-danger').addClass('text-dark');
}


