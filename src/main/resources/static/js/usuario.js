$body = $("body");

$(document).on({
	ajaxStart : function() {
		$body.addClass("loading");
	},
	ajaxStop : function() {
		$body.removeClass("loading");
	}

});

function scrolled(o) {

	if (o.offsetWidth + o.scrollLeft == o.scrollWidth) {
		pesquisarPedidos();
	}

}

function saveAdmin() {
	$.ajax({
		url : "/add-admin",
		type : "POST",
		success : function(result) {
			alert("admin adicionado")
		},
		error : function() {

		}
	})
}

function salvarUsuario() {
	var nome = $("#name").val();
	var email = $("#email").val();
	var pass = $("#password").val();

	var dados = {
		"nome" : nome,
		"email" : email,
		"senha" : pass
	}

	$.ajax({
		type : "POST",
		url : "/usuario/add",
		data : dados,
		success : function(result) {
			$.notify({
				message : result
			}, {
				type : 'success'

			});
		},
		error : function() {
			$.notify({
				message : "Erro ao salvar!"
			}, {
				type : 'success'

			});
		}

	})

}


function listaUf() {
	$.ajax({
		url : "/list-uf",
		type : "GET",
		success : function(result) {
			var json = JSON.Stringfy(result);
			var obj = JSON.parse(json);
			alert(obj[1]);
			$.each(obj, function(idx, item) {

			})

		},
		error : function() {
			alert("erro");
		}

	})
}

function testee() {
	$.ajax({
		url : "/get-logged-user",
		type : "GET",
		success : function(result) {
			var json = JSON.stringify(result);
			var obj = JSON.parse(json);

			$("#user-logado").html(obj.nome);
		},
		error : function() {

		}
	})
}

function mostrarSelectUf() {
	$("#itens-carregados").addClass("tohide");
	$("#preencher-endereco").removeClass("tohide");
	$("#select-endereco").removeClass("tohide");
	$("#uf-label").html("");
	$("#separator").removeClass("fa fa-angle-right");
	$("#alert-sem-pedidos").addClass("tohide");
	$("#loadMorePedidos").addClass("tohide");
	$("#categoria").addClass("tohide");

}

$("#select-categoria").change(function(){
	localStorage.removeItem("pagina");
	$("#itens-carregados").html("");
	localStorage.removeItem("fimLista");
	localStorage.removeItem("pagina-final");
	localStorage.removeItem("iterator");
	localStorage.removeItem("paginacao");
	localStorage.removeItem("categoria-selecionada");
    localStorage.setItem("categoria-selecionada", $(this).val());
    $("#itens-carregados").html("");
    pesquisarPedidos();
})

$("#select-categoria-instituicao").change(function(){
	localStorage.removeItem("pagina-instituicao");
	localStorage.removeItem("categoria-selecionada");
    localStorage.setItem("categoria-selecionada", $(this).val());
    $("#itens-carregados-instituicao").html("");
    $("#alert-sem-instituicoes").addClass("tohide")
    pesquisarInstituicao();
})

function onLoadPageInstituicoes(){
	localStorage.removeItem("pagina-instituicao");
	localStorage.removeItem("categoria-selecionada");
	$("#itens-carregados-instituicao").html("");
	$("#itens-carregados-instituicao").addClass("tohide");
	$("#select-endereco-pesquisa-instituicao").removeClass("tohide");
	$("#categoria-instituicao").addClass("tohide");
	
	
}

$("#selecionar-estado").click(function(){
	localStorage.removeItem("pagina-instituicao");
	$("#select-categoria-instituicao").addClass("tohide");
	$("#uf").addClass("tohide");
	$("#itens-carregados-instituicao").html("");
	$("#alert-sem-instituicoes").addClass("tohide")
	$("#select-endereco-pesquisa-instituicao").removeClass("tohide");
	$("#categoria-instituicao").addClass("tohide");
	
})

