
$('#gasto').on('click',function(e){
        e.preventDefault();
        $('#cantidad').val()*-1;
        $('#gasto').text("Ingreso");
    });
$('#primero').on('click',function(e){
    e.preventDefault();
    $('#primero').css("display","none");
    $('#save').css("display","block");
    $('#cancelar').css("display","block");
});
$('#cancelar').click(function(e){
    e.preventDefault();
    $('#primero').css("display","block");
    $('#save').css("display","none");
    $('#cancelar').css("display","none");
});
$('#tipoMovimiento').on('click',function(e){
    e.preventDefault();
    var c = $('#cantidad').val();
    $('#cantidad').val(c*-1); 
    if($('#tipoMovimiento').text() === "Ingreso"){
        $('#tipoMovimiento').text("Gasto");
    }else{
         $('#tipoMovimiento').text("Ingreso");
    }
});