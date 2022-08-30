/**
 * CONFIRMACAO DE EXCLUSAO DE UM CONTATO
 *@param idcon
 */
 
 function confirmar(idcon){
	let resposta = confirm("confirma a exclusao desde contato ?")
	if (resposta === true) {
		
		window.location.href = "delete?idcon=" + idcon
	}
	
	
	
	
}