function pesquisarInstituicao(){
	$("#categoria-instituicao").removeClass("tohide");
	var pagina = localStorage.getItem("pagina-instituicao");
	if (pagina != null) {
		localStorage.setItem("pagina-instituicao", parseInt(pagina) + 1);
	} else {
		localStorage.setItem("pagina-instituicao", 0);
	}
	
	var uf = document.getElementById("uf").value;
	uf = retornarSiglaUf(uf);
	$("#uf-label").html(document.getElementById("uf").value);
	
	
	var dados={}
	
	if(localStorage.getItem("categoria-selecionada")!=null){
		dados = {
				"uf" : uf,
				"pagina" : localStorage.getItem("pagina-instituicao"),
				"categoria" : localStorage.getItem("categoria-selecionada")
			}
	}else{
		dados = {
				"uf" : uf,
				"pagina" : localStorage.getItem("pagina-instituicao"),
				"categoria" : "TODOS"
			}
	}
	
	
	$.ajax({
		url: "/instituicoes-estado",
		data:dados,
		type:"POST",
		success:function(result){
			alert(result)
			$("#select-endereco-pesquisa-instituicao").addClass("tohide");
			$("#itens-carregados-instituicao").removeClass("tohide");
			var json = JSON.stringify(result);
			var obj = JSON.parse(json);
			if(result==0){
				$("#alert-sem-instituicoes").removeClass("tohide")
			}
			$.each(obj, function(idx,item){
				console.log(item)
				var myDate = new Date(item.dataFundacao);
				var dia = myDate.getDate();
				var mes = myDate.getMonth() + 1;
				var ano = myDate.getFullYear();
				dataCompleta = dia+'/'+mes+'/'+ ano;
				var horario=null;
				var turnos = []; 
				
				if(item.horarioVisita!=null){
					$.each(item.horarioVisita.turnoFuncionamento, function(i,x){
						if(x=="M"){
							turnos.push("Manhã");
						}
						if(x=="T"){
							turnos.push("Tarde");
						}
						if(x=="N"){
							turnos.push("Noite");
						}
											
						
					})
					
					if(item.horarioVisita.funcionaFds==true){
						horario ='Aberto para visita no(s) turno(s) ' + turnos.join("/") + ' incluindo finais de semana.' 
					}else{
						horario ='Aberto para visita no(s) turno(s) ' + turnos.join("/") + ' exceto finais de semana.'
					}
				}
				
				
				
				//$("#itens-carregados-instituicao").append('<div class="pg style_list"><img id="img-presente" style="max-height: 200px;" src="'+item.foto+'" alt=""/><div class="con"><a class="comm pull-right" href="#" onclick="carregarExcluirPresente('+item.id+')" style="margin-top:10px" title="excluir"><i class="fa fa-times fa-2x"></i></a>	<a class="comm pull-right" href="#" onclick="alterarPresente('+item.id+') " style="margin-top:10px" title="alterar"><i class="fa fa-pencil-square 	fa-2x"></i></a><h2 id = "nome-presente"><a>'+ item.nome +'</a><span></span></h2><span id = "descricao-instituicao" class="comm">'+truncar(item.descricao) +'</span> <span id="data-criacao" class="comm data_nascimento"><b>Data de criação:</b> ' + item.carencia+' </span><span id="nome-beneficiado" class="comm"><b>Beneficiado: </b>'+ item.numerobeneficiado+ ' </span><span id="sexo-beneficiado" class="comm"><b>Situacao: </b>'+ item.site  +'</span></div>');
				
				$("#itens-carregados-instituicao").append('<img id="img-beneficiado" style="width:200px;height:200px;margin-top:20px" class="image" src="'+ item.foto		+ '" onclick="modalImagem(this)" alt=""/><div class="con"><small><span style="color:#BBCC00" class="badge-person badge pull-right">'	+ item.categoria+ ' </span></small><h2 id = "nome"><a style="margin-right:5px;" target="_blank" href="/instituicoes/'+item.nomeUsuario+'">'+ item.nome+ '</a><span></span></h2><p class="p5"> <h5 id="descricao" class="comm ">'+ truncar(item.descricao, 140)+ '</h5><label id="carencia" style="margin-left:5px;"><i class="fa fa-hand-o-right"></i> Precisando de '+ item.carencia+' </label><br /><label style="margin-top:4px;margin-left:5px;"><i class="fa fa-map-marker"></i>   '   + item.endereco.logradouro+ ' nº '+ item.endereco.numero	+ ' - '	+ item.endereco.bairro	+ '-'	+ item.endereco.cidade	+ '/'	+ item.endereco.uf	+ '</label><br /><label id="telefone" style="margin-top:4px;margin-left:5px;"><i class="fa fa-phone"></i>  '+ item.telefone[0].ddd+ ' ' +item.telefone[0].numero+ '</label><br /><label style="margin-top:4px;margin-left:5px;"><i class="fa fa-home"></i> '+horario+'</label><br /><h6 id="fundacao" class="pull-right" style="margin-right:8px;color:#BBCC00">Fundado em '+dataCompleta + ' </h6></div>');				
				
				
				
			})
			
			
		},
		erro:function(){
			alert("erro")
			
		}
	})
}

