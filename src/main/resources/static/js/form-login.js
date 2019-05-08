$body = $("body");

$(document).on({
	ajaxStart : function() {
		$body.addClass("loading");
	},
	ajaxStop : function() {
		$body.removeClass("loading");
	}

});

$("#btn-instituicao-form").on('click', function(){
	$("#btn-type-user").addClass("tohide");
	$("#login-user").addClass("tohide");
	$("#login-instituicao").removeClass("tohide");
	$("#voltar-escolha-usuario").removeClass("tohide");
})

$("#voltar-escolha-usuario").on('click', function(){
	$("#btn-type-user").removeClass("tohide");
	$("#login-instituicao").addClass("tohide");
	$("#login-user").addClass("tohide");
	$("#voltar-escolha-usuario").addClass("tohide");
})

$("#btn-usuario-form").on('click', function(){
	$("#btn-type-user").addClass("tohide");
	$("#login-user").removeClass("tohide");
	$("#login-instituicao").addClass("tohide");
	$("#voltar-escolha-usuario").removeClass("tohide");
})


$("#esqueci-senha").on('click', function(){
	var email = document.getElementById("email").value;
	var dados = {
			"email" : email
	}
	
	$.ajax({
		url: "/instituicao/enviar-email-esqueci-senha",
		type:"POST",
		data:dados,
		success: function(result){
			if(result=="Email enviado com sucesso!"){
					$("#alert-sucesso").removeClass("tohide");
					$("#form-email-esqueci-senha").addClass("tohide");
					$("#alert-email-nao-cadastrado").addClass("tohide");
			}
			if(result=="Email não cadastrado!"){
				$("#alert-sucesso").addClass("tohide");
				$("#form-email-esqueci-senha").removeClass("tohide");
				$("#alert-email-nao-cadastrado").removeClass("tohide");
			}
				
			
				
		},
		error: function(){
			
		}
		
	})
	
	
	
})

