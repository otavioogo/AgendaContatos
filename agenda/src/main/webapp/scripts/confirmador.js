/**
 * CONFIRMACAO DE EXCLUSAO DE UM CONTATO
 */
 
 function confirmar(idcon){
	let resposta = confirm("confirma a exclusao desde contato ?")
	if (resposta === true) {
		//alert(idcon)
		window.location.href = "delete?idcon=" + idcon
	}
	
	
	
	
}