function formatarData(dia, mes, ano){
	return dia + "/" + mes + "/" + ano;
 }

function pesquisarPedidos(isInitial, isOk) {
	
	$("#categoria").removeClass("tohide");

	if (isInitial) {
		localStorage.removeItem("pagina");
		$("#itens-carregados").html("");
		localStorage.removeItem("fimLista");
		localStorage.removeItem("pagina-final");
		localStorage.removeItem("iterator");
		localStorage.removeItem("paginacao");
		localStorage.removeItem("categoria-selecionada");
	}

	var pagina = localStorage.getItem("pagina");
	if (pagina != null) {
		localStorage.setItem("pagina", parseInt(pagina) + 1);
	} else {
		localStorage.setItem("pagina", 0);
	}
	// alert(localStorage.getItem("pagina"))
	var uf = document.getElementById("uf").value;

	var select = document.getElementById("uf");
	var ufselecionado = select.options[select.selectedIndex].text;

	$("#itens-carregados").removeClass("tohide");
	$("#preencher-endereco").addClass("tohide");
	$("#estado-selecionado").removeClass("tohide");
	$("#estado-selecionado")
			.html(
					"<a href='#' class='pull-right' style='margin:15px' onclick='alterarEndereco()'>"
							+ ufselecionado
							+ " <i class='fa fa-edit'></i>	</a>");

	var ufVindoSelect = uf;

	uf = retornarSiglaUf(uf);
	ufVindoSelectSigla = uf;
	var dados = {};
	if (localStorage.getItem("uf") != null) {
		uf = retornarSiglaUf(localStorage.getItem("uf"));
		$("#separator").addClass("fa fa-angle-right");
		
		$("#uf-label").html(localStorage.getItem("uf"));
		if(localStorage.getItem("categoria-selecionada")!=null){
			alert(localStorage.getItem("categoria-selecionada"))
			dados = {
					"uf" : uf,
					"pagina" : localStorage.getItem("pagina"),
					"categoria" : localStorage.getItem("categoria-selecionada")
				}
		}else{
			dados = {
					"uf" : uf,
					"pagina" : localStorage.getItem("pagina"),
					"categoria" : "TODOS"
				}
		}
		
	}
	if (isOk) {
		$("#uf-label").html(ufVindoSelect);
		localStorage.setItem("uf", ufVindoSelect);

		dados = {
			"uf" : ufVindoSelectSigla,
			"pagina" : localStorage.getItem("pagina"),
			"categoria" : "TODOS"
			
		}
	}

	// if (uf == "Pernambuco") {
	// uf = "PE";
	// }

	// var dados = {
	// "uf" : uf,
	// "pagina" : localStorage.getItem("pagina")
	// }
	// alert(localStorage.getItem("pagina"));
	$
			.ajax({
				url : "/pedidos",
				type : "GET",
				data : dados,
				success : function(result) {
					if (result.length > 0) {
						$("#alert-sem-pedidos").addClass("tohide");
					} else if (result.length == 0) {
						$("#alert-sem-pedidos").removeClass("tohide");
					}
					$("#select-endereco").addClass("tohide")
					if ((result.length / 2) != 5) {
						localStorage.setItem("fimLista", true);
						//$("#alert-sem-pedidos").addClass("tohide");
						$("#loadMorePedidos").addClass("tohide");

						localStorage.setItem("pagina-final",
								parseInt(localStorage.getItem("pagina")) * 2);
					} else {
						$("#loadMorePedidos").removeClass("tohide");
					}
					var json = JSON.stringify(result);
					var obj = JSON.parse(json);
					console.log(obj);
					$("#itens-carregados").removeClass("tohide");
					$
							.each(
									obj,
									function(idx, item) {
										console.log(idx)

										$("#itens-carregados")
												.append(
														'<div class="block"><div class="wrapper" ><div class="card1 radius shadowDepth1"><div class="card__image border-tlr-radius"><img src="'
																+ item.photo
																+ '" class="image border-tlr-radius" id="img-pedido" onclick="modalImagem(this)" /></div><div class="card__content card__padding"><div class="card__share"><div class="card__social"><a href="#"><i class=""></i </a> </div><a class="share-toggle btn-atender" href="/pedido/'
																+ item.id
																+ '" target="_blank">Atender<i style="margin-left:5px" class="fa fa-thumbs-o-up" aria-hidden="true"></i></a></div><div class="card__meta"><a target="_blank" href="/instituicoes/'+item.instituicao.nomeUsuario+'">'
																+ item.instituicao.nome
																+ '</a><time>'
																+ item.instituicao.categoria
																
																+ ' </time> </div> <article class="card__article"> <h2><a href="/pedido/'
																+ item.id
																+ '"class="link">'
																+ truncar(
																		item.nome,
																		20)
																+ '</a></h2> <p>'
																+ truncar(
																		item.descricao,
																		80)
																+ '</p> Beneficiado(a):<a href="#" ">  '+ item.beneficiado.nome +'</a> </article></div> <div class="badge pull-left" style="margin-left:10px;margin-top:10px"></div> <div class="card__action" > <div class="card__author  pull-right"> <span style="color:#BBCC00" class="badge-person badge">'+ item.instituicao.endereco.cidade+'/'+item.instituicao.endereco.uf+'</span> </div> </div> </div></div> ');

									})

				},
				error : function() {

				}
			})

}

