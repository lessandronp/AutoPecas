function formataDecimal(objTextBox, SeparadorMilesimo, SeparadorDecimal, e) {
    var sep = 0;
    var key = '';
    var i = j = 0;
    var len = len2 = 0;
    var strCheck = '0123456789';
    var aux = aux2 = '';
    var whichCode = (window.addEventListener) ? e.which : e.keyCode;
    /*
     * 13=enter, 8=backspace as demais retornam 0(zero) whichCode==0 faz com que
     * seja possivel usar todas as teclas como delete, setas, etc
     */
    if ((objTextBox.value.length >= objTextBox.maxLength) && (whichCode != 13)
        && (whichCode != 8))
        whichCode = 1;
    if ((whichCode == 13) || (whichCode == 0) || (whichCode == 8))
        return true;
    /* Valor para o c�digo da Chave */
    key = String.fromCharCode(whichCode);

    if (strCheck.indexOf(key) == -1)
        /* Chave inv�lida */
        return false;
    len = objTextBox.value.length;
    for (i = 0; i < len; i++)
        if ((objTextBox.value.charAt(i) != '0')
            && (objTextBox.value.charAt(i) != SeparadorDecimal))
            break;
    aux = '';
    for (; i < len; i++)
        if (strCheck.indexOf(objTextBox.value.charAt(i)) != -1)
            aux += objTextBox.value.charAt(i);
    aux += key;
    len = aux.length;
    if (len == 0)
        objTextBox.value = '';
    if (len == 1)
        objTextBox.value = '0' + SeparadorDecimal + '0' + aux;
    if (len == 2)
        objTextBox.value = '0' + SeparadorDecimal + aux;
    if (len > 2) {
        aux2 = '';
        for (j = 0, i = len - 3; i >= 0; i--) {
            if (j == 3) {
                aux2 += SeparadorMilesimo;
                j = 0;
            }
            aux2 += aux.charAt(i);
            j++;
        }
        objTextBox.value = '';
        len2 = aux2.length;
        for (i = len2 - 1; i >= 0; i--)
            objTextBox.value += aux2.charAt(i);
        objTextBox.value += SeparadorDecimal + aux.substr(len - 2, len);
    }
    return false;
}

function somenteCaracteres(campo) {
    var stringIgnorada = "áàãââÁÀÃÂéêÉÊíÍóõôÓÔÕúüÚÜçÇabcdefghijklmnopqrstuvxwyz^~´`!#$%¨&*()_-+=[]{}|/'?:;<>0123456789";
    var removido ="AAAAAAAAAEEEEIIOOOOOOUUUUCCABCDEFGHIJKLMNOPQRSTUVXWYZ";
    var posicao, caractere;
    var alterado = "";
    for (var i=0; i < campo.length; i++)
    {
        caractere = campo.charAt(i);
        posicao  = stringIgnorada.indexOf(caractere);
        if (posicao > -1)
            alterado += removido.charAt(posicao);
        else
            alterado += campo.charAt(i);
    }
    return alterado;
}

function removeCaracteresEspeciais(campo) {
    var stringIgnorada = "áàãââÁÀÃÂéêÉÊíÍóõôÓÔÕúüÚÜçÇabcdefghijklmnopqrstuvxwyz^~´`!#$%¨&*()_-+=[]{}|/'?:;<>";
    var removido ="AAAAAAAAAEEEEIIOOOOOOUUUUCCABCDEFGHIJKLMNOPQRSTUVXWYZ";
    var posicao, caractere;
    var alterado = "";
    for (var i=0; i < campo.length; i++)
    {
        caractere = campo.charAt(i);
        posicao  = stringIgnorada.indexOf(caractere);
        if (posicao > -1)
            alterado += removido.charAt(posicao);
        else
            alterado += campo.charAt(i);
    }
    return alterado;
}


function somenteNumeros(e, decimal) {
    var key;
    var keychar;

    if (window.event) {
        key = window.event.keyCode;
    }
    else if (e) {
        key = e.which;
    }
    else {
        return true;
    }
    keychar = String.fromCharCode(key);

    if ((key==null) || (key==0) || (key==8) ||  (key==9) || (key==13) || (key==27) ) {
        return true;
    }
    else if ((("0123456789").indexOf(keychar) > -1)) {
        return true;
    }
    else if (decimal && (keychar == ".")) {
        return true;
    }
    else
        return false;

}

function limpaCampos(nomeForm) {
    document.forms[nomeForm].reset();
}

//Remove caracteres
function remove(str, sub) {
    i = str.indexOf(sub);
    r = "";
    if (i == -1) return str;
    r += str.substring(0,i) + remove(str.substring(i + sub.length), sub);
    return r;
}

function removeMascara(object){
    valor = recuperaValor(object);
    semMascara = remove(valor, ".")
    semMascara = remove(semMascara, "-")
    semMascara = remove(semMascara, "/")
    semMascara = remove(semMascara, "(")
    semMascara = remove(semMascara, ")")
    document.getElementById(object).value = semMascara;
    return semMascara;
}


function recuperaValor(object){
    valor = document.getElementById(object).value;
    return valor;
}

function validaCpf(cpf) {
    var numeros, digitos, soma, i, resultado, digitos_iguais;
    digitos_iguais = 1;
    if (cpf.length < 11) {
        return false;
    }
    for (i = 0; i < cpf.length - 1; i++)
        if (cpf.charAt(i) != cpf.charAt(i + 1))
        {
            digitos_iguais = 0;
            break;
        }
    if (!digitos_iguais)
    {
        numeros = cpf.substring(0,9);
        digitos = cpf.substring(9);
        soma = 0;
        for (i = 10; i > 1; i--)
            soma += numeros.charAt(10 - i) * i;
        resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
        if (resultado != digitos.charAt(0))
            return false;
        numeros = cpf.substring(0,10);
        soma = 0;
        for (i = 11; i > 1; i--)
            soma += numeros.charAt(11 - i) * i;
        resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
        if (resultado != digitos.charAt(1))
            return false;
        return true;
    }
    else
        return false;
}

function validaCnpj(cnpj) {
    var numeros, digitos, soma, i, resultado, pos, tamanho, digitos_iguais;
    digitos_iguais = 1;
    if (cnpj.length < 14 && cnpj.length < 15)
        return false;
    for (i = 0; i < cnpj.length - 1; i++)
        if (cnpj.charAt(i) != cnpj.charAt(i + 1))
        {
            digitos_iguais = 0;
            break;
        }
    if (!digitos_iguais)
    {
        tamanho = cnpj.length - 2
        numeros = cnpj.substring(0,tamanho);
        digitos = cnpj.substring(tamanho);
        soma = 0;
        pos = tamanho - 7;
        for (i = tamanho; i >= 1; i--)
        {
            soma += numeros.charAt(tamanho - i) * pos--;
            if (pos < 2)
                pos = 9;
        }
        resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
        if (resultado != digitos.charAt(0))
            return false;
        tamanho = tamanho + 1;
        numeros = cnpj.substring(0,tamanho);
        soma = 0;
        pos = tamanho - 7;
        for (i = tamanho; i >= 1; i--)
        {
            soma += numeros.charAt(tamanho - i) * pos--;
            if (pos < 2)
                pos = 9;
        }
        resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
        if (resultado != digitos.charAt(1))
            return false;
        return true;
    }
    else
        return false;
}