function alterarEndereco() {
	$("#itens-carregados").addClass("tohide");
	$("#preencher-endereco").removeClass("tohide");
	$("#loadMorePedidos").addClass("tohide");
	localStorage.removeItem("pagina");
}

function setUf() {
	var uf = document.getElementById("uf").value;
	localStorage.setItem("uf", uf);

}

function retornarSiglaUf(uf) {
	if (uf == "Acre") {
		return "AC";
	}
	if (uf == "Alagoas") {
		return "AL";
	}
	if (uf == "Amazonas") {
		return "AM";
	}
	if (uf == " Amapá") {
		return "AP";
	}

	if (uf == "Bahia") {
		return "BA";
	}

	if (uf == "Ceará") {
		return "CE";
	}
	if (uf == "Distriro Federal") {
		return "DF";
	}
	if (uf == "Espírito Santo") {
		return "ES";
	}
	if (uf == "Góias") {
		return "GO";
	}

	if (uf == "Maranhão") {
		return "MA";
	}

	if (uf == "Minas Gerais") {
		return "MG";
	}

	if (uf == "Mato Grosso do Sul") {
		return "MS";
	}
	if (uf == "Mato Grosso") {
		return "MT";

	}
	if (uf == "Pará") {
		return "PA";
	}

	if (uf == "Paraíba") {
		return "PB";
	}

	if (uf == "Pernambuco") {
		return "PE";
	}

	if (uf == "Piauí") {
		return "PI";
	}

	if (uf == "Paraná") {
		return "PR";

	}
	if (uf == "Rio de Janeiro") {
		return "RJ";
	}
	if (uf == "Rio Grande do Norte") {
		return "RN";
	}

	if (uf == "Rondônia") {
		return "RO";
	}

	if (uf == "Roraima") {
		return "RR";
	}
	if (uf == "Rio Grande do Sul") {
		return "RS";
	}

	if (uf == "Santa Catarina") {
		return "SC";
	}

	if (uf == "Sergipe") {
		return "SE";
	}

	if (uf == "São Paulo") {
		return "SP";
	}

	if (uf == "Tocatins") {
		return "TO";
	}

}

function openFormAtender() {
	$("#atender").removeClass("none");
}

function closeFormAtender() {
	$("#atender").addClass("none");
	$("#btn-confirm").addClass("tohide");
	grecaptcha.reset();
}

function atenderPedido() {
	var prazo = $("#selectPrazo").val();
	var idPresente = $("#idPedido").val();

	var dados = {
		"prazo" : prazo,
		"idPresente" : idPresente
	}

	$.ajax({
		url : "/usuario/pedido-aceito",
		method : "POST",
		data : dados,
		success : function(result) {
			if(result=="1"){
				$.notify({
					message : "Existe um pedido aceito e ainda não entregue por você. Se já realizou a entrega vá no menu, depois em suas doações e marque como entregue."
				}, {
					type : 'success'

				});
			}
				
			else{
				closeFormAtender();
				$("#pedido").addClass("tohide");
				$("#pedido-sucesso").removeClass("tohide");
			}
			
			
		
		},
		error : function() {
			$.notify({
				message : "Erro ao confirmar aceitação do pedido !"
			}, {
				type : 'success'

			});
		}
	})

}

function meusPedidos() {
	$("#list-meus-pedidos").html("");

	$
			.ajax({
				url : "/usuario/meus-pedidos",
				type : "GET",
				success : function(result) {
					var json = JSON.stringify(result);
					var obj = JSON.parse(json);
					console.log(obj);
					var dia = "";
					var mes = "";
					var ano = "";
					$
							.each(
									obj,
									function(idx, item) {
										var dateAceitacao = new Date(
												item.dataAceitacao)
												.format("dd-mm-yyyy hh:mm");
										var ano = dateAceitacao
												.substring(6, 10);
										var mes = dateAceitacao.substring(3, 5);
										var dia = dateAceitacao.substring(0, 2);
										var dataAceitacao = dia + "/" + mes
												+ "/" + ano;

										var dataPrazo = new Date(ano + "-"
												+ mes + "-" + dia);
										dataPrazo.setDate(dataPrazo.getDate());
										var dataFormatadaPrazo = calculoPrazo(dataPrazo)

										var situacao = situacaoPresente(item.presente.situacao);
										if (situacao == "Aguardando sua entrega") {
											$("#list-meus-pedidos")
													.append(
															'<div class="pg style_list"><img id="img-beneficiado" style="width:200px;height:200px;margin-top:20px" class="image" src="'
																	+ item.presente.photo
																	+ '" onclick="modalImagem(this)" alt=""/><div class="con1"><span style="color: #95c800;"class="situacao badge pull-right">'
																	+ situacao
																	+ '</span><h2 id = "nome"><a>'
																	+ item.presente.nome
																	+ '</a><span></span></h2> <span id="descricao" class="comm">Descrição: '
																	+ item.presente.descricao
																	+ '</span><span id="data-aceitacao">Data aceitação: '
																	+ dia
																	+ ''
																	+ "/"
																	+ ''
																	+ mes
																	+ ''
																	+ "/"
																	+ ''
																	+ ano
																	+ '</span><span id="data-aceitacao">Prazo: '
																	+ dataFormatadaPrazo
																	+ '</span><span id="nome-beneficiado">Nome do beneficiado: '
																	+ item.presente.beneficiado.nome
																	+ '</span><span id="instituicao">Instituicao: '
																	+ '<a target="_blank" href="/instituicoes/'+item.presente.instituicao.nomeUsuario+'">'+item.presente.instituicao.nome+'</a>'
																	+ '</span><span id="instituicao-endereco">Endereço: '
																	+ item.presente.instituicao.endereco.logradouro
																	+ ' nº '
																	+ item.presente.instituicao.endereco.numero
																	+ ' - '
																	+ item.presente.instituicao.endereco.bairro
																	+ '-'
																	+ item.presente.instituicao.endereco.cidade
																	+ '/'
																	+ item.presente.instituicao.endereco.uf
																	+ ' </span><a href="#">Vizualizar endereço no mapa</a><div class="row"><span class="pull-right"  ><small style="margin-right:20px;">* Se você já realizou a entrega da doação clique <a href ="#" onclick="enviarEmailConfirmacaoEntregaDoacao('
																	+ item.presente.instituicao.email
																	+ ','
																	+ item.presente.instituicao.nome
																	+ ','
																	+ item.presente.nome
																	+ ','
																	+ item.presente.beneficiado.nome
																	+ ','
																	+ dataAceitacao
																	+ ')"> aqui</a> para comunicar a instituição.</small></span></div>');
										}
										if (situacao == "Entregue") {
											var dataEntrega = new Date(
													item.dataEntregaConfirmada)
													.format("dd-mm-yyyy hh:mm");
											var anoEntrega = dateAceitacao
													.substring(6, 10);
											var mesEntrega = dateAceitacao
													.substring(3, 5);
											var diaEntrega = dateAceitacao
													.substring(0, 2);

											$("#list-meus-pedidos")
													.append(
															'<div class="pg style_list"><img id="img-beneficiado" style="width:200px;height:200px;margin-top:20px" class="image" src="'
																	+ item.presente.photo
																	+ '" onclick="modalImagem(this)" alt=""/><div class="con1"><span style="color: #95c800;"class="situacao badge pull-right">'
																	+ situacao
																	+ '</span><h2 id = "nome"><a>'
																	+ item.presente.nome
																	+ '</a><span></span></h2> <span id="descricao" class="comm">Descrição: '
																	+ item.presente.descricao
																	+ '</span><span id="data-aceitacao">Data aceitação: '
																	+ dia
																	+ ''
																	+ "/"
																	+ ''
																	+ mes
																	+ ''
																	+ "/"
																	+ ''
																	+ ano
																	+ '</span><span id="data-aceitacao">Data da entrega: '
																	+ diaEntrega
																	+ '/'
																	+ mesEntrega
																	+ '/'
																	+ anoEntrega
																	+ '</span><span id="nome-beneficiado">Nome do beneficiado: '
																	+ item.presente.beneficiado.nome
																	+ '</span><span id="instituicao">Instituicao: '
																	+ item.presente.instituicao.nome
																	+ '</span><span id="instituicao-endereco">Endereço: '
																	+ item.presente.instituicao.endereco.logradouro
																	+ ' nº '
																	+ item.presente.instituicao.endereco.numero
																	+ ' - '
																	+ item.presente.instituicao.endereco.bairro
																	+ '-'
																	+ item.presente.instituicao.endereco.cidade
																	+ '/'
																	+ item.presente.instituicao.endereco.uf
																	+ ' </span><a href="#">Vizualizar endereço no mapa</a></div>');
										}

									})

				},
				error : function() {

				}
			})
}

function viewMinhasDoacoes() {
	$("#minhas-doacoes").removeClass("tohide");
	$("#doacoesDisponiveis").addClass("tohide");
	meusPedidos();
}
function viewDoacoes() {
	$("#minhas-doacoes").addClass("tohide");
	$("#doacoesDisponiveis").removeClass("tohide");
}

function calculoPrazo(dataPrazo) {

	dataPrazo.setDate(dataPrazo.getDate() + 7);
	if (dataPrazo.getDay() == 5) {
		dataPrazo.setDate(dataPrazo.getDate() + 2);
	}
	if (dataPrazo.getDay() == 6) {
		dataPrazo.setDate(dataPrazo.getDate() + 1);
	}
	var anoPrazo = dataPrazo.getFullYear();
	var mesPrazo = dataPrazo.getMonth() + 1;
	var diaPrazo = dataPrazo.getDate() + 1;
	var dataFormatada = diaPrazo + '/' + mesPrazo + '/' + anoPrazo;
	return dataFormatada;

}

function situacaoPresente(situacao) {
	if (situacao == "ACEITO") {
		return "Aguardando sua entrega"
	}
	if (situacao == "ENTREGUE") {
		return "Entregue"
	}
}

function modalImagem(img) {
	var modal = document.getElementById('modal-foto');
	// var img = document.getElementById('img-beneficiado');

	var modalImg = document.getElementById("img01");
	var captionText = document.getElementById("caption");
	modal.style.display = "block";
	modalImg.src = img.src;
	$("#select-categoria").removeClass("form-control");
	var span = document.getElementsByClassName("close")[0];

	span.onclick = function() {
		$("#select-categoria").addClass("form-control");
		modal.style.display = "none";
	}

}

function enviarEmailConfirmacaoEntregaDoacao(emailInstituicao, nomeInstituicao,
		nomeDoacao, nomeBeneficiado, dataAceitacao) {
	alert("chegou");

	var dados = {
		"emailInstituicao" : emailInstituicao,
		"nomeInstituicao" : nomeInstituicao,
		"nomeDoacao" : nomeDoacao,
		"nomeBeneficiado" : nomeBeneficiado,
		"dataAceitacao" : dataAceitacao
	}

	$.ajax({
		url : "/usuario/enviar-email-confirmacao-entrega",
		type : "POST",
		data : dados,
		success : function(result) {
			$.notify({
				message : result
			}, {
				type : 'success'

			});
		},
		error : function() {
			$.notify({
				message : "Erro ao enviar email."
			}, {
				type : 'success'

			});
		}

	})
}

function formConfirmacaoEntrega(){
	$("#confirmar-entrega").removeClass("none");
	//$('#confirmar-entrega').html('<div class="place_form"><i class="fa fa-times close_window" id="closeExcluirPresente"  onclick="closeFormConfirmacaoEntrega()" ></i><h3>Tem certeza?<span></span></h3><form id="formConfirmarEntrega"><label>Tem certeza que realizou a entrega?</label><div class="btns-confirm" style="margin-top:20px" ><a class="btn_confirm" href="#" id="confirm-sim-confirmacao-entrega" onClick="confirmarEntrega('+id+')"> Sim</a><a class="btn_confirm" href="#" id="confirm-nao-confirmacao-entrega" style="margin-left:10px" onclick="closeFormConfirmacaoEntrega()"> Não</a></div></form></div>');
}

function closeFormConfirmacaoEntrega(){
	$("#confirmar-entrega").addClass("none");
}


function confirmarEntrega(id) {
	var dados = {
		"id" : id
	}

	$.ajax({
		url : "/usuario/confirmar-entrega-doacao",
		type : "POST",
		data : dados,
		success : function(result) {
			location.reload();
		},
		error : function() {
			alert("erro");
		}
	})
}

function truncar(texto, limite) {
	if (texto != null) {
		if (texto.length > limite) {
			limite--;
			last = texto.substr(limite - 1, 1);
			while (last != ' ' && limite > 0) {
				limite--;
				last = texto.substr(limite - 1, 1);
			}
			last = texto.substr(limite - 2, 1);
			if (last == ',' || last == ';' || last == ':') {
				texto = texto.substr(0, limite - 2) + '...';
			} else if (last == '.' || last == '?' || last == '!') {
				texto = texto.substr(0, limite - 1);
			} else {
				texto = texto.substr(0, limite - 1) + '...';
			}
		}
	} else {
		return " ";
	}

	return texto;
}

var x = 0;
$('#right-button').click(
		function() {
			if (localStorage.getItem("paginacao") == null) {
				localStorage.setItem("paginacao", 0);
			} else {
				localStorage.setItem("paginacao", localStorage
						.getItem("paginacao") + 1);
			}

			/*
			 * if(localStorage.getItem("paginacao") == 0){
			 * $("#left-button").addClass("tohide"); }
			 */

			event.preventDefault();
			$('#itens-carregados').animate({
				marginLeft : "-=100%",

			}, "fast");
			alert(localStorage.getItem("pagina-final"))
			if (localStorage.getItem("pagina-final") != null) {
				if (localStorage.getItem("") == null) {
					localStorage.setItem("iterator", localStorage
							.getItem("pagina-final"));
				}

				if (localStorage.getItem("iterator") == localStorage
						.getItem("pagina-final")) {
					$("#right-button").addClass("tohide");
				} else {
					localStorage.setItem("iterator", parseInt(localStorage
							.getItem("iterator")) + 1);
				}

			}

			// alert(localStorage.getItem("pagina-final"));
		});

$('#left-button').click(
		function() {
			alert(localStorage.getItem("iterator"));
			x = x - 1;
			event.preventDefault();
			$('#itens-carregados').animate({
				marginLeft : "+=100%",

			}, "fast");

			if (localStorage.getItem("pagina-final") != null) {
				localStorage.setItem("iterator", parseInt(localStorage
						.getItem("iterator")) - 1);
				$("#right-button").removeClass("tohide");
				if (localStorage.getItem("iterator") == 1) {

				}
			}
			alert(localStorage.getItem("iterator"));
		});

var y = 0;
$('#right-button-mobile').click(function() {

	y++;
	if (y == 10) {
		pesquisarPedidos();
		y = 0;
	}
	event.preventDefault();
	$('#itens-carregados').animate({
		marginLeft : "-=100%",

	}, "fast");
});

$('#left-button-mobile').click(function() {

	y = y - 1;
	event.preventDefault();
	$('#itens-carregados').animate({
		marginLeft : "+=100%",

	}, "fast");

});

function salvarNovaSenha(){
	var senha = document.getElementById("senha").value;
	var confirmarSenha = document.getElementById("confirmar-senha").value;
	
	if(senha!=confirmarSenha){
		$.notify({
			message : "Campo confirmar senha diferente do campo senha."
		}, {
			type : 'success'

		});
		document.getElementById("senha").value="";
		document.getElementById("confirmar-senha").value="";
		return ;
	}
	
	var dados = {
			"id": document.getElementById("id").value ,
			"senha":  senha
			
	}
	
	$.ajax({
		
		url:"/instituicao/salvar-alteracao-senha",
		type:"POST",
		data:dados,
		success:function(result){
			$.notify({
				message : "Senha alterada com sucesso."
			}, {
				type : 'success'

			});
			window.setTimeout(function(){ window.location = "http://localhost:8080/form-login"; },2000);
		},
		error : function(){
			
		}
	})
	
	
